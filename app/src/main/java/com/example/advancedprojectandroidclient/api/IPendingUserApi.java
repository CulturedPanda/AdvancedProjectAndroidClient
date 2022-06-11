package com.example.advancedprojectandroidclient.api;

import com.example.advancedprojectandroidclient.entities.PendingUser;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface IPendingUserApi  {

    @GET("PendingUsers/doesPendingUserExistBy{X}/{username}")
    retrofit2.Call<Boolean> doesPendingUserExistByX(@Path("X") String X, @Path("username") String username);

    @POST("PendingUsers")
    retrofit2.Call<Void> createPendingUser(@Body PendingUser pendingUser);

    @PUT("PendingUsers/{username}")
    retrofit2.Call<Void> renewCode(@Path("username") String username);
}
