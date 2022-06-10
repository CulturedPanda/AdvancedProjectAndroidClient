package com.example.advancedprojectandroidclient.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.advancedprojectandroidclient.MyApplication;
import com.example.advancedprojectandroidclient.api.ContactApi;
import com.example.advancedprojectandroidclient.daos.ContactDao;
import com.example.advancedprojectandroidclient.entities.Contact;

import java.util.LinkedList;
import java.util.List;

public class ContactsRepository {

    private final ContactDao contactDao;
    private final ContactApi contactApi;
    private final ContactsData contacts;

    public ContactsRepository() {
        contactDao = MyApplication.appDB.contactDao();
        contacts = new ContactsData();
        contactApi = new ContactApi(contacts, contactDao);
    }

    public void addContactByUsername(Contact contact, MutableLiveData<Boolean> isAlreadyContact,
                           MutableLiveData<Boolean> doesUserExist, MutableLiveData<Boolean> callSuccess) {
        contactApi.addContactByUsername(contact, isAlreadyContact, doesUserExist, callSuccess);
    }

    class ContactsData extends MutableLiveData<List<Contact>> {

        public ContactsData() {
            super();
            LinkedList<Contact> dummyData = new LinkedList<>();
            setValue(dummyData);
        }

        @Override
        protected void onActive() {
            super.onActive();

            new Thread(() -> {
                List<Contact> contactsList = contactDao.getAll();
                contacts.postValue(contactsList);
            }).start();
        }
    }

    public LiveData<List<Contact>> getAll(){
        return contacts;
    }

    public void update(){
        contactApi.getAll();
    }

}
