package com.example.advancedprojectandroidclient.api;

import com.example.advancedprojectandroidclient.entities.AccessToken;
import com.example.advancedprojectandroidclient.entities.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * The API for handling registered users.
 */
public interface IRegisteredUserApi {

    /**
     * Logs a user in.
     *
     * @param user the user to log in.
     * @return an access token containing a refresh token and an access token.
     */
    @POST("RegisteredUsers")
    Call<AccessToken> logIn(@Body User user);

    /**
     * Logs a user in by their email.
     *
     * @param user the user to log in.
     * @return an access token containing a refresh token, an access token and the user's username.
     */
    @POST("RegisteredUsers/emailLogIn")
    Call<AccessToken> logInByEmail(@Body User user);

    /**
     * Edits a user's description.
     *
     * @param newDescription the new description.
     * @param token          the JWT bearer token.
     */
    @PUT("RegisteredUsers/editDescription/{newDescription}")
    Call<Void> editDescription(@Path("newDescription") String newDescription, @Header("Authorization") String token);

    /**
     * Changes a user's server.
     *
     * @param newServer the new server.
     * @param token     the JWT bearer token.
     */
    @PUT("RegisteredUsers/changeServer/{newServer}")
    Call<Void> changeServer(@Path("newServer") String newServer, @Header("Authorization") String token);

    /**
     * Edits a user's nickname
     *
     * @param newNickName the new nickname.
     * @param token       the JWT bearer token.
     */
    @PUT("RegisteredUsers/editNickName/{newNickName}")
    Call<Void> editNickName(@Path("newNickName") String newNickName, @Header("Authorization") String token);

    /**
     * Sets a user's firebase token in the server.
     *
     * @param token      the JWT bearer token.
     * @param phoneToken the firebase token.
     */
    @PUT("RegisteredUsers/setPhoneToken")
    Call<Void> setPhoneToken(@Header("Authorization") String token, @Query("phoneToken") String phoneToken);

    /**
     * Logs out the user.
     *
     * @param token the JWT bearer token.
     */
    @PUT("RegisteredUsers/logOutAndroid")
    Call<Void> logOut(@Header("Authorization") String token);

    /**
     * Verifies a user's secret question when resetting their password.
     *
     * @param username the username of the user.
     * @param question the secret question.
     * @param answer   the answer to the secret question.
     * @return whether the answer is correct.
     */
    @GET("RegisteredUsers/secretQuestion/{username}")
    Call<Boolean> verifySecretQuestion(@Path("username") String username,
                                       @Query("question") String question, @Query("answer") String answer);

    /**
     * Renews a user's email verification code.
     *
     * @param username the username of the user.
     */
    @PUT("RegisteredUsers/renewVerificationCode/{username}")
    Call<Void> renewVerificationCode(@Path("username") String username);

    /**
     * Resets a user's password.
     *
     * @param token the JWT bearer token.
     * @param user  the user to reset.
     */
    @PUT("RegisteredUsers/editPassword")
    Call<Void> resetPassword(@Header("Authorization") String token, @Body User user);
}
