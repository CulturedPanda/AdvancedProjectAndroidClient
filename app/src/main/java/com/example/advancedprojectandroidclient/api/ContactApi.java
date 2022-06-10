package com.example.advancedprojectandroidclient.api;

import android.os.Build;

import androidx.annotation.RequiresApi;
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
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<List<Contact>> call, retrofit2.Response<List<Contact>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Making sure the date is parsed correctly before displaying to the user.
                    for (Contact contact : response.body()) {
                        contact.setLastdate(Contact.parseDate(contact.getLastdate()));
                    }
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

    public void addContactByUsername(Contact contact, MutableLiveData<Boolean> isAlreadyContact,
                                     MutableLiveData<Boolean> doesContactExist, MutableLiveData<Boolean> callSuccess){
        Call<Boolean> call = IContactsApi.doesUserExistByUsername(contact.getId(), "Bearer " + RefreshTokenRepository.accessToken);
        call.enqueue(new retrofit2.Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, retrofit2.Response<Boolean> response) {
                if (response.isSuccessful() && response.body() != null) {
                    doesContactExist.postValue(response.body());
                    if (response.body()) {
                        Call<Boolean> call2 = IContactsApi.isAlreadyContact(contact.getId(), "Bearer " + RefreshTokenRepository.accessToken);
                        call2.enqueue(new retrofit2.Callback<Boolean>() {
                            @Override
                            public void onResponse(Call<Boolean> call, retrofit2.Response<Boolean> response) {
                                if (response.isSuccessful() && response.body() != null) {
                                    isAlreadyContact.postValue(response.body());
                                    if (!response.body()){
                                        Call<Contact> call3 = IContactsApi.createContactByUsername(contact,
                                                "Bearer " + RefreshTokenRepository.accessToken, true);
                                        call3.enqueue(new retrofit2.Callback<Contact>() {
                                            @Override
                                            public void onResponse(Call<Contact> call, retrofit2.Response<Contact> response) {
                                                if (response.isSuccessful()) {
                                                    callSuccess.postValue(true);
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<Contact> call, Throwable t) {
                                                callSuccess.postValue(false);
                                            }
                                        });
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<Boolean> call, Throwable t) {
                                callSuccess.postValue(true);
                            }
                        });
                    }
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                callSuccess.postValue(false);
            }
        });
    }
}
