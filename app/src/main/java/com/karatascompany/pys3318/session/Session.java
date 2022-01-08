package com.karatascompany.pys3318.session;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by azizmahmud on 11.3.2018.
 */

public class Session {

    private final SharedPreferences prefs;

    public Session(Context context) {
        prefs = context.getSharedPreferences("MyPrivatePrefApp", 0); //PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void setUserId(String userId) {
        prefs.edit().putString("userId", userId).apply();
    }

    public String getUserId() {
        return prefs.getString("userId", "");
    }

    public void setUserMail(String userMail) {
        prefs.edit().putString("userMail", userMail).apply();
    }

    public String getUserMail() {
        return prefs.getString("userMail", "");
    }

    public void removeSession(Context context) {
        SharedPreferences mySPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove("MyPrivatePrefApp");
        editor.apply();
    }

    public boolean checkSession() {
        return !getUserId().isEmpty() && !getUserMail().isEmpty();
    }
}
