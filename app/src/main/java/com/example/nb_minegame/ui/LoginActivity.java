package com.example.nb_minegame.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.EditText;

import com.example.nb_minegame.R;
import com.example.nb_minegame.databinding.ActivityLoginBinding;
import com.example.nb_minegame.util.AlertType;
import com.example.nb_minegame.util.NotificationUtility;
import com.example.nb_minegame.util.ProgressDialog;
import com.example.nb_minegame.util.Utility;
import com.example.nb_minegame.util.ValidationUtility;
import com.example.nb_minegame.viewmodel.FirebaseViewModel;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private FirebaseViewModel customerViewModel;
    private Dialog progressDialog;
    private final String TAG = "LoginActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        setObservers();
    }

    //init ui
    private void init() {
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        progressDialog = ProgressDialog.createProgressDialog(this);

        customerViewModel = new FirebaseViewModel(getApplication());
        customerViewModel.init(this);

        binding.lblSignup.setOnClickListener(view -> {
            Utility.startActivity(SignupActivity.class, LoginActivity.this);
        });

        binding.btnLogin.setOnClickListener(view -> {
            validateLogin();
        });

    }

    //validate login
    private void validateLogin() {
        if (!ValidationUtility.validateField((EditText) binding.username, getString(R.string.enter_username))) {
            return;
        } else if (!ValidationUtility.validateField((EditText) binding.password, getString(R.string.enter_password))) {
            return;
        } else {
            customerViewModel.login(binding.username.getText().toString(), binding.password.getText().toString());
        }
    }

    private void setObservers() {

        customerViewModel.getIsLoading().observe(this, new Observer<Boolean>() {
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

        customerViewModel.getOnSuccess().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    Utility.startActivityFinish(HomeActivity.class, LoginActivity.this);
                }
            }
        });

        customerViewModel.getOnError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                NotificationUtility.showMessage(AlertType.ERROR, LoginActivity.this, s);
            }
        });


    }
}