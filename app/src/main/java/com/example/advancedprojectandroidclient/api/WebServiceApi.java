package com.example.advancedprojectandroidclient.api;

import com.example.advancedprojectandroidclient.entities.Contact;

import java.util.List;

import retrofit2.http.GET;

public interface WebServiceApi {

    @GET("/contacts")
    List<Contact> getContacts();
}
