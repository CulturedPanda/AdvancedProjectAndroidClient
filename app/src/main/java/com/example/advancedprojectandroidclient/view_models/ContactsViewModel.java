package com.example.advancedprojectandroidclient.view_models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.advancedprojectandroidclient.entities.Contact;
import com.example.advancedprojectandroidclient.repositories.ContactsRepository;

import java.util.List;

public class ContactsViewModel extends ViewModel {

    private LiveData<List<Contact>> contacts;
    private final ContactsRepository contactsRepository;

    public ContactsViewModel() {
        this.contactsRepository = new ContactsRepository();
    }

    public synchronized LiveData<List<Contact>> getContacts(boolean shouldUpdate) {
        if (shouldUpdate || contacts == null) {
            contacts = contactsRepository.getAll();
        }
        return contacts;
    }

    public void update() {
        contactsRepository.update();
    }

    public void addContact(Contact contact, MutableLiveData<Boolean> isAlreadyContact,
                           MutableLiveData<Boolean> doesUserExist, MutableLiveData<Boolean> callSuccess) {
        contactsRepository.addContactByUsername(contact, isAlreadyContact, doesUserExist, callSuccess);
    }
}
