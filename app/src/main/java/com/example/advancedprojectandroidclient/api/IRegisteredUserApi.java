package com.example.advancedprojectandroidclient.api;

import com.example.advancedprojectandroidclient.entities.User;

import retrofit2.http.POST;

public interface IRegisteredUserApi {

    @POST("/RegisteredUsers")
    boolean logIn(User user);
}
