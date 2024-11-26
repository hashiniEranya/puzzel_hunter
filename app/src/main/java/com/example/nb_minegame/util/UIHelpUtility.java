package com.example.nb_minegame.util;

import android.app.Activity;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class UIHelpUtility {


    //using this method should be able to hide the soft keyboard
    public static void hideSoftKeyboard(Activity activity) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
                    View view = activity.getCurrentFocus();
                    if (view == null) {
                        view = new View(activity);
                    }
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                } catch (Exception e) {

                }
            }
        }, 1000);
    }

    public static void showBottomSheetDialog(Activity activity, int layout) {
//        View view = getLayoutInflater().inflate(R.layout.bottom_sheet_add_new_card, null);
        View view = activity.getLayoutInflater().inflate(layout, null);

        BottomSheetDialog dialog = new BottomSheetDialog(activity);

        dialog.setContentView(view);
        dialog.show();
    }

    public static void popupBottomSheetDialogFragment(Activity activity, BottomSheetDialogFragment dialogFragment) {
        dialogFragment = new BottomSheetDialogFragment();
        dialogFragment.show(((FragmentActivity) activity).getSupportFragmentManager(), dialogFragment.getTag());
    }

    public static BottomSheetDialog getBottomSheet(Activity activity, int layout) {
//        View view = getLayoutInflater().inflate(R.layout.bottom_sheet_add_new_card, null);
        View view = activity.getLayoutInflater().inflate(layout, null);

        BottomSheetDialog dialog = new BottomSheetDialog(activity);

        dialog.setContentView(view);
        return dialog;
    }

    //this method will help to make activity full screen
    public static void makeActivityFullScreen(Activity activity, AppCompatActivity appCompatActivity) {
        if (appCompatActivity.getSupportActionBar() != null) {
            appCompatActivity.getSupportActionBar().hide();
        }
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    //hide navigation bar
    public static void hideNavigationBar(AppCompatActivity activity) {
        activity.getSupportActionBar().hide();
    }

    //Show soft keyboard and focus a view
    public static void showSoftKeyboard(Activity activity, EditText editText) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                editText.requestFocus();
            }
        }, 100);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
    }

    //using this method user can remove color in status bar and make it transparent
    public static void makeStatusBarTransparent(Activity activity) {
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }


    //this method will hide bottom navigation bar. Note : should call in "onWindowFocusChanged" event of life cycle
    public static void hideSoftNavigationBar(Activity activity, boolean hasFocus) {
        if (hasFocus) {
            activity.getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }


}
