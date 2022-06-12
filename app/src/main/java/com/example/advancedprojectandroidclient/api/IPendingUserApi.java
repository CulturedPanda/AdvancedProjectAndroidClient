package com.example.advancedprojectandroidclient.api;

import com.example.advancedprojectandroidclient.entities.PendingUser;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * The API for handling pending users.
 */
public interface IPendingUserApi {

    /**
     * Checks if a pending user exists by attribute X
     *
     * @param X         the attribute to check
     * @param attribute the attribute's value.
     * @return true if the user exists, false otherwise.
     */
    @GET("PendingUsers/doesPendingUserExistBy{X}/{attribute}")
    retrofit2.Call<Boolean> doesPendingUserExistByX(@Path("X") String X, @Path("attribute") String attribute);

    /**
     * Adds a new pending user to the server.
     *
     * @param pendingUser the pending user to add.
     */
    @POST("PendingUsers")
    retrofit2.Call<Void> createPendingUser(@Body PendingUser pendingUser);

    /**
     * Renews a pending user's verification code.
     *
     * @param username the username of the pending user.
     */
    @PUT("PendingUsers/{username}")
    retrofit2.Call<Void> renewCode(@Path("username") String username);

    /**
     * Sets a pending user's firebase token in the server.
     *
     * @param username the username of the pending user.
     * @param token    the firebase token.
     */
    @PUT("PendingUsers/setToken/{user}")
    retrofit2.Call<Void> setPhoneToken(@Path("user") String username, @Query("token") String token);
}
