package com.example.advancedprojectandroidclient.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.example.advancedprojectandroidclient.MyApplication;
import com.example.advancedprojectandroidclient.api.ContactApi;
import com.example.advancedprojectandroidclient.daos.AppDB;
import com.example.advancedprojectandroidclient.daos.ContactDao;
import com.example.advancedprojectandroidclient.entities.Contact;

import java.util.LinkedList;
import java.util.List;

public class ContactsRepository {

    private final ContactDao contactDao;
    private final ContactApi contactApi;
    private final ContactsData contacts;

    public ContactsRepository() {
        AppDB db = Room.databaseBuilder(
                MyApplication.context,
                AppDB.class,
                "app.db"
        ).fallbackToDestructiveMigration().allowMainThreadQueries().build();
        contactDao = db.contactDao();
        contacts = new ContactsData();
        contactApi = new ContactApi(contacts, contactDao);
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

}
