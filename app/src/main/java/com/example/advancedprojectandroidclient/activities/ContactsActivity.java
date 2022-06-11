package com.example.advancedprojectandroidclient.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
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
import com.example.advancedprojectandroidclient.daos.ImageDao;
import com.example.advancedprojectandroidclient.entities.Contact;
import com.example.advancedprojectandroidclient.entities.Image;
import com.example.advancedprojectandroidclient.view_models.ContactsViewModel;
import com.example.advancedprojectandroidclient.view_models.MessagesViewModel;
import com.example.advancedprojectandroidclient.view_models.RefreshTokenViewModel;

import java.util.List;

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
        ImageDao imageDao = MyApplication.appDB.imageDao();
        username = getIntent().getStringExtra("username");
        ImageView userProfileImage = findViewById(R.id.contacts_user_img_tv);
        List<Image> image = imageDao.getAllImages();
        if (image != null){
            Image profileImg = image.get(0);

            //decode string to image
            byte[] decodedString = Base64.decode(String.valueOf(profileImg), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        }



        ImageView settingsIv = findViewById(R.id.contacts_settings_iv);
        settingsIv.setOnClickListener(v -> {
            Intent intent = new Intent(this, SettingsActivity.class);
            intent.putExtra("username", username);
            startActivity(intent);
        });

        ImageView logoutIv = findViewById(R.id.contacts_logout_iv);
        logoutIv.setOnClickListener(v -> {
            refreshTokenViewModel.deleteRefreshToken();
            contactsViewModel.deleteAll();
            messagesViewModel.deleteAll();
            registeredUserApi.logOut();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        ImageView addContactIv = findViewById(R.id.contacts_add_contact_iv);
        addContactIv.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddContactActivity.class);
            intent.putExtra("username", username);
            startActivity(intent);
        });

        RecyclerView lstContacts = findViewById(R.id.contacts_list_rv);
        adapter = new ContactListAdapter(this);
        lstContacts.setLayoutManager(new LinearLayoutManager(this));
        lstContacts.setAdapter(adapter);

        contactsViewModel.getContacts(false).observe(this, adapter::setContacts);

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
        super.onResume();
        contactsViewModel.update();
        refreshTokenViewModel.refreshTokens();
        TextView usernameTv = findViewById(R.id.contacts_username_tv);
        MutableLiveData<String> userNickname = new MutableLiveData<>();
        userNickname.observe(this, s -> usernameTv.setText(s));
        new Thread(() -> {registeredUserApi.getNickname(userNickname, username);}).start();
    }
}
