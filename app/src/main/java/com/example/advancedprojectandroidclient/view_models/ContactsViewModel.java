package com.example.advancedprojectandroidclient.view_models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.advancedprojectandroidclient.MyApplication;
import com.example.advancedprojectandroidclient.entities.Contact;
import com.example.advancedprojectandroidclient.repositories.ContactsRepository;

import java.util.List;

/**
 * The view model for contacts.
 * Links the repository with the view.
 */
public class ContactsViewModel extends ViewModel {

    private LiveData<List<Contact>> contacts;
    private final ContactsRepository contactsRepository;

    /**
     * Constructor
     */
    public ContactsViewModel() {
        this.contactsRepository = MyApplication.contactsRepository;
    }

    /**
     * Gets the contacts from the repository or locally if they are up to date.
     * @param shouldUpdate whether the contacts should be updated from the repository.
     * @return the contacts.
     */
    public synchronized LiveData<List<Contact>> getContacts(boolean shouldUpdate) {
        if (shouldUpdate || contacts == null) {
            contacts = contactsRepository.getAll();
        }
        return contacts;
    }

    /**
     * Gets the contacts repository to update its contacts list.
     */
    public void update() {
        contactsRepository.update();
    }

    /**
     * Adds a contact by their username
     * @param contact         the contact to add
     * @param isAlreadyContact Mutable live data for determining whether the contact is already the user's contact.
     * @param doesUserExist  Mutable live data for determining whether the user exists.
     * @param callSuccess    Mutable live data for determining whether the call was successful.
     */
    public void addContactByUsername(Contact contact, MutableLiveData<Boolean> isAlreadyContact,
                                     MutableLiveData<Boolean> doesUserExist, MutableLiveData<Boolean> callSuccess) {
        contactsRepository.addContactByUsername(contact, isAlreadyContact, doesUserExist, callSuccess);
    }

    /**
     * Adds a contact by their email
     * @param contact        the contact to add
     * @param isAlreadyContact Mutable live data for determining whether the contact is already the user's contact.
     * @param doesUserExist Mutable live data for determining whether the user exists.
     * @param callSuccess  Mutable live data for determining whether the call was successful.
     */
    public void addContactByEmail(Contact contact, MutableLiveData<Boolean> isAlreadyContact,
                                     MutableLiveData<Boolean> doesUserExist, MutableLiveData<Boolean> callSuccess) {
        contactsRepository.addContactByEmail(contact, isAlreadyContact, doesUserExist, callSuccess);
    }

    /**
     * Adds a contact by their phone number.
     * @param contact       the contact to add
     * @param isAlreadyContact Mutable live data for determining whether the contact is already the user's contact.
     * @param doesUserExist Mutable live data for determining whether the user exists.
     * @param callSuccess Mutable live data for determining whether the call was successful.
     */
    public void addContactByPhone(Contact contact, MutableLiveData<Boolean> isAlreadyContact,
                                  MutableLiveData<Boolean> doesUserExist, MutableLiveData<Boolean> callSuccess) {
        contactsRepository.addContactByPhone(contact, isAlreadyContact, doesUserExist, callSuccess);
    }

    /**
     * Deletes all contacts from the local database.
     */
    public void deleteAll(){
        new Thread(contactsRepository::deleteAll).start();
    }
}
