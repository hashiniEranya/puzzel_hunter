package com.example.nb_minegame.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;

import com.example.nb_minegame.R;
import com.example.nb_minegame.databinding.ActivitySignupBinding;
import com.example.nb_minegame.model.CustomerModel;
import com.example.nb_minegame.util.AlertType;
import com.example.nb_minegame.util.Constants;
import com.example.nb_minegame.util.Encryptor;
import com.example.nb_minegame.util.NotificationUtility;
import com.example.nb_minegame.util.ProgressDialog;
import com.example.nb_minegame.util.Utility;
import com.example.nb_minegame.util.ValidationUtility;
import com.example.nb_minegame.viewmodel.FirebaseViewModel;

public class SignupActivity extends AppCompatActivity {

    private ActivitySignupBinding binding;
    private FirebaseViewModel viewModel;
    private Dialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        setObservers();
    }

    //init UI
    private void init() {
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new FirebaseViewModel(getApplication());
        viewModel.init(this);
        progressDialog = ProgressDialog.createProgressDialog(this);

        binding.lblLogin.setOnClickListener(view -> {
            finish();
        });

        binding.btnSignup.setOnClickListener(view -> {
            validateSignup();
        });
    }

    //set observers
    private void setObservers() {
        viewModel.getIsLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    if (!progressDialog.isShowing()) {
                        progressDialog.show();
                    }
                } else {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                }
            }
        });


        viewModel.getOnError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                NotificationUtility.showMessage(AlertType.ERROR, SignupActivity.this, s);
            }
        });

        viewModel.getOnSuccess().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                NotificationUtility.showMessage(AlertType.SUCCESS, SignupActivity.this, getString(R.string.operation_successful));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Utility.startActivityFinish(SplashActivity.class, SignupActivity.this);
                    }
                }, 3000);

            }
        });
    }

    //validate signup
    private void validateSignup() {
        if (!ValidationUtility.validateField(binding.username, getString(R.string.enter_username))) {
            return;
        } else if (!ValidationUtility.validatePasswords(binding.password, getString(R.string.password_empty_msg))) {
            return;
        } else if (!ValidationUtility.validatePasswords(binding.confirmPassword, getString(R.string.password_empty_msg))) {
            return;
        } else if (!binding.password.getText().toString().equals(binding.confirmPassword.getText().toString())) {
            binding.confirmPassword.setError(getString(R.string.password_does_not_matched));
            return;
        } else {
            CustomerModel model = new CustomerModel(binding.username.getText().toString(),
                    Encryptor.encrypt(binding.password.getText().toString()), Constants.ACTIVE_STATUS);
            viewModel.signup(model);
        }
    }


}