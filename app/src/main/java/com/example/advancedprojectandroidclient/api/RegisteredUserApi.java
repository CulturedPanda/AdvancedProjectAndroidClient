package com.example.advancedprojectandroidclient.api;

import androidx.lifecycle.MutableLiveData;

import com.example.advancedprojectandroidclient.MyApplication;
import com.example.advancedprojectandroidclient.R;
import com.example.advancedprojectandroidclient.entities.AccessToken;
import com.example.advancedprojectandroidclient.entities.RefreshToken;
import com.example.advancedprojectandroidclient.entities.User;
import com.example.advancedprojectandroidclient.repositories.RefreshTokenRepository;

import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisteredUserApi {

    private final Retrofit retrofit;
    private final IRegisteredUserApi IRegisteredUserApi;
    RefreshTokenRepository refreshTokenRepository;

    public RegisteredUserApi() {
        this.retrofit = new Retrofit.Builder()
                .baseUrl(MyApplication.context.getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .callbackExecutor(Executors.newSingleThreadExecutor())
                .build();
        this.IRegisteredUserApi = retrofit.create(IRegisteredUserApi.class);
        refreshTokenRepository = new RefreshTokenRepository();
    }

    public void loginUser(User user, MutableLiveData<Boolean> loggedIn) {

        Call<AccessToken> call = IRegisteredUserApi.logIn(user);
        call.enqueue(new retrofit2.Callback<AccessToken>() {
            @Override
            public void onResponse(Call<AccessToken> call, retrofit2.Response<AccessToken> response) {
                if (response.isSuccessful()) {
                    RefreshTokenRepository.accessToken = response.body().getAccessToken();
                    RefreshToken refreshToken = new RefreshToken(response.body().getRefreshToken());
                    new Thread(() -> {
                        refreshTokenRepository.deleteRefreshToken();
                        refreshTokenRepository.setRefreshToken(refreshToken);
                    }).start();
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
