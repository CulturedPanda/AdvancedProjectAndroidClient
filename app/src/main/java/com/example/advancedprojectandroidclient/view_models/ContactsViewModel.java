//package com.example.advancedprojectandroidclient.view_models;
//
//import androidx.lifecycle.LiveData;
//import androidx.lifecycle.ViewModel;
//
//import com.example.advancedprojectandroidclient.daos.AppDB;
//import com.example.advancedprojectandroidclient.daos.ContactDao;
//import com.example.advancedprojectandroidclient.entities.Contact;
//
//import java.util.List;
//
//public class ContactsViewModel extends ViewModel {
//
//    private LiveData<List<Contact>> contacts;
//
//    public ContactsViewModel(ContactsRepository contactsRepository) {
//        contacts = contactsRepository.getAll();
//    }
//
//    public LiveData<List<Contact>> getContacts() {
//        return contacts;
//    }
//}
