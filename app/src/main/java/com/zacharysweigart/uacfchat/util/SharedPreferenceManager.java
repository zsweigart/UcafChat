package com.zacharysweigart.uacfchat.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;

public class SharedPreferenceManager {
    private static final String FIREBASE_USER_DATA = "FIREBASE_USER_DATA";

    private SharedPreferences sharedPreferences;
    private Gson gson;

    public SharedPreferenceManager(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        gson = new Gson();
    }

    private  void setPreference(String preferenceKey, Object value) {
        if (value instanceof Boolean) {
            sharedPreferences.edit()
                    .putBoolean(preferenceKey, (Boolean) value)
                    .apply();
        } else if (value instanceof Float) {
            sharedPreferences.edit()
                    .putFloat(preferenceKey, (Float) value)
                    .apply();
        } else if (value instanceof Integer) {
            sharedPreferences.edit()
                    .putInt(preferenceKey, (Integer) value)
                    .apply();
        } else if (value instanceof Long) {
            sharedPreferences.edit()
                    .putLong(preferenceKey, (Long) value)
                    .apply();
        } else if (value instanceof String) {
            sharedPreferences.edit()
                    .putString(preferenceKey, (String) value)
                    .apply();
        } else {
            sharedPreferences.edit()
                    .putString(preferenceKey, gson.toJson(value))
                    .apply();
        }
    }
}

