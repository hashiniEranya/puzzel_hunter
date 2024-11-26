package com.example.nb_minegame.viewmodel;

import android.app.Activity;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.nb_minegame.listener.OnNetworkResponseListener;
import com.example.nb_minegame.model.CustomerModel;
import com.example.nb_minegame.repo.FirebaseRepo;
import com.example.nb_minegame.util.Constants;
import com.example.nb_minegame.util.NotificationUtility;
import com.example.nb_minegame.util.PreferenceManager;
import com.example.nb_minegame.util.StringUtility;

import org.jetbrains.annotations.NotNull;

/**
 * @author chalana
 * @created 2022/12/10 | 3:33 PM
 * @contact Chalana.n@fidenz.com | 071 6 359 376
 */
public class FirebaseViewModel extends BaseViewModel {

    private void onLoading() {
        isLoading.postValue(true);
    }

    private void stopLoading() {
        isLoading.postValue(false);
    }

    public MutableLiveData<CustomerModel> customerModelMutableLiveData = new MutableLiveData<>();
    CustomerModel localModel;

    private Activity activity;
    private FirebaseRepo repo;
    private PreferenceManager manager;

    public FirebaseViewModel(@NonNull @NotNull Application application) {
        super(application);
    }

    public void init(Activity activity) {
        this.activity = activity;
        manager = new PreferenceManager(activity);
        repo = new FirebaseRepo(activity);
    }


    public void signup(CustomerModel customerModel) {
        onLoading();
        repo.signup(customerModel, new OnNetworkResponseListener<Boolean, String>() {
            @Override
            public void onSuccessResponse(Boolean response) {
                stopLoading();
                manager.setPreference(Constants.USER_DATE, StringUtility.objectToString(customerModel));
                onSuccess.postValue(true);
            }

            @Override
            public void onErrorResponse(String response) {
                stopLoading();
                onError.postValue(response);
            }

            @Override
            public void onNetworkError() {
                stopLoading();
                NotificationUtility.showNoInternetMessage(activity);
            }
        });
    }


    //get customer details from local db
    public void fetchCustomerObject() {
        onLoading();
        repo.fetchCustomerObject(localModel.getUsername(), new OnNetworkResponseListener<CustomerModel, String>() {
            @Override
            public void onSuccessResponse(CustomerModel response) {
                stopLoading();
                manager.setPreference(Constants.USER_DATE, StringUtility.objectToString(response));
                customerModelMutableLiveData.postValue(response);
            }

            @Override
            public void onErrorResponse(String response) {
                stopLoading();
                onError.postValue(response);
            }

            @Override
            public void onNetworkError() {
                stopLoading();
                NotificationUtility.showNoInternetMessage(activity);
            }
        });
    }


    //customer login
    public void login(String username, String password) {
        onLoading();
        repo.login(username, password, new OnNetworkResponseListener<CustomerModel, String>() {
            @Override
            public void onSuccessResponse(CustomerModel response) {
                manager.setPreference(Constants.USER_DATE, StringUtility.objectToString(response));
                stopLoading();
                onSuccess.postValue(true);
            }

            @Override
            public void onErrorResponse(String response) {
                stopLoading();
                onError.postValue(response);
            }

            @Override
            public void onNetworkError() {
                stopLoading();
                NotificationUtility.showNoInternetMessage(activity);
            }
        });
    }


    //logout session
    public void logout() {
        manager.removePreference(Constants.USER_DATE);
//        localDbRepo.clearAll();
    }


}
