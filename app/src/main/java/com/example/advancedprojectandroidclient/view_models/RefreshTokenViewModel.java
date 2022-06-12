package com.example.advancedprojectandroidclient.view_models;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.advancedprojectandroidclient.MyApplication;
import com.example.advancedprojectandroidclient.entities.RefreshToken;
import com.example.advancedprojectandroidclient.repositories.RefreshTokenRepository;

/**
 * The view model for the refresh token.
 * Links the repository with the views.
 */
public class RefreshTokenViewModel extends ViewModel {

    private final RefreshTokenRepository refreshTokenRepository;

    /**
     * Constructor
     */
    public RefreshTokenViewModel() {
        refreshTokenRepository = MyApplication.refreshTokenRepository;
    }

    /**
     * Gets the refresh token from the repository.
     * @return the refresh token.
     */
    public RefreshToken getRefreshToken() {
        return refreshTokenRepository.getRefreshToken();
    }

    /**
     * Refreshes the tokens from the server.
     */
    public void refreshTokens(){
        refreshTokenRepository.renewTokens();
    }

    /**
     * Sets the refresh token.
     * @param refreshToken the refresh token.
     */
    public void setRefreshToken(RefreshToken refreshToken) {
        new Thread(() -> refreshTokenRepository.setRefreshToken(refreshToken)).start();
    }

    /**
     * Deletes the refresh token.
     */
    public void deleteRefreshToken() {
        new Thread(refreshTokenRepository::deleteRefreshToken).start();
    }

    /**
     * Updates the refresh token.
     * @param refreshToken the refresh token to update.
     */
    public void updateRefreshToken(RefreshToken refreshToken) {
        new Thread(() -> refreshTokenRepository.updateRefreshToken(refreshToken)).start();
    }

    /**
     * Logs in the user via their tokens.
     * @param canProceed Mutable live data for whether it is possible to proceed.
     * @param loggedIn Mutable live data for whether the user is logged in.
     */
    public void logInWithAccessToken(MutableLiveData<Boolean> canProceed, MutableLiveData<Boolean> loggedIn) {
        refreshTokenRepository.logInWithAccessToken(canProceed, loggedIn);
    }

    /**
     * Begins auto refreshing the user's tokens.
     */
    public void beginAutoRefresh() {
        refreshTokenRepository.autoRenewTokens(1, 13);
    }
}
