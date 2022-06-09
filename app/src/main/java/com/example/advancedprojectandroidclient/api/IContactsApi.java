package com.example.advancedprojectandroidclient.api;

import com.example.advancedprojectandroidclient.entities.Contact;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface IContactsApi {

    @GET("Contacts")
    Call<List<Contact>> getContacts(@Header("Authorization") String token);
}
