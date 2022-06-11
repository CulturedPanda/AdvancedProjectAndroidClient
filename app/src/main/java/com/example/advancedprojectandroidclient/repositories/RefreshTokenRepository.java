package com.example.advancedprojectandroidclient.repositories;

import androidx.lifecycle.MutableLiveData;

import com.example.advancedprojectandroidclient.MyApplication;
import com.example.advancedprojectandroidclient.api.RefreshTokenApi;
import com.example.advancedprojectandroidclient.daos.RefreshTokenDao;
import com.example.advancedprojectandroidclient.entities.RefreshToken;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class RefreshTokenRepository {

    private final RefreshTokenDao refreshTokenDao;
    private final RefreshTokenApi refreshTokenApi;
    private RefreshToken refreshToken;
    public static String accessToken;
    private final ScheduledExecutorService execService;
    boolean updated;

    public RefreshTokenRepository() {
        refreshTokenDao = MyApplication.appDB.refreshTokenDao();
        new Thread(() -> refreshToken = refreshTokenDao.get()).start();
        refreshTokenApi = new RefreshTokenApi(this);
        execService = Executors.newSingleThreadScheduledExecutor();
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

    public void autoRenewTokens(int i, int i1) {
        execService.scheduleAtFixedRate(() -> renewTokens(i, i1),
                3, 4, TimeUnit.MINUTES);
    }
}
