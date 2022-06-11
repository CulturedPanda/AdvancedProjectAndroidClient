package com.example.advancedprojectandroidclient.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;

import com.example.advancedprojectandroidclient.R;
import com.example.advancedprojectandroidclient.api.RegisteredUserApi;


public class ForgotPasswordActivity extends AppCompatActivity {

    private RegisteredUserApi registeredUserApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        //get the spinner of security questions from the xml.
        Spinner questionsList = findViewById(R.id.forgot_pass_security_sp);
        //create a list of questions for the spinner.
        String[] items = new String[]{"What was your elementary's school name?",
                "What is / was your dog's name?",
                "What is your favorite sport?",
                "In what city did you grow up in?",
                "What was your nick-name as a child?",
                "What was your favorite teacher's name?"};
        registeredUserApi = new RegisteredUserApi();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        //set the spinners adapter to the previously created one.
        questionsList.setAdapter(adapter);

        TextView rememberPassTv = findViewById(R.id.forgot_pass_remember_tv);
        rememberPassTv.setOnClickListener(v -> {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        });

        EditText usernameEt = findViewById(R.id.forgot_pass_username_input_et);

        MutableLiveData<Boolean> isSuccessful = new MutableLiveData<>();
        isSuccessful.observe(this, aBoolean -> {
            if (aBoolean) {
                String username = usernameEt.getText().toString();
                registeredUserApi.renewCode(username);
                Intent i = new Intent(this, EmailVerificationActivity.class);
                i.putExtra("username", username);
                i.putExtra("from", "forgotPassword");
                startActivity(i);
                finish();
            }
            else{
                TextView error = findViewById(R.id.forgot_pass_tv_error);
                error.setVisibility(android.view.View.VISIBLE);
            }
        });

        Button forgotPassBtn = findViewById(R.id.forgot_pass_submit_btn);
        forgotPassBtn.setOnClickListener(v -> {
            EditText answerEt = findViewById(R.id.forgot_pass_security_answer_et);
            String username = usernameEt.getText().toString();
            String answer = answerEt.getText().toString();
            if (username.isEmpty() && answer.isEmpty()) {
                usernameEt.setError(getString(R.string.username_is_required));
                answerEt.setError(getString(R.string.answer_is_required));
            }
            else if (answer.isEmpty()){
                answerEt.setError(getString(R.string.answer_is_required));
            }
            else if (username.isEmpty()){
                usernameEt.setError(getString(R.string.username_is_required));
            }
            else {
                String question = Integer.toString(questionsList.getSelectedItemPosition() + 1);
                registeredUserApi.verifySecretQuestion(username, question, answer, isSuccessful);
            }
        });
    }
}