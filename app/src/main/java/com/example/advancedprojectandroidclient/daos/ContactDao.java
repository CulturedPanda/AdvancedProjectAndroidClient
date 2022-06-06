package com.example.advancedprojectandroidclient.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.advancedprojectandroidclient.entities.Contact;

import java.util.List;

@Dao
public interface ContactDao {

    @Query("SELECT * FROM contact")
    public List<Contact> getAll();

    @Query("SELECT * FROM contact WHERE id = :id")
    public Contact getById(String id);

    @Insert
    public void insert(Contact contact);

    @Delete
    public void delete(Contact contact);

    @Update
    public void update(Contact contact);
}
