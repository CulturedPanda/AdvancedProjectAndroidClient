package com.example.advancedprojectandroidclient.api;

import androidx.lifecycle.MutableLiveData;

import com.example.advancedprojectandroidclient.MyApplication;
import com.example.advancedprojectandroidclient.R;
import com.example.advancedprojectandroidclient.daos.ContactDao;
import com.example.advancedprojectandroidclient.entities.Contact;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ContactApi {

    private Retrofit retrofit;
    private IContactsApi IContactsApi;
    private MutableLiveData<List<Contact>> contacts;
    private ContactDao contactDao;

    public ContactApi(MutableLiveData<List<Contact>> contacts, ContactDao contactDao) {
        this.retrofit = new Retrofit.Builder()
                .baseUrl(MyApplication.context.getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        this.IContactsApi = retrofit.create(IContactsApi.class);
        this.contacts = contacts;
        this.contactDao = contactDao;
    }
}
