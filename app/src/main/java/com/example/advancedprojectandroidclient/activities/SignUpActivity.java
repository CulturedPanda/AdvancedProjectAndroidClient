package com.example.advancedprojectandroidclient.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.advancedprojectandroidclient.R;

public class SignUpActivity extends AppCompatActivity {

    int SELECT_IMAGE_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Button btn = findViewById(R.id.sign_up_choose_img_btn);
        btn.setOnClickListener(v -> {
            Intent i = new Intent();
            i.setType("image/*");
            i.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(i, "Title"),SELECT_IMAGE_CODE);
        });

        //get the spinner of security questions from the xml.
        Spinner questionsList = findViewById(R.id.securitySpinner);
        //create a list of questions for the spinner.
        String[] items = new String[]{"What was your elementary's school name?",
                "What is / was your dog's name?",
                "What is your favorite sport?",
                "In what city did you grow up in?",
                "What was your nick-name as a child?",
                "What was your favorite teacher's name?"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        //set the spinners adapter to the previously created one.
        questionsList.setAdapter(adapter);

        Button btnSignUp = findViewById(R.id.sign_up_btn);
        btnSignUp.setOnClickListener(v -> {
            Intent i = new Intent(this, EmailVerificationActivity.class);
            startActivity(i);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1){
            Uri uri = data.getData();
            ImageView profileImg = findViewById(R.id.sign_up_profile_pic_iv);
            profileImg.setImageURI(uri);

        }
    }
}