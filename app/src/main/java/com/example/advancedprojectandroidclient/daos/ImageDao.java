package com.example.advancedprojectandroidclient.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.advancedprojectandroidclient.entities.Image;

/**
 * Data Access Object for the Image table.
 */
@Dao
public interface ImageDao {

    @Query("SELECT * FROM image WHERE id = :id")
    Image get(String id);

    @Insert
    void insert(Image image);

    @Delete
    void delete(Image image);

    @Update
    void update(Image image);
}
