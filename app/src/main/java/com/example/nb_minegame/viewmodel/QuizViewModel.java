package com.example.nb_minegame.viewmodel;

import android.app.Activity;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.nb_minegame.listener.OnNetworkResponseListener;
import com.example.nb_minegame.model.QuizResponse;
import com.example.nb_minegame.repo.Repository;

import org.jetbrains.annotations.NotNull;

/**
 * @author chalana
 * @created 2023/03/12 | 12:11 PM
 * @contact Chalana.n@fidenz.com | 071 6 359 376
 */
public class QuizViewModel extends BaseViewModel{

    private void onLoading() {
        isLoading.postValue(true);
    }

    private Repository repository;

    private void stopLoading() {
        isLoading.postValue(false);
    }

    private Activity mActivity;

    public MutableLiveData<QuizResponse> quizViewModelMutableLiveData = new MutableLiveData<>();

    public QuizViewModel(@NonNull @NotNull Application application) {
        super(application);
    }

    public void init(Activity activity) {
        mActivity = activity;
        repository = new Repository(activity);
    }


    public void fetchQuiz(){
        onLoading();
        repository.fetchQuestion(new OnNetworkResponseListener<QuizResponse,String>() {
            @Override
            public void onSuccessResponse(QuizResponse response) {
                stopLoading();
                quizViewModelMutableLiveData.postValue(response);
            }

            @Override
            public void onErrorResponse(String response) {
                stopLoading();
                onError.postValue(response);
            }

            @Override
            public void onNetworkError() {
                stopLoading();
                onError.postValue("No Internet !");
            }
        });
    }
}
