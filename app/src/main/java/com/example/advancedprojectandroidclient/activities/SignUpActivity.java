package com.example.advancedprojectandroidclient.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.advancedprojectandroidclient.R;
import com.example.advancedprojectandroidclient.daos.AppDB;
import com.example.advancedprojectandroidclient.daos.ImageDao;

public class SignUpActivity extends AppCompatActivity {
    private AppDB db;
    private ImageDao imageDao;
    Bitmap bmpImage;
    ImageView imageView;

    int SELECT_IMAGE_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        bmpImage = null;
        imageView = findViewById(R.id.sign_up_profile_pic_iv);
        //Create db for profile image
        db = Room.databaseBuilder(getApplicationContext(),AppDB.class, "ImageDB").
                allowMainThreadQueries().build();
        imageDao= db.imageDao();



        //Choose profile img button and display in box
        Button btnImage = findViewById(R.id.sign_up_choose_img_btn);
        btnImage.setOnClickListener(v -> {
            Intent i = new Intent();
            i.setType("image/*");
            i.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(i, "Title"),SELECT_IMAGE_CODE);

            //
            imageView = findViewById(R.id.sign_up_profile_pic_iv);
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

        //sign up button
        Button btnSignUp = findViewById(R.id.sign_up_btn);
        btnSignUp.setOnClickListener(v -> {
            Intent i = new Intent(this, EmailVerificationActivity.class);
            startActivity(i);
        });

        //already have account button
        TextView alreadySignUp = findViewById(R.id.sign_up_already_tv);
        alreadySignUp.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1){
            Uri uri = data.getData();
            ImageView profileImg = findViewById(R.id.sign_up_profile_pic_iv);
            profileImg.setImageURI(uri);
            bmpImage = (Bitmap) data.getExtras().get("data");
            if (bmpImage != null){
                imageView.setImageBitmap(bmpImage);
            }

        }
    }
}