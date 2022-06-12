package com.example.advancedprojectandroidclient.api;

import com.example.advancedprojectandroidclient.entities.Message;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * The API for handling messages.
 */
public interface IMessageApi {

    /**
     * Gets all the messages with a particular user
     *
     * @param id    the id of the user
     * @param token the JWT bearer token.
     * @return a list of messages
     */
    @GET("Contacts/{id}/messages")
    Call<List<Message>> getMessages(@Path("id") String id, @Header("Authorization") String token);

    /**
     * Sends a message to a user
     *
     * @param id      the id of the user
     * @param token   the JWT bearer token.
     * @param message the message to send
     * @return the message that was sent
     */
    @POST("Contacts/{id}/messages")
    Call<Message> addMessage(@Path("id") String id, @Header("Authorization") String token, @Body Message message);

}
