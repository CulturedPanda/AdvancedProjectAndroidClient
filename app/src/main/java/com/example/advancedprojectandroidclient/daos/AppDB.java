package com.example.advancedprojectandroidclient.daos;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.advancedprojectandroidclient.entities.Contact;
import com.example.advancedprojectandroidclient.entities.Message;

@Database(entities = {Contact.class, Message.class}, version = 5)
public abstract class AppDB extends RoomDatabase {
    public abstract ContactDao contactDao();
    public abstract MessageDao messageDao();
}
