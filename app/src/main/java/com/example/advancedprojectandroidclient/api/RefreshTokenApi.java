package com.example.advancedprojectandroidclient.api;

import androidx.lifecycle.MutableLiveData;

import com.example.advancedprojectandroidclient.MyApplication;
import com.example.advancedprojectandroidclient.R;
import com.example.advancedprojectandroidclient.entities.AccessToken;
import com.example.advancedprojectandroidclient.entities.RefreshToken;
import com.example.advancedprojectandroidclient.repositories.RefreshTokenRepository;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * The implementation of the API for handling refresh tokens.
 */
public class RefreshTokenApi {

    private final Retrofit retrofit;
    private final IRefreshTokenApi refreshTokenApi;
    RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenApi(RefreshTokenRepository refreshTokenRepository) {
        this.retrofit = new Retrofit.Builder()
                .baseUrl(MyApplication.context.getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .callbackExecutor(Executors.newSingleThreadExecutor())
                .build();
        this.refreshTokenApi = retrofit.create(IRefreshTokenApi.class);
        this.refreshTokenRepository = refreshTokenRepository;
    }

    /**
     * Renews a user's refresh token and access token.
     *
     * @param refreshToken      the refresh token to renew
     * @param nextAttemptParams the parameters to use for the next attempt, in case this method fails.
     */
    public void renewTokens(RefreshToken refreshToken, int... nextAttemptParams) {
        Call<AccessToken> call = refreshTokenApi.renewRefreshToken(refreshToken, true);
        call.enqueue(new retrofit2.Callback<AccessToken>() {
            @Override
            public void onResponse(Call<AccessToken> call, retrofit2.Response<AccessToken> response) {
                // Set the new tokens on success.
                if (response.isSuccessful() && response.body() != null) {
                    RefreshTokenRepository.accessToken = response.body().getAccessToken();
                    new Thread(() -> {
                        refreshTokenRepository.deleteRefreshToken();
                        refreshTokenRepository.setRefreshToken(new RefreshToken(response.body().getRefreshToken()));
                    }).start();
                }
            }

            @Override
            public void onFailure(Call<AccessToken> call, Throwable t) {
                // In case of failure, begin a timer to try again in an increasing amount of time with each call.
                if (nextAttemptParams.length > 0) {
                    if (nextAttemptParams[1] > 0) {
                        nextAttemptParams[1]--;
                        nextAttemptParams[0] = nextAttemptParams[0] + 5 * 1000;
                        new Timer().schedule(new TimerTask() {
                            @Override
                            public void run() {
                                renewTokens(refreshToken, nextAttemptParams);
                            }
                        }, nextAttemptParams[0]);
                    }
                }
            }
        });
    }

    /**
     * Logs in with a refresh token instead of username + password.
     *
     * @param refreshToken the refresh token to use for logging in
     * @param loggedIn     the MutableLiveData to update when the login is successful.
     */
    public void loginWithRefreshToken(RefreshToken refreshToken, MutableLiveData<Boolean> loggedIn) {
        Call<AccessToken> call = refreshTokenApi.renewRefreshToken(refreshToken, true);
        call.enqueue(new retrofit2.Callback<AccessToken>() {
            @Override
            public void onResponse(Call<AccessToken> call, retrofit2.Response<AccessToken> response) {
                // Sets the new refresh and access token, as well as the username and logged in status.
                if (response.isSuccessful() && response.body() != null) {
                    RefreshTokenRepository.accessToken = response.body().getAccessToken();
                    new Thread(() -> {
                        refreshTokenRepository.deleteRefreshToken();
                        refreshTokenRepository.setRefreshToken(new RefreshToken(response.body().getRefreshToken()));
                    }).start();
                    MyApplication.username = response.body().getUsername();
                    loggedIn.postValue(true);
                } else {
                    loggedIn.postValue(false);
                }
            }

            @Override
            public void onFailure(Call<AccessToken> call, Throwable t) {
                loggedIn.postValue(false);
            }
        });
    }

}
