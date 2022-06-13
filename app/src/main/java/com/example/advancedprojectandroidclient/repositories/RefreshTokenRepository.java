package com.example.advancedprojectandroidclient.repositories;

import androidx.lifecycle.MutableLiveData;

import com.example.advancedprojectandroidclient.MyApplication;
import com.example.advancedprojectandroidclient.api.RefreshTokenApi;
import com.example.advancedprojectandroidclient.daos.RefreshTokenDao;
import com.example.advancedprojectandroidclient.entities.RefreshToken;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * The repository for the refresh token table.
 * Manages the database operations for the refresh token table as well as contacting the server for
 * refresh token operations.
 */
public class RefreshTokenRepository {

    private final RefreshTokenDao refreshTokenDao;
    private final RefreshTokenApi refreshTokenApi;
    private RefreshToken refreshToken;
    public static String accessToken;
    private final ScheduledExecutorService execService;
    private boolean autoRefreshing;
    boolean updated;

    /**
     * Constructor
     */
    public RefreshTokenRepository() {
        refreshTokenDao = MyApplication.appDB.refreshTokenDao();
        new Thread(() -> refreshToken = refreshTokenDao.get()).start();
        refreshTokenApi = new RefreshTokenApi(this);
        execService = Executors.newSingleThreadScheduledExecutor();
        autoRefreshing = false;
    }

    /**
     * Getter for the refresh token. Also updates it if needed.
     *
     * @return the refresh token.
     */
    public synchronized RefreshToken getRefreshToken() {
        if (updated) {
            // This used to not be on a separate thread, yet it worked.
            // Meaning this never happened, but..... just to make sure I kept it here and on a separate thread.
            new Thread(() -> {
                refreshToken = refreshTokenDao.get();
            }).start();
            updated = false;
        }
        return refreshToken;
    }

    /**
     * Setter for the refresh token. Should always be called on a seperate thread.
     *
     * @param refreshToken the new refresh token.
     */
    public synchronized void setRefreshToken(RefreshToken refreshToken) {
        this.refreshToken = refreshToken;
        refreshTokenDao.insert(refreshToken);
        updated = true;
    }

    /**
     * Deletes the refresh token and sets it to null.
     * Should always be called on a separate thread.
     */
    public synchronized void deleteRefreshToken() {
        if (refreshToken != null) {
            refreshTokenDao.delete(refreshToken);
            refreshToken = null;
        }
    }

    /**
     * Updates the refresh token.
     * Should always be called on a separate thread.
     *
     * @param refreshToken the new refresh token.
     */
    public synchronized void updateRefreshToken(RefreshToken refreshToken) {
        if (refreshToken != null) {
            refreshTokenDao.update(refreshToken);
            this.refreshToken = refreshTokenDao.get();
        }
    }

    /**
     * Logs in with the refresh token.
     *
     * @param canProceed MutableLiveData for whether or not it is possible to proceed.
     * @param loggedIn   MutableLiveData for whether or not the user is logged in.
     */
    public synchronized void logInWithAccessToken(MutableLiveData<Boolean> canProceed, MutableLiveData<Boolean> loggedIn) {
        if (refreshToken == null) {
            canProceed.setValue(false);
            return;
        }
        canProceed.setValue(true);
        refreshTokenApi.loginWithRefreshToken(refreshToken, loggedIn);
    }

    /**
     * Renews the user's tokens.
     *
     * @param nextAttemptParams integer values for next attempt time and maximum number of attempts, if needed.
     */
    public synchronized void renewTokens(int... nextAttemptParams) {
        refreshTokenApi.renewTokens(refreshToken, nextAttemptParams);
    }

    /**
     * Starts automatically renewing a user's tokens.
     *
     * @param i  the next attempt time.
     * @param i1 maximum number of attempts.
     */
    public synchronized void autoRenewTokens(int i, int i1) {
        if (!autoRefreshing) {
            execService.scheduleAtFixedRate(() -> renewTokens(i, i1),
                    10, 4, TimeUnit.MINUTES);
            autoRefreshing = true;
        }
    }
}
