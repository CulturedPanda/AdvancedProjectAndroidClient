package com.example.advancedprojectandroidclient.api;

import com.example.advancedprojectandroidclient.entities.Message;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IMessageApi {

    @GET("Contacts/{id}/messages")
    Call<List<Message>> getMessages(@Path("id") String id, @Header("Authorization") String token);

    @POST("Contacts/{id}/messages")
    Call<Message> addMessage(@Path("id") String id, @Header("Authorization") String token, @Body Message message);

}
