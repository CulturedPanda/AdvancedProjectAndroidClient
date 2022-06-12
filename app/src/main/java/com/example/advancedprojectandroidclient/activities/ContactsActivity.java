package com.example.advancedprojectandroidclient.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.advancedprojectandroidclient.MyApplication;
import com.example.advancedprojectandroidclient.R;
import com.example.advancedprojectandroidclient.adapters.ContactListAdapter;
import com.example.advancedprojectandroidclient.api.RegisteredUserApi;
import com.example.advancedprojectandroidclient.click_listeners.ContactItemClickListener;
import com.example.advancedprojectandroidclient.entities.Contact;
import com.example.advancedprojectandroidclient.entities.Image;
import com.example.advancedprojectandroidclient.view_models.ContactsViewModel;
import com.example.advancedprojectandroidclient.view_models.MessagesViewModel;
import com.example.advancedprojectandroidclient.view_models.RefreshTokenViewModel;

/**
 * This activity is the main activity of the application. It displays the list of contacts, and
 * allows to go into the chat with each of them, as well as go into options, add contacts and logout.
 */
public class ContactsActivity extends AppCompatActivity {

    private ContactsViewModel contactsViewModel;
    private RefreshTokenViewModel refreshTokenViewModel;
    private ContactListAdapter adapter;
    private String username;
    private RegisteredUserApi registeredUserApi;
    private MessagesViewModel messagesViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        getSupportActionBar().hide();
        contactsViewModel = new ViewModelProvider(this).get(ContactsViewModel.class);
        refreshTokenViewModel = new ViewModelProvider(this).get(RefreshTokenViewModel.class);
        messagesViewModel = new ViewModelProvider(this).get(MessagesViewModel.class);
        refreshTokenViewModel.beginAutoRefresh();
        registeredUserApi = new RegisteredUserApi();
        username = getIntent().getStringExtra("username");
        // Yet another terribly hacky way to do stuff.
        MutableLiveData<Image> userImage = new MutableLiveData<>();
        // Sets the user's image to the image view.
        userImage.observe(this, image -> {
            // If the image is not null and its bitmap is not null, then set the image.
            if (image != null) {
                ImageView userProfileImage = findViewById(R.id.contacts_user_img_tv);
                Bitmap bitmap = image.decode();
                if (bitmap != null) {
                    userProfileImage.setImageBitmap(bitmap);
                }
            }
        });

        new Thread(() -> {
            // Get the image from the database and set it to the mutable live data.
            Image image = MyApplication.appDB.imageDao().get(username);
            if (image != null) {
                userImage.postValue(image);
            }
        }).start();


        ImageView settingsIv = findViewById(R.id.contacts_settings_iv);
        // Go to settings when clicking on this image view.
        settingsIv.setOnClickListener(v -> {
            Intent intent = new Intent(this, SettingsActivity.class);
            intent.putExtra("username", username);
            startActivity(intent);
        });

        ImageView logoutIv = findViewById(R.id.contacts_logout_iv);
        // Log out upon clicking this image view.
        logoutIv.setOnClickListener(v -> {
            // Delete all information regarding the user from the local database, as well as their
            // tokens from the server.
            refreshTokenViewModel.deleteRefreshToken();
            contactsViewModel.deleteAll();
            messagesViewModel.deleteAll();
            registeredUserApi.logOut();
            // Go back to log in screen
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        ImageView addContactIv = findViewById(R.id.contacts_add_contact_iv);
        // Go to add contacts activity on click
        addContactIv.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddContactActivity.class);
            intent.putExtra("username", username);
            startActivity(intent);
        });

        // Set the recycler view to display the list of contacts.
        RecyclerView lstContacts = findViewById(R.id.contacts_list_rv);
        adapter = new ContactListAdapter(this);
        lstContacts.setLayoutManager(new LinearLayoutManager(this));
        lstContacts.setAdapter(adapter);

        contactsViewModel.getContacts(false).observe(this, adapter::setContacts);

        // On click on a contact, go to the chat with that contact
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
            }
        }));
    }

    @Override
    protected void onResume() {
        // When resuming, update the contacts list (in case contacts were added by the local user or
        // by another user), as well as the tokens.
        // Also updates the user's nickname in case they changed it.
        super.onResume();
        contactsViewModel.update();
        refreshTokenViewModel.refreshTokens();
        TextView usernameTv = findViewById(R.id.contacts_username_tv);
        MutableLiveData<String> userNickname = new MutableLiveData<>();
        userNickname.observe(this, s -> usernameTv.setText(s));
        new Thread(() -> {
            registeredUserApi.getNickname(userNickname, username);
        }).start();
    }
}
