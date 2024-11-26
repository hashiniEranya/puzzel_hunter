package com.example.nb_minegame.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.nb_minegame.R;
import com.example.nb_minegame.databinding.ActivityHomeBinding;
import com.example.nb_minegame.model.QuizResponse;
import com.example.nb_minegame.util.AlertType;
import com.example.nb_minegame.util.Constants;
import com.example.nb_minegame.util.NotificationUtility;
import com.example.nb_minegame.util.PreferenceManager;
import com.example.nb_minegame.util.ProgressDialog;
import com.example.nb_minegame.util.Utility;
import com.example.nb_minegame.viewmodel.QuizViewModel;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;
    private CountDownTimer timer;
    private int countDownTime = 15 * 1000;
    private int remainTime = 0;
    private QuizViewModel quizViewModel;
    private Dialog progressDialog;
    private QuizResponse quizResponse;
    private int answer = -1;

    private PreferenceManager preferenceManager;
    private int quizNo, score;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        setObservers();
    }

    //init
    private void init() {
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        quizViewModel = new QuizViewModel(getApplication());
        quizViewModel.init(this);

        binding.llResults.setVisibility(View.GONE);

        preferenceManager = new PreferenceManager(this);

        progressDialog = ProgressDialog.createProgressDialog(this);

        timer = new CountDownTimer(countDownTime, 1000) {

            public void onTick(long millisUntilFinished) {
                remainTime--;
                binding.lblRemainingTime.setText(getString(R.string.remaining_time) + " ( " + remainTime + "s )");
                binding.progressBar.setProgress(remainTime);
            }

            public void onFinish() {
                checkAnswer();
            }
        };

        binding.pinLayout.number0.setOnClickListener(view -> {
            numberOnTap(0);
        });

        binding.pinLayout.number1.setOnClickListener(view -> {
            numberOnTap(1);
        });

        binding.pinLayout.number2.setOnClickListener(view -> {
            numberOnTap(2);
        });

        binding.pinLayout.number3.setOnClickListener(view -> {
            numberOnTap(3);
        });

        binding.pinLayout.number4.setOnClickListener(view -> {
            numberOnTap(4);
        });

        binding.pinLayout.number5.setOnClickListener(view -> {
            numberOnTap(5);
        });

        binding.pinLayout.number6.setOnClickListener(view -> {
            numberOnTap(6);
        });

        binding.pinLayout.number7.setOnClickListener(view -> {
            numberOnTap(7);
        });

        binding.pinLayout.number8.setOnClickListener(view -> {
            numberOnTap(8);
        });
        binding.pinLayout.number9.setOnClickListener(view -> {
            numberOnTap(9);
        });


        binding.imgExit.setOnClickListener(view -> {
            NotificationUtility.showAlertDialog(HomeActivity.this, "Logout",
                    "Are you sure you want to logout ?", "Yes", "No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            preferenceManager.removePreference(Constants.USER_DATE);
                            preferenceManager.removePreference(Constants.SCORE);
                            preferenceManager.removePreference(Constants.QUESTION_NO);
                            Utility.startActivityFinishAll(SplashActivity.class,HomeActivity.this);
                        }
                    }, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
        });


        binding.pinLayout.lblSubmit.setOnClickListener(view -> {
            remainTime = 15;
            timer.cancel();
            checkAnswer();

        });


        score = preferenceManager.getInt(Constants.SCORE);
        quizNo = preferenceManager.getInt(Constants.QUESTION_NO);

        binding.lblQuizNo.setText(getString(R.string.quiz_no) + " " + quizNo);
        binding.lblScore.setText(getString(R.string.score) + " " + (score * 5));

        fetchQuestion();
    }


    private void numberOnTap(int number) {
        answer = number;

        binding.pinLayout.number0.setBackground(number == 0 ? getDrawable(R.drawable.pin_button_disabled) : getDrawable(R.drawable.pin_button));
        binding.pinLayout.number1.setBackground(number == 1 ? getDrawable(R.drawable.pin_button_disabled) : getDrawable(R.drawable.pin_button));
        binding.pinLayout.number2.setBackground(number == 2 ? getDrawable(R.drawable.pin_button_disabled) : getDrawable(R.drawable.pin_button));
        binding.pinLayout.number3.setBackground(number == 3 ? getDrawable(R.drawable.pin_button_disabled) : getDrawable(R.drawable.pin_button));
        binding.pinLayout.number4.setBackground(number == 4 ? getDrawable(R.drawable.pin_button_disabled) : getDrawable(R.drawable.pin_button));
        binding.pinLayout.number5.setBackground(number == 5 ? getDrawable(R.drawable.pin_button_disabled) : getDrawable(R.drawable.pin_button));
        binding.pinLayout.number6.setBackground(number == 6 ? getDrawable(R.drawable.pin_button_disabled) : getDrawable(R.drawable.pin_button));
        binding.pinLayout.number7.setBackground(number == 7 ? getDrawable(R.drawable.pin_button_disabled) : getDrawable(R.drawable.pin_button));
        binding.pinLayout.number8.setBackground(number == 8 ? getDrawable(R.drawable.pin_button_disabled) : getDrawable(R.drawable.pin_button));
        binding.pinLayout.number9.setBackground(number == 9 ? getDrawable(R.drawable.pin_button_disabled) : getDrawable(R.drawable.pin_button));
    }

    private void checkAnswer() {

        binding.llResults.setVisibility(View.VISIBLE);

        if (answer == -1) {
            binding.lottieStatus.setAnimation("time_over.json");
            binding.lottieStatus.playAnimation();
        } else if (answer == quizResponse.getSolution()) {
            binding.lottieStatus.setAnimation("correct_answer.json");
            score++;
            binding.lottieStatus.playAnimation();
        } else if (answer != quizResponse.getSolution()) {
            binding.lottieStatus.setAnimation("wrong_answer.json");
            binding.lottieStatus.playAnimation();
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                fetchQuestion();
            }
        }, 3000);
    }

    private void setObservers() {
        quizViewModel.getIsLoading().observe(this, new Observer<Boolean>() {
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


        quizViewModel.getOnError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                NotificationUtility.showMessage(AlertType.ERROR, HomeActivity.this, s);
            }
        });


        quizViewModel.quizViewModelMutableLiveData.observe(this, new Observer<QuizResponse>() {
            @Override
            public void onChanged(QuizResponse response) {
                quizResponse = response;
                statTimer();
            }
        });

        quizViewModel.getOnSuccess().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {

            }
        });
    }

    private void fetchQuestion() {
        reset();
        quizViewModel.fetchQuiz();
    }

    private void statTimer() {
        remainTime = 15;
        answer = -1;

        Glide.with(this)
                .load(quizResponse.getQuestion())
                .centerCrop()
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.image_error)
                .fallback(R.drawable.image_error)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        // Handle error
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        quizNo++;
                        preferenceManager.setPreference(Constants.QUESTION_NO, quizNo);
                        preferenceManager.setPreference(Constants.SCORE, score);
                        binding.lblQuizNo.setText(getString(R.string.quiz_no) + " " + quizNo);
                        binding.lblScore.setText(getString(R.string.score) + " " + (score * 5));
                        binding.progressBar.setProgress(remainTime);
                        timer.start();
                        return false;
                    }
                })
                .into(binding.imgQuiz);


    }


    private void reset() {
        answer = -1;
        binding.llResults.setVisibility(View.GONE);
        binding.pinLayout.number0.setBackground(getDrawable(R.drawable.pin_button));
        binding.pinLayout.number1.setBackground(getDrawable(R.drawable.pin_button));
        binding.pinLayout.number2.setBackground(getDrawable(R.drawable.pin_button));
        binding.pinLayout.number3.setBackground(getDrawable(R.drawable.pin_button));
        binding.pinLayout.number4.setBackground(getDrawable(R.drawable.pin_button));
        binding.pinLayout.number5.setBackground(getDrawable(R.drawable.pin_button));
        binding.pinLayout.number6.setBackground(getDrawable(R.drawable.pin_button));
        binding.pinLayout.number7.setBackground(getDrawable(R.drawable.pin_button));
        binding.pinLayout.number8.setBackground(getDrawable(R.drawable.pin_button));
        binding.pinLayout.number9.setBackground(getDrawable(R.drawable.pin_button));

    }
}