package com.example.advancedprojectandroidclient.view_models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.advancedprojectandroidclient.entities.Contact;
import com.example.advancedprojectandroidclient.repositories.ContactsRepository;

import java.util.List;

public class ContactsViewModel extends ViewModel {

    private final LiveData<List<Contact>> contacts;
    private final ContactsRepository contactsRepository;

    public ContactsViewModel() {
        this.contactsRepository = new ContactsRepository();
        contacts = contactsRepository.getAll();
    }

    public LiveData<List<Contact>> getContacts() {
        return contacts;
    }
}
