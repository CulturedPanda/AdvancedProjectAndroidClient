package com.example.advancedprojectandroidclient.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.advancedprojectandroidclient.MyApplication;
import com.example.advancedprojectandroidclient.R;
import com.example.advancedprojectandroidclient.adapters.ContactListAdapter;
import com.example.advancedprojectandroidclient.click_listeners.ContactItemClickListener;
import com.example.advancedprojectandroidclient.entities.Contact;
import com.example.advancedprojectandroidclient.view_models.ContactsViewModel;
import com.example.advancedprojectandroidclient.view_models.RefreshTokenViewModel;

public class ContactsActivity extends AppCompatActivity {

    private ContactsViewModel contactsViewModel;
    private RefreshTokenViewModel refreshTokenViewModel;
    private ContactListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        contactsViewModel = new ViewModelProvider(this).get(ContactsViewModel.class);
        refreshTokenViewModel = new ViewModelProvider(this).get(RefreshTokenViewModel.class);
        refreshTokenViewModel.beginAutoRefresh();
        String username = getIntent().getStringExtra("nickname");
        TextView usernameTv = findViewById(R.id.contacts_username_tv);
        usernameTv.setText(username);

        ImageView settingsIv = findViewById(R.id.contacts_settings_iv);
        settingsIv.setOnClickListener(v -> {
            Intent intent = new Intent(this, SettingsActivity.class);
            intent.putExtra("username", username);
            startActivity(intent);
        });

        ImageView logoutIv = findViewById(R.id.contacts_logout_iv);
        logoutIv.setOnClickListener(v -> {
            refreshTokenViewModel.deleteRefreshToken();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        RecyclerView lstContacts = findViewById(R.id.contacts_list_rv);
        adapter = new ContactListAdapter(this);
        lstContacts.setLayoutManager(new LinearLayoutManager(this));
        lstContacts.setAdapter(adapter);

        lstContacts.addOnItemTouchListener(new ContactItemClickListener(MyApplication.context, lstContacts, new ContactItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Contact contact = adapter.getContact(position);
                Intent intent = new Intent(ContactsActivity.this, ChatActivity.class);
                intent.putExtra("contactName", contact.getName());
                intent.putExtra("contactId", contact.getId());
                startActivity(intent);
            }

            @Override
            public void onLongItemClick(View view, int position) {
                // do whatever
            }
        }));
    }

    @Override
    protected void onResume() {
        super.onResume();
        contactsViewModel.getContacts(true).observe(this, adapter::setContacts);
        refreshTokenViewModel.refreshTokens();
    }
}
