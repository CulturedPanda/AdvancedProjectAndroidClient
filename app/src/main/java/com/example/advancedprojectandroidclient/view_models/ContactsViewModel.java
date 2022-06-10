package com.example.advancedprojectandroidclient.view_models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.advancedprojectandroidclient.MyApplication;
import com.example.advancedprojectandroidclient.entities.Contact;
import com.example.advancedprojectandroidclient.repositories.ContactsRepository;

import java.util.List;

public class ContactsViewModel extends ViewModel {

    private LiveData<List<Contact>> contacts;
    private final ContactsRepository contactsRepository;

    public ContactsViewModel() {
        this.contactsRepository = MyApplication.contactsRepository;
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

    public void addContactByUsername(Contact contact, MutableLiveData<Boolean> isAlreadyContact,
                                     MutableLiveData<Boolean> doesUserExist, MutableLiveData<Boolean> callSuccess) {
        contactsRepository.addContactByUsername(contact, isAlreadyContact, doesUserExist, callSuccess);
    }

    public void addContactByEmail(Contact contact, MutableLiveData<Boolean> isAlreadyContact,
                                     MutableLiveData<Boolean> doesUserExist, MutableLiveData<Boolean> callSuccess) {
        contactsRepository.addContactByEmail(contact, isAlreadyContact, doesUserExist, callSuccess);
    }

    public void addContactByPhone(Contact contact, MutableLiveData<Boolean> isAlreadyContact,
                                  MutableLiveData<Boolean> doesUserExist, MutableLiveData<Boolean> callSuccess) {
        contactsRepository.addContactByPhone(contact, isAlreadyContact, doesUserExist, callSuccess);
    }

    public void deleteAll(){
        new Thread(contactsRepository::deleteAll).start();
    }
}
