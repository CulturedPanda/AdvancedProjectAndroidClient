package com.example.advancedprojectandroidclient.daos;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.advancedprojectandroidclient.entities.Contact;

@Database(entities = {Contact.class}, version = 2)
public abstract class AppDB extends RoomDatabase {
    public abstract ContactDao contactDao();
}
