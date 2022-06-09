package com.example.advancedprojectandroidclient.api;

import com.example.advancedprojectandroidclient.entities.AccessToken;
import com.example.advancedprojectandroidclient.entities.RefreshToken;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface IRefreshTokenApi {

    @PUT("RefreshToken")
    Call<AccessToken> renewRefreshToken(@Body RefreshToken refreshToken, @Query("login") boolean login);
}
