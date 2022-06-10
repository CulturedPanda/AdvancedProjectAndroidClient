package com.example.advancedprojectandroidclient.repositories;

import androidx.lifecycle.MutableLiveData;

import com.example.advancedprojectandroidclient.MyApplication;
import com.example.advancedprojectandroidclient.api.RefreshTokenApi;
import com.example.advancedprojectandroidclient.daos.RefreshTokenDao;
import com.example.advancedprojectandroidclient.entities.RefreshToken;

public class RefreshTokenRepository {

    private final RefreshTokenDao refreshTokenDao;
    private RefreshTokenApi refreshTokenApi;
    private RefreshToken refreshToken;
    public static String accessToken;
    boolean updated;

    public RefreshTokenRepository() {
//        AppDB db = Room.databaseBuilder(
//                MyApplication.context,
//                AppDB.class,
//                "app.db"
//        ).fallbackToDestructiveMigration().build();
        refreshTokenDao = MyApplication.appDB.refreshTokenDao();
        new Thread(() -> refreshToken = refreshTokenDao.get()).start();
        refreshTokenApi = new RefreshTokenApi(this);
    }

    public synchronized RefreshToken getRefreshToken() {
        if (updated) {
            refreshToken = refreshTokenDao.get();
            updated = false;
        }
        return refreshToken;
    }

    public synchronized void setRefreshToken(RefreshToken refreshToken) {
        this.refreshToken = refreshToken;
        refreshTokenDao.insert(refreshToken);
        updated = true;
    }

    public synchronized void deleteRefreshToken() {
        if (refreshToken != null) {
            refreshTokenDao.delete(refreshToken);
            refreshToken = null;
        }
    }

    public synchronized void updateRefreshToken(RefreshToken refreshToken) {
        if (refreshToken != null) {
            refreshTokenDao.update(refreshToken);
            this.refreshToken = refreshTokenDao.get();
        }
    }

    public synchronized void logInWithAccessToken(MutableLiveData<Boolean> canProceed, MutableLiveData<Boolean> loggedIn){
        if (refreshToken == null){
            canProceed.setValue(false);
            return;
        }
        canProceed.setValue(true);
        refreshTokenApi.loginWithRefreshToken(refreshToken, loggedIn);
    }

    public synchronized void renewTokens(int ... nextAttemptParams) {
        refreshTokenApi.renewTokens(refreshToken, nextAttemptParams);
    }
}
