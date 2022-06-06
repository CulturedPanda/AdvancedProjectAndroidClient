package com.example.advancedprojectandroidclient.api;

import com.example.advancedprojectandroidclient.MyApplication;
import com.example.advancedprojectandroidclient.R;
import com.example.advancedprojectandroidclient.entities.User;

import java.util.concurrent.Executors;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisteredUserApi {

    private Retrofit retrofit;
    private IRegisteredUserApi IRegisteredUserApi;

    public RegisteredUserApi() {
        this.retrofit = new Retrofit.Builder()
                .baseUrl(MyApplication.context.getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .callbackExecutor(Executors.newSingleThreadExecutor())
                .build();
        this.IRegisteredUserApi = retrofit.create(IRegisteredUserApi.class);
    }

    public boolean loginUser(User user) {
        return IRegisteredUserApi.logIn(user);
    }
}
