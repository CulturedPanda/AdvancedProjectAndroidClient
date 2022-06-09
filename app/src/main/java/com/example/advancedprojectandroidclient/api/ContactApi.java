package com.example.advancedprojectandroidclient.api;

import androidx.lifecycle.MutableLiveData;

import com.example.advancedprojectandroidclient.MyApplication;
import com.example.advancedprojectandroidclient.R;
import com.example.advancedprojectandroidclient.daos.ContactDao;
import com.example.advancedprojectandroidclient.entities.Contact;
import com.example.advancedprojectandroidclient.repositories.RefreshTokenRepository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ContactApi {

    private final Retrofit retrofit;
    private final IContactsApi IContactsApi;
    private final MutableLiveData<List<Contact>> contacts;
    private final ContactDao contactDao;

    public ContactApi(MutableLiveData<List<Contact>> contacts, ContactDao contactDao) {
        this.retrofit = new Retrofit.Builder()
                .baseUrl(MyApplication.context.getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        this.IContactsApi = retrofit.create(IContactsApi.class);
        this.contacts = contacts;
        this.contactDao = contactDao;
    }

    public void getAll(){
        Call<List<Contact>> call = IContactsApi.getContacts("Bearer " + RefreshTokenRepository.accessToken);
        call.enqueue(new retrofit2.Callback<List<Contact>>() {
            @Override
            public void onResponse(Call<List<Contact>> call, retrofit2.Response<List<Contact>> response) {
                if (response.isSuccessful() && response.body() != null && contacts.getValue() != null) {
                    new Thread(() -> {
                        contactDao.deleteAll();
                        for (Contact contact : response.body()) {
                            contactDao.insert(contact);
                        }
                    }).start();
                }
                contacts.postValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Contact>> call, Throwable t) {
                contacts.postValue(contactDao.getAll());
            }
        });
    }
}
