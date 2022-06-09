package com.example.advancedprojectandroidclient.repositories;

import androidx.room.Room;

import com.example.advancedprojectandroidclient.MyApplication;
import com.example.advancedprojectandroidclient.daos.AppDB;
import com.example.advancedprojectandroidclient.daos.RefreshTokenDao;
import com.example.advancedprojectandroidclient.entities.RefreshToken;

public class RefreshTokenRepository {

    private final RefreshTokenDao refreshTokenDao;
    // private ContactApi contactApi;
    private RefreshToken refreshToken;
    public static String accessToken;
    boolean updated;

    public RefreshTokenRepository() {
        AppDB db = Room.databaseBuilder(
                MyApplication.context,
                AppDB.class,
                "app.db"
        ).fallbackToDestructiveMigration().build();
        refreshTokenDao = db.refreshTokenDao();
        new Thread(() -> refreshToken = refreshTokenDao.get()).start();
    }

    public synchronized RefreshToken getRefreshToken() {
        if (updated) {
            refreshToken = refreshTokenDao.get();
            updated = false;
        }
        return refreshToken;
    }

    public synchronized void setRefreshToken(RefreshToken refreshToken) {
        refreshTokenDao.insert(refreshToken);
        updated = true;
    }

    public synchronized void deleteRefreshToken() {
        if (refreshToken != null) {
            refreshTokenDao.delete(refreshToken);
        }
    }

    public synchronized void updateRefreshToken(RefreshToken refreshToken) {
        if (refreshToken != null) {
            refreshTokenDao.update(refreshToken);
        }
    }
}
