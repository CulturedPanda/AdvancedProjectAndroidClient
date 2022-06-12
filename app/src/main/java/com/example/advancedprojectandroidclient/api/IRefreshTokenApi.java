package com.example.advancedprojectandroidclient.api;

import com.example.advancedprojectandroidclient.entities.AccessToken;
import com.example.advancedprojectandroidclient.entities.RefreshToken;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.PUT;
import retrofit2.http.Query;

/**
 * The API for handling refresh tokens.
 */
public interface IRefreshTokenApi {

    /**
     * Renews a user's tokens.
     *
     * @param refreshToken the refresh token.
     * @param login        whether or not this a login request.
     * @return An access token containing a new refresh token, a new access token, and if login was true,
     * also the user's username on success.
     */
    @PUT("RefreshToken")
    Call<AccessToken> renewRefreshToken(@Body RefreshToken refreshToken, @Query("login") boolean login);
}
