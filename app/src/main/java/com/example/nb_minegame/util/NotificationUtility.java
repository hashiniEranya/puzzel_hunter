package com.example.nb_minegame.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Toast;

import com.example.nb_minegame.R;

import de.mateware.snacky.Snacky;

public class NotificationUtility {


    public static void showNoInternetMessage(Activity activity) {
//        Toast.makeText(activity, activity.getString(R.string.no_internet_message), Toast.LENGTH_LONG).show();
        showMessage(AlertType.ERROR,activity,activity.getString(R.string.no_internet_message));
    }



    public static void showMessage(AlertType alertType, Activity activity, String message) {
        switch (alertType) {

            case INFO:
                Snacky.builder()
                        .setActivity(activity)
                        .setText(message)
                        .setDuration(3000)
                        .info()
                        .show();
                break;

            case ERROR:
                Snacky.builder()
                        .setActivity(activity)
                        .setText(message)
                        .setDuration(3000)
                        .error()
                        .show();
                break;

            case SUCCESS:
                Snacky.builder()
                        .setActivity(activity)
                        .setText(message)
                        .setDuration(3000)
                        .success()
                        .show();
                break;

            case WARNING:
                Snacky.builder()
                        .setActivity(activity)
                        .setText(message)
                        .setDuration(3000)
                        .warning()
                        .show();
                break;

            default:
                Snacky.builder()
                        .setActivity(activity)
                        .setText(message)
                        .setDuration(3000)
                        .error()
                        .show();
                break;

        }
    }




    public static void showAlertDialog(Context context,
                                       String title,
                                       String message,
                                       String positiveBtnText,
                                       String negativeBtnText,
                                       final DialogInterface.OnClickListener positiveCallBack,
                                       final DialogInterface.OnClickListener negativeCallBack) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(positiveBtnText, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        positiveCallBack.onClick(dialog, which);
                    }
                })
                .setNegativeButton(negativeBtnText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        negativeCallBack.onClick(dialog, which);
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_info)
                .show();
    }

}
