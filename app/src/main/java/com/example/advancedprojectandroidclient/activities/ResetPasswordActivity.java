package com.example.advancedprojectandroidclient.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;

import com.example.advancedprojectandroidclient.R;
import com.example.advancedprojectandroidclient.api.RegisteredUserApi;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ResetPasswordActivity extends AppCompatActivity {

    private RegisteredUserApi registeredUserApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        registeredUserApi = new RegisteredUserApi();
        EditText passwordEt = findViewById(R.id.reset_pass_et);
        EditText passwordConfirmEt = findViewById(R.id.reset_pass_confirm_et);
        Button resetBtn = findViewById(R.id.reset_pass_btn);

        MutableLiveData<Boolean> isSuccessful = new MutableLiveData<>();
        isSuccessful.observe(this, aBoolean -> {
            if (aBoolean) {
                Intent i = new Intent(this, MainActivity.class);
                i.putExtra("resetPassword", true);
                startActivity(i);
                finish();
            }
            else{
            }
                resetBtn.setError(getString(R.string.server_error));
            });


        resetBtn.setOnClickListener(v -> {
            String password = passwordEt.getText().toString();
            String passwordConfirm = passwordConfirmEt.getText().toString();
            if (password.length() < 8){
                passwordEt.setError(getString(R.string.password_len_error));
            }
            else{
                Pattern p = Pattern.compile("(?=[A-Za-z0-9@#$%^&+!=]+$)^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[@#$%^&+!=])(?=.{8,}).*$");
                Matcher m = p.matcher(password);
                boolean b = m.find();
                if (!b){
                    passwordEt.setError(getString(R.string.password_format_error));
                }
                else if (!password.equals(passwordConfirm)) {
                    passwordEt.setError(getString(R.string.password_match_error));
                    passwordConfirmEt.setError(getString(R.string.password_match_error));
                }
                else{
                    registeredUserApi.resetPassword(password, isSuccessful);
                }
            }
        });
    }
}