package com.example.advancedprojectandroidclient.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.advancedprojectandroidclient.entities.RefreshToken;

/**
 * Data Access Object for the RefreshToken table.
 */
@Dao
public interface RefreshTokenDao {

    @Insert
    void insert(RefreshToken refreshToken);

    @Delete
    void delete(RefreshToken refreshToken);

    @Query("SELECT * FROM refresh_token")
    RefreshToken get();

    @Update
    void update(RefreshToken refreshToken);
}
