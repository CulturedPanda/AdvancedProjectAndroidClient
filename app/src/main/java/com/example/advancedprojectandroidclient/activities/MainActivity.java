package com.example.advancedprojectandroidclient.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.advancedprojectandroidclient.R;
import com.example.advancedprojectandroidclient.api.RegisteredUserApi;
import com.example.advancedprojectandroidclient.entities.User;
import com.example.advancedprojectandroidclient.utils.LoginScreenTextWatcher;

public class MainActivity extends AppCompatActivity {

    private RegisteredUserApi registeredUserApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.registeredUserApi = new RegisteredUserApi();

        EditText usernameEt = findViewById(R.id.login_et_username);
        EditText passwordEt = findViewById(R.id.login_et_pwd);
        TextView usernameErrorTv = findViewById(R.id.login_tv_username_empty);
        String errorText = "username " + getResources().getString(R.string.empty);
        usernameErrorTv.setText(errorText);


        usernameEt.addTextChangedListener(new LoginScreenTextWatcher(findViewById(R.id.login_tv_username_empty)));
        passwordEt.addTextChangedListener(new LoginScreenTextWatcher(findViewById(R.id.login_tv_pwd_empty)));

        RadioGroup radioGroup = findViewById(R.id.login_rg_email_or_login);
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton rb = findViewById(checkedId);
            String text = rb.getText().toString();
            usernameEt.setHint(text);
            String errText = text + " " + getResources().getString(R.string.empty);
            usernameErrorTv.setText(errText);
        });

        Button loginBtn = findViewById(R.id.login_btn_login);
        loginBtn.setOnClickListener(v -> {
            String username = usernameEt.getText().toString();
            String password = passwordEt.getText().toString();

            boolean error = false;
            if (username.isEmpty()) {
                usernameErrorTv.setVisibility(TextView.VISIBLE);
                error = true;
            }
            if (password.isEmpty()) {
                TextView errorTv = findViewById(R.id.login_tv_pwd_empty);
                errorTv.setVisibility(TextView.VISIBLE);
                error = true;
            }
            if (error){
                return;
            }

            User user = new User(username, password);
            registeredUserApi.loginUser(user);
            if (user.isLoggedIn()){
                usernameEt.setText("WOOOOOOO");
            }
            else{
                TextView errorTv = findViewById(R.id.login_tv_error);
            }
        });

        TextView signupTv = findViewById(R.id.login_tv_sign_up_link);
        signupTv.setOnClickListener(v -> {
            Intent i = new Intent(this, SignUpActivity.class);
            startActivity(i);
        });
    }
}