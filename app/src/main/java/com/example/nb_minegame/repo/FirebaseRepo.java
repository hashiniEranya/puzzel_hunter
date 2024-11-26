package com.example.nb_minegame.repo;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;
import com.example.nb_minegame.R;
import com.example.nb_minegame.listener.OnNetworkResponseListener;
import com.example.nb_minegame.model.CustomerModel;
import com.example.nb_minegame.util.Constants;
import com.example.nb_minegame.util.Encryptor;
import com.example.nb_minegame.util.StringUtility;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * @author chalana
 * @created 2022/12/10 | 3:04 PM
 * @contact Chalana.n@fidenz.com | 071 6 359 376
 */
public class FirebaseRepo {

    private Activity activity;
    private FirebaseFirestore firebaseFirestore;
    private String TAG = "CustomerRepo";
    private CollectionReference customer_reference;
    private CollectionReference vehicle_reference;
    private CollectionReference queue_reference;
    private CustomerModel customerModel;

    public FirebaseRepo(Activity activity) {
        this.activity = activity;
        firebaseFirestore = FirebaseFirestore.getInstance();
        customer_reference = firebaseFirestore.collection(Constants.FIRESTORE_USER);
    }


    // new customer signup
    public void signup(CustomerModel customerModel, OnNetworkResponseListener listener) {
        customer_reference.document(customerModel.getUsername()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                boolean isExist = false;
                if (task.isSuccessful()) {
                    //if customer not exist signup as new user
                    DocumentSnapshot result = task.getResult();
                    if (!result.exists()) {
                        isExist = false;
                    } else {
                        isExist = true;
                        listener.onErrorResponse(activity.getString(R.string.customer_already_exist));
                    }
                }

                if (!isExist) {
                    customer_reference.document(customerModel.getUsername()).set(customerModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            listener.onSuccessResponse(true);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            listener.onErrorResponse(e.getLocalizedMessage());
                        }
                    });
                }
            }
        });
    }


    //customer login
    public void login(String username, String password, OnNetworkResponseListener listener) {
        customer_reference.document(username).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    if (documentSnapshot.getData().get("username").equals(username) & Encryptor.decrypt(documentSnapshot.getData().get("password").toString()).equals(password)) {
                        CustomerModel customerModel = (CustomerModel) StringUtility.stringToObject(documentSnapshot.getData().toString(), CustomerModel.class);
                        listener.onSuccessResponse(customerModel);
                    } else {
                        listener.onErrorResponse(activity.getString(R.string.password_error_message));
                    }
                } else {
                    listener.onErrorResponse(activity.getString(R.string.account_not_found));
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                listener.onErrorResponse(e.getLocalizedMessage());
            }
        });
    }

    //get customer details
    public void fetchCustomerObject(String nic, OnNetworkResponseListener listener) {
        customer_reference.document(nic).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    Log.e(TAG, "DATA = " + documentSnapshot.getData().toString());
                    CustomerModel customerModel = (CustomerModel) StringUtility.stringToObject(documentSnapshot.getData().toString(), CustomerModel.class);
                    listener.onSuccessResponse(customerModel);
                } else {
                    listener.onSuccessResponse(null);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                listener.onErrorResponse(e.getLocalizedMessage());
            }
        });
    }


}
