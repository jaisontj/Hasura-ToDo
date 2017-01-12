package com.example.jaison.hasura_todo.db;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by jaison on 12/01/17.
 */

public class SharedPrefHandler {

    static String PREFS_NAME = "PrefsName";
    static String USERID = "userId";
    static String ROLE = "role";
    static String SESSIONID = "sessionId";

    Context context;

    SharedPreferences sharedPreferences;

    public SharedPrefHandler(Context context) {
        this.context = context;
        this.sharedPreferences = context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
    }

    public void setUserData(Integer userId, String role, String sessionId) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(ROLE,role);
        editor.putString(SESSIONID,sessionId);
        editor.putInt(USERID,userId);
        editor.apply();
    }


    public int getUserId() {
        return sharedPreferences.getInt(USERID,-1);
    }

    public String getUserSessionId() {
        return sharedPreferences.getString(SESSIONID,null);
    }

}
