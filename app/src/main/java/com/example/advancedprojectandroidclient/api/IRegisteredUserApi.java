package com.example.advancedprojectandroidclient.api;

import com.example.advancedprojectandroidclient.entities.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface IRegisteredUserApi {

    @POST("/RegisteredUsers")
    Call<Boolean> logIn(@Body User user);
}
