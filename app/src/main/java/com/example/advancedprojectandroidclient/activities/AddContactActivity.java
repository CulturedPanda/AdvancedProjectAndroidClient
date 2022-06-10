package com.example.advancedprojectandroidclient.activities;

import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import com.example.advancedprojectandroidclient.R;
import com.example.advancedprojectandroidclient.entities.Contact;
import com.example.advancedprojectandroidclient.view_models.ContactsViewModel;

public class AddContactActivity extends AppCompatActivity {

    private ContactsViewModel contactsViewModel;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        contactsViewModel = new ViewModelProvider(this).get(ContactsViewModel.class);
        String currentUser = getIntent().getStringExtra("username");
        MutableLiveData<Boolean> doesContactExist = new MutableLiveData<>();
        MutableLiveData<Boolean> isAlreadyContact = new MutableLiveData<>();
        MutableLiveData<Boolean> callSuccess = new MutableLiveData<>();
        TextView errorAddingUserTv = findViewById(R.id.add_contact_tv_user_error);

        doesContactExist.observe(this, aBoolean -> {
            if (!aBoolean) {
                errorAddingUserTv.setText(R.string.add_contact_user_does_not_exist);
                errorAddingUserTv.setVisibility(TextView.VISIBLE);
            }
        });

        isAlreadyContact.observe(this, aBoolean -> {
            if (aBoolean) {
                errorAddingUserTv.setText(R.string.add_contact_contact_already_exists);
                errorAddingUserTv.setVisibility(TextView.VISIBLE);
            }
        });

        callSuccess.observe(this, aBoolean -> {
            if (!aBoolean) {
                errorAddingUserTv.setText(R.string.add_contact_something_went_wrong);
                errorAddingUserTv.setVisibility(TextView.VISIBLE);
            }
            else{
                finish();
            }
        });

        Button addContactBtn = findViewById(R.id.add_contact_btn_confirm);
        addContactBtn.setOnClickListener(v -> {
            EditText usernameEt = findViewById(R.id.add_contact_et_user_identification);
            Contact contact = new Contact(usernameEt.getText().toString(), currentUser, null, "abcde", null, null);
            RadioGroup addContactIdentificationChoice = findViewById(R.id.add_contact_rg_username_email_phone);
            RadioButton selected = findViewById(addContactIdentificationChoice.getCheckedRadioButtonId());
            if (selected.getText().equals("Username")) {
                contactsViewModel.addContactByUsername(contact, isAlreadyContact, doesContactExist, callSuccess);
            } else if (selected.getText().equals("Email")) {
                contactsViewModel.addContactByEmail(contact, isAlreadyContact, doesContactExist, callSuccess);
            } else if (selected.getText().equals("Phone number")) {
                contactsViewModel.addContactByPhone(contact, isAlreadyContact, doesContactExist, callSuccess);
            }
        });

        Button cancelBtn = findViewById(R.id.add_contact_btn_close);
        cancelBtn.setOnClickListener(v -> finish());
    }
}