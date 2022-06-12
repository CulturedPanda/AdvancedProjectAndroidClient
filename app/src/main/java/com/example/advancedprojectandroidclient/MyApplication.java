package com.example.advancedprojectandroidclient;

import android.app.Application;
import android.content.Context;

import androidx.room.Room;

import com.example.advancedprojectandroidclient.daos.AppDB;
import com.example.advancedprojectandroidclient.repositories.ContactsRepository;
import com.example.advancedprojectandroidclient.repositories.MessagesRepository;
import com.example.advancedprojectandroidclient.repositories.RefreshTokenRepository;

/**
 * A class made for convenience, for accessing useful static items.
 */
public class MyApplication extends Application {

    public static Context context;
    public static String username;
    public static AppDB appDB;
    public static MessagesRepository messagesRepository;
    public static ContactsRepository contactsRepository;
    public static RefreshTokenRepository refreshTokenRepository;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        appDB = Room.databaseBuilder(context, AppDB.class, "app.db").fallbackToDestructiveMigration().build();
        messagesRepository = new MessagesRepository();
        contactsRepository = new ContactsRepository();
        refreshTokenRepository = new RefreshTokenRepository();
    }
}
