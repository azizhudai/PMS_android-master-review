package com.karatascompany.pys3318.session;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by azizmahmud on 11.3.2018.
 */

public class Session {

    private SharedPreferences prefs;

    public Session(Context context){
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }
    public void setUserId (String userId){
        prefs.edit().putString("userId",userId).apply();

    }
    public String getUserId(){
        String userId = prefs.getString("userId","");
        return userId;
    }

    public void setUserMail(String userMail){
        prefs.edit().putString("userMail",userMail).apply();
    }
    public String getUserMail(){
        String userMail = prefs.getString("userMail","");
        return userMail;
    }
}
