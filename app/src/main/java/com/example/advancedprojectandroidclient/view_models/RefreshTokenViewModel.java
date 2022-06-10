package com.example.advancedprojectandroidclient.view_models;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.advancedprojectandroidclient.MyApplication;
import com.example.advancedprojectandroidclient.entities.RefreshToken;
import com.example.advancedprojectandroidclient.repositories.RefreshTokenRepository;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class RefreshTokenViewModel extends ViewModel {

    private final RefreshTokenRepository refreshTokenRepository;
    ScheduledExecutorService execService;

    public RefreshTokenViewModel() {
        refreshTokenRepository = MyApplication.refreshTokenRepository;
        execService = Executors.newSingleThreadScheduledExecutor();
    }

    public RefreshToken getRefreshToken() {
        return refreshTokenRepository.getRefreshToken();
    }

    public void refreshTokens(){
        refreshTokenRepository.renewTokens();
    }

    public void setRefreshToken(RefreshToken refreshToken) {
        new Thread(() -> refreshTokenRepository.setRefreshToken(refreshToken)).start();
    }

    public void deleteRefreshToken() {
        new Thread(refreshTokenRepository::deleteRefreshToken).start();
    }

    public void updateRefreshToken(RefreshToken refreshToken) {
        new Thread(() -> refreshTokenRepository.updateRefreshToken(refreshToken)).start();
    }

    public void logInWithAccessToken(MutableLiveData<Boolean> canProceed, MutableLiveData<Boolean> loggedIn) {
        refreshTokenRepository.logInWithAccessToken(canProceed, loggedIn);
    }

    public void beginAutoRefresh() {
        execService.scheduleAtFixedRate(() -> refreshTokenRepository.renewTokens(1, 13),
                3, 4, TimeUnit.MINUTES);
    }
}
