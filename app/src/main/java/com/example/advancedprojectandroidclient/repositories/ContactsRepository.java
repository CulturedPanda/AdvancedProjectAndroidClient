package com.example.advancedprojectandroidclient.repositories;

import androidx.lifecycle.MutableLiveData;

import com.example.advancedprojectandroidclient.api.ContactApi;
import com.example.advancedprojectandroidclient.daos.AppDB;
import com.example.advancedprojectandroidclient.daos.ContactDao;
import com.example.advancedprojectandroidclient.entities.Contact;

import java.util.List;

public class ContactsRepository {

    private final ContactDao contactDao;
    private ContactApi contactApi;
    private MutableLiveData<List<Contact>> contacts;

    private ContactsRepository(AppDB appDB) {
        contactDao = appDB.contactDao();
        contacts = new MutableLiveData<>(contactDao.getAll());
        contactApi = new ContactApi(contacts, contactDao);
    }

}
