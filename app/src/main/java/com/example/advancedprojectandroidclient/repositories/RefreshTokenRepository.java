package com.example.advancedprojectandroidclient.repositories;

import androidx.room.Room;

import com.example.advancedprojectandroidclient.MyApplication;
import com.example.advancedprojectandroidclient.daos.AppDB;
import com.example.advancedprojectandroidclient.daos.RefreshTokenDao;
import com.example.advancedprojectandroidclient.entities.RefreshToken;

public class RefreshTokenRepository {

    private RefreshTokenDao refreshTokenDao;
    // private ContactApi contactApi;
    private RefreshToken refreshToken;
    boolean updated;

    public RefreshTokenRepository() {
        AppDB db = Room.databaseBuilder(
                MyApplication.context,
                AppDB.class,
                "app.db"
        ).fallbackToDestructiveMigration().allowMainThreadQueries().build();
        refreshTokenDao = db.refreshTokenDao();
        refreshToken = refreshTokenDao.get();
    }

    public RefreshToken getRefreshToken() {
        if (!updated) {
            return refreshToken;
        } else {
            refreshToken = refreshTokenDao.get();
            updated = false;
            return refreshToken;
        }
    }

    public void setRefreshToken(RefreshToken refreshToken) {
        refreshTokenDao.insert(refreshToken);
        updated = true;
    }

    public void deleteRefreshToken() {
        if (refreshToken != null) {
            refreshTokenDao.delete(refreshToken);
        }
    }

    public void updateRefreshToken(RefreshToken refreshToken) {
        if (refreshToken != null) {
            refreshTokenDao.update(refreshToken);
        }
    }
}
