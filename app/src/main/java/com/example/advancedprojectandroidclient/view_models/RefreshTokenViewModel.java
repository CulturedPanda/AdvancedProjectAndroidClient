package com.example.advancedprojectandroidclient.view_models;

import androidx.lifecycle.ViewModel;

import com.example.advancedprojectandroidclient.entities.RefreshToken;
import com.example.advancedprojectandroidclient.repositories.RefreshTokenRepository;

public class RefreshTokenViewModel extends ViewModel {

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenViewModel() {
        refreshTokenRepository = new RefreshTokenRepository();
    }

    public RefreshToken getRefreshToken() {
        return refreshTokenRepository.getRefreshToken();
    }

    public void setRefreshToken(RefreshToken refreshToken) {
        refreshTokenRepository.setRefreshToken(refreshToken);
    }

    public void deleteRefreshToken() {
        refreshTokenRepository.deleteRefreshToken();
    }

    public void updateRefreshToken(RefreshToken refreshToken) {
        refreshTokenRepository.updateRefreshToken(refreshToken);
    }
}
