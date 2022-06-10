package com.example.advancedprojectandroidclient.api;

import com.example.advancedprojectandroidclient.entities.Contact;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IContactsApi {

    @GET("Contacts")
    Call<List<Contact>> getContacts(@Header("Authorization") String token);

    @POST("Contacts")
    Call<Contact> createContactByUsername(@Body Contact contact, @Header("Authorization") String token,
                                @Query("local") boolean local);

    @GET("Contacts/alreadyContact/{user}")
    Call<Boolean> isAlreadyContact(@Path("user") String user, @Header("Authorization") String token);

    @GET("RegisteredUsers/doesUserExistByUsername/{username}")
    Call<Boolean> doesUserExistByUsername(@Path("username") String username, @Header("Authorization") String token);
}
