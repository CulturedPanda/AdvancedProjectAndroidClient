package com.example.advancedprojectandroidclient.activities;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.advancedprojectandroidclient.R;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

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
    }
}