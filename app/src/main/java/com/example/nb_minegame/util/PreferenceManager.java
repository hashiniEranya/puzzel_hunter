package com.example.nb_minegame.util;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManager {

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Context _context;
    int PRIVATE_MODE = 0;
    private static String PREF_NAME = "MineGame";

    public PreferenceManager(Context context) {
        preferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = preferences.edit();
        _context = context;
    }

    public void setPreference(String paramName, String paramValue) {
        editor.putString(paramName, paramValue);
        editor.commit();
    }

    public void setPreference(String paramName, int paramValue) {
        editor.putInt(paramName, paramValue);
        editor.commit();
    }

    public void setPreference(String paramName, boolean paramValue) {
        editor.putBoolean(paramName, paramValue);
        editor.commit();
    }

    public String getString(String paramName) {
        return preferences.getString(paramName, "");
    }

    public boolean getBoolean(String paramName) {
        return preferences.getBoolean(paramName, false);
    }

    public int getInt(String paramName) {
        return preferences.getInt(paramName, 0);
    }

    public void removePreference(String paramName) {
        editor.remove(paramName);
        editor.commit();
    }

}

