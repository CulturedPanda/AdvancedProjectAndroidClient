package com.example.advancedprojectandroidclient;

import android.app.Application;
import android.content.Context;

public class MyApplication extends Application {

    public static Context context;
    public static String username;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }
}
