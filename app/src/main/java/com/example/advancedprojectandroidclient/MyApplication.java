package com.example.advancedprojectandroidclient;

import android.app.Application;
import android.content.Context;

import androidx.room.Room;

import com.example.advancedprojectandroidclient.daos.AppDB;

public class MyApplication extends Application {

    public static Context context;
    public static String username;
    public static AppDB appDB;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        appDB = Room.databaseBuilder(context, AppDB.class, "appDB").fallbackToDestructiveMigration().build();
    }
}
