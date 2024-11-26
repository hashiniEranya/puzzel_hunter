package com.example.nb_minegame.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;

import com.example.nb_minegame.MainActivity;
import com.example.nb_minegame.R;
import com.example.nb_minegame.util.Constants;
import com.example.nb_minegame.util.LogUtil;
import com.example.nb_minegame.util.PreferenceManager;
import com.example.nb_minegame.util.StringUtility;
import com.example.nb_minegame.util.UIHelpUtility;
import com.example.nb_minegame.util.Utility;

public class SplashActivity extends AppCompatActivity {

    private PreferenceManager preferenceManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UIHelpUtility.makeActivityFullScreen(this, this);
        setContentView(R.layout.activity_splash);

        preferenceManager = new PreferenceManager(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                validateLogin();
            }
        }, 3000);
    }

    private void validateLogin() {
        if (StringUtility.isNotNull(preferenceManager.getString(Constants.USER_DATE))) { //check logged in as customer
            LogUtil.debug("fuck","Home");
            Utility.startActivityFinish(HomeActivity.class, this);
        } else {
            LogUtil.debug("fuck","login");
            Utility.startActivityFinish(LoginActivity.class, this);
        }
    }
}