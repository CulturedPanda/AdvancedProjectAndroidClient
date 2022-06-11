package com.example.advancedprojectandroidclient.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.advancedprojectandroidclient.entities.Image;

import java.util.List;

@Dao
public interface ImageDao {

    @Query("SELECT * FROM image")
    List<Image> getAllImages();


    @Query("SELECT * FROM image WHERE id = :id")
    Image get(int id);

    @Insert
    void insert(Image image);

    @Delete
    void delete(Image image);

    @Update
    void update(Image image);
}
