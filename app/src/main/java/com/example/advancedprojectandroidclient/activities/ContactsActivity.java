package com.example.advancedprojectandroidclient.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.advancedprojectandroidclient.R;
import com.example.advancedprojectandroidclient.adapters.ContactListAdapter;
import com.example.advancedprojectandroidclient.view_models.ContactsViewModel;

public class ContactsActivity extends AppCompatActivity {

    private ContactsViewModel contactsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        contactsViewModel = new ViewModelProvider(this).get(ContactsViewModel.class);

        RecyclerView lstContacts = findViewById(R.id.contacts_list_rv);
        final ContactListAdapter adapter = new ContactListAdapter(this);
        lstContacts.setLayoutManager(new LinearLayoutManager(this));
        lstContacts.setAdapter(adapter);

        contactsViewModel.getContacts().observe(this, adapter::setContacts);
    }
}