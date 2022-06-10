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
    Call<Boolean> isAlreadyContactByUsername(@Path("user") String user, @Header("Authorization") String token);

    @GET("RegisteredUsers/doesUserExistByUsername/{username}")
    Call<Boolean> doesUserExistByUsername(@Path("username") String username, @Header("Authorization") String token);

    @POST("Contacts/byEmail")
    Call<Contact> createContactByEmail(@Body Contact contact, @Header("Authorization") String token,
                                          @Query("local") boolean local);

    @GET("Contacts/byEmail/{email}")
    Call<Boolean> isAlreadyContactByEmail(@Path("email") String email, @Header("Authorization") String token);

    @GET("RegisteredUsers/doesUserExistByEmail/{email}")
    Call<Boolean> doesUserExistByEmail(@Path("email") String email, @Header("Authorization") String token);

    @POST("Contacts/byPhone")
    Call<Contact> createContactByPhone(@Body Contact contact, @Header("Authorization") String token,
                                          @Query("local") boolean local);

    @GET("Contacts/alreadyContact/{phone}")
    Call<Boolean> isAlreadyContactByPhone(@Path("phone") String phoneNum, @Header("Authorization") String token);

    @GET("RegisteredUsers/doesUserExistByPhone/{phone}")
    Call<Boolean> doesUserExistByPhone(@Path("phone") String phoneNum, @Header("Authorization") String token);
}
