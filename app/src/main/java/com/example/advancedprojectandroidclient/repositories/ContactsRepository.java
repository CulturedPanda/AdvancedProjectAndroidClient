package com.example.advancedprojectandroidclient.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.advancedprojectandroidclient.MyApplication;
import com.example.advancedprojectandroidclient.api.ContactApi;
import com.example.advancedprojectandroidclient.daos.ContactDao;
import com.example.advancedprojectandroidclient.entities.Contact;

import java.util.LinkedList;
import java.util.List;

/**
 * The repository for the contacts table.
 * Manages the database operations for the contacts table as well as contacting the server for contacts.
 */
public class ContactsRepository {

    private final ContactDao contactDao;
    private final ContactApi contactApi;
    private final ContactsData contacts;

    /**
     * Constructor
     */
    public ContactsRepository() {
        contactDao = MyApplication.appDB.contactDao();
        contacts = new ContactsData();
        contactApi = new ContactApi(contacts, contactDao);
    }

    /**
     * Adds a contact by their username
     *
     * @param contact          the contact to add
     * @param isAlreadyContact Mutable live data for determining whether the contact is already the user's contact.
     * @param doesUserExist    Mutable live data for determining whether the user exists.
     * @param callSuccess      Mutable live data for determining whether the call was successful.
     */
    public synchronized void addContactByUsername(Contact contact, MutableLiveData<Boolean> isAlreadyContact,
                                     MutableLiveData<Boolean> doesUserExist, MutableLiveData<Boolean> callSuccess) {
        contactApi.addContactByUsername(contact, isAlreadyContact, doesUserExist, callSuccess);
    }

    /**
     * Adds a contact by their email
     *
     * @param contact          the contact to add
     * @param isAlreadyContact Mutable live data for determining whether the contact is already the user's contact.
     * @param doesUserExist    Mutable live data for determining whether the user exists.
     * @param callSuccess      Mutable live data for determining whether the call was successful.
     */
    public synchronized void addContactByEmail(Contact contact, MutableLiveData<Boolean> isAlreadyContact,
                                  MutableLiveData<Boolean> doesUserExist, MutableLiveData<Boolean> callSuccess) {
        contactApi.addContactByEmail(contact, isAlreadyContact, doesUserExist, callSuccess);
    }

    /**
     * Adds a contact by their phone number.
     *
     * @param contact          the contact to add
     * @param isAlreadyContact Mutable live data for determining whether the contact is already the user's contact.
     * @param doesUserExist    Mutable live data for determining whether the user exists.
     * @param callSuccess      Mutable live data for determining whether the call was successful.
     */
    public synchronized void addContactByPhone(Contact contact, MutableLiveData<Boolean> isAlreadyContact,
                                  MutableLiveData<Boolean> doesUserExist, MutableLiveData<Boolean> callSuccess) {
        contactApi.addContactByPhone(contact, isAlreadyContact, doesUserExist, callSuccess);
    }

    /**
     * Deletes all contacts from the table.
     */
    public synchronized void deleteAll() {
        contactDao.deleteAll();
    }

    /**
     * An extension of MutableLiveData that contains a list of contacts, and loads up the list from the database
     * once active.
     */
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

    /**
     * Getter for the contacts list
     *
     * @return the contacts list
     */
    public LiveData<List<Contact>> getAll() {
        return contacts;
    }

    /**
     * Updates the contacts list from the server.
     */
    public synchronized void update() {
        contactApi.getAll();
    }

}
