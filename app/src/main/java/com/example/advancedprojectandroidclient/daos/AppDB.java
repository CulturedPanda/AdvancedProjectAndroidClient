package com.example.advancedprojectandroidclient.daos;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.advancedprojectandroidclient.entities.Contact;
import com.example.advancedprojectandroidclient.entities.Message;
import com.example.advancedprojectandroidclient.entities.RefreshToken;

@Database(entities = {Contact.class, Message.class, RefreshToken.class}, version = 8)
public abstract class AppDB extends RoomDatabase {
    public abstract ContactDao contactDao();
    public abstract MessageDao messageDao();
    public abstract RefreshTokenDao refreshTokenDao();
}
