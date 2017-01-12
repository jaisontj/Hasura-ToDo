package com.example.jaison.hasura_todo;

import android.util.Log;
import io.hasura.auth.AuthService;
import io.hasura.db.DBService;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by jaison on 12/01/17.
 */

public class Hasura {

    public static AuthService auth;
    public static DBService db;
    private Integer userId;
    private String sessionId;
    private String role;

    public static Hasura getInstance() {
        return currentCtx;
    }

    public static void setClient() {

        // Ready
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient c = new OkHttpClient.Builder()
                .addInterceptor(new HasuraTokenInterceptor())
                .addInterceptor(logging)
                .build();

        Hasura.auth = new AuthService("https://auth.warble80.hasura-app.io", c);
        Hasura.db = new DBService("https://data.warble80.hasura-app.io/api/1", "", c);
    }

    public static Integer getCurrentUserId() {
        return currentCtx.userId;
    }

    public static String getCurrentRole() {
        return currentCtx.role;
    }

    public static String getCurrentSessionId() {
        return currentCtx.sessionId;
    }

    public static void setCurrentSession(Integer userId, String role, String sessionId) {
        Log.d("{{HASURA :: AUTH", "Setting current session");

        if (role == null) {
            role = "anonymous";
        }

        currentCtx.userId = userId;
        currentCtx.sessionId = sessionId;
        currentCtx.role = role;

        // Reset the current client so that we are logged in
        setClient();
    }

    public static void clearSession() {
        setCurrentSession(-1,null,null);
    }

    private static final Hasura currentCtx = new Hasura();

}
