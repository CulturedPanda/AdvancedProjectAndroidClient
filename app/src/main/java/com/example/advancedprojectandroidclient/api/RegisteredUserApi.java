package com.example.advancedprojectandroidclient.api;

import com.example.advancedprojectandroidclient.MyApplication;
import com.example.advancedprojectandroidclient.R;
import com.example.advancedprojectandroidclient.entities.User;

import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisteredUserApi {

    private Retrofit retrofit;
    private final IRegisteredUserApi IRegisteredUserApi;

    public RegisteredUserApi() {
        this.retrofit = new Retrofit.Builder()
                .baseUrl(MyApplication.context.getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .callbackExecutor(Executors.newSingleThreadExecutor())
                .build();
        this.IRegisteredUserApi = retrofit.create(IRegisteredUserApi.class);
    }

    public void loginUser(User user) {
        Call<Boolean> call =  IRegisteredUserApi.logIn(user);
        call.enqueue(new retrofit2.Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, retrofit2.Response<Boolean> response) {
                user.setLoggedIn(Boolean.TRUE.equals(response.body()));
                if (user.isLoggedIn()){
                    MyApplication.username = user.getUsername();
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                user.setLoggedIn(Boolean.FALSE);
            }
        });
    }
}
