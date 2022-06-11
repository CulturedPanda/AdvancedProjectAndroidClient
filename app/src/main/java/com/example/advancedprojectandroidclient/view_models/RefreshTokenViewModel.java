package com.example.advancedprojectandroidclient.view_models;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.advancedprojectandroidclient.MyApplication;
import com.example.advancedprojectandroidclient.entities.RefreshToken;
import com.example.advancedprojectandroidclient.repositories.RefreshTokenRepository;

public class RefreshTokenViewModel extends ViewModel {

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenViewModel() {
        refreshTokenRepository = MyApplication.refreshTokenRepository;
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
        refreshTokenRepository.autoRenewTokens(1, 13);
    }
}
