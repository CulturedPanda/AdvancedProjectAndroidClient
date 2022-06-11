package com.example.advancedprojectandroidclient.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;

import com.example.advancedprojectandroidclient.R;
import com.example.advancedprojectandroidclient.api.PendingUserApi;
import com.example.advancedprojectandroidclient.api.RegisteredUserApi;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailVerificationActivity extends AppCompatActivity {

    private RegisteredUserApi registeredUserApi;
    private PendingUserApi pendingUserApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_verification);
        String username = getIntent().getStringExtra("username");
        String from = getIntent().getStringExtra("from");
        registeredUserApi = new RegisteredUserApi();
        pendingUserApi = new PendingUserApi();
        Button btnResend = findViewById(R.id.verify_email_btn_resend);
        EditText codeEt = findViewById(R.id.verify_code_input_et);

        Integer[] countdown = {60};
        MutableLiveData<String> countdownText = new MutableLiveData<>();
        countdownText.observe(this, s -> {
            btnResend.setText(s);
            if (s != null && s.equals(getString(R.string.email_verification_resend))) {
                btnResend.setText(s);
                btnResend.setEnabled(true);
                countdown[0] = 60;
            }
        });

        MutableLiveData<Boolean> isSuccessful = new MutableLiveData<>();
        MutableLiveData<Boolean> signUpFinishedSuccessfully = new MutableLiveData<>();
        isSuccessful.observe(this, aBoolean -> {
            if (aBoolean) {
                if (from.equals("forgotPassword")) {
                    Intent i = new Intent(this, ResetPasswordActivity.class);
                    i.putExtra("username", username);
                    startActivity(i);
                    finish();
                } else {
                    pendingUserApi.finishSignUp(signUpFinishedSuccessfully);
                    signUpFinishedSuccessfully.observe(this, aBoolean1 -> {
                        if (aBoolean1) {
                            MainActivity.fa.finish();
                            Intent i = new Intent(this, ContactsActivity.class);
                            i.putExtra("username", username);
                            startActivity(i);
                            finish();
                        }
                        else{
                            btnResend.setError(getString(R.string.add_contact_something_went_wrong));
                        }
                    });
                }
            }
            else{
                codeEt.setError(getString(R.string.invalid_code));
            }
        });

        Button btnSubmit = findViewById(R.id.email_verification_submit_btn);
        btnSubmit.setOnClickListener(v -> {
            String code = codeEt.getText().toString();
            if (code.isEmpty()) {
                codeEt.setError(getString(R.string.email_verification_code_empty_error));
            }
            else if (code.length() != 6){
                codeEt.setError(getString(R.string.email_verification_code_length_error));
            }
            else {
                Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
                Matcher m = p.matcher(code);
                boolean b = m.find();
                if (b) {
                    codeEt.setError(getString(R.string.email_verification_code_format_error));
                }
                else {
                    if (from.equals("forgotPassword")) {
                        registeredUserApi.verifyCode(username, code, isSuccessful);
                    }
                    else{
                        pendingUserApi.verifyCode(username, code, isSuccessful);
                    }
                }
            }
        });

        btnResend.setOnClickListener(v -> {
            if (from.equals("forgotPassword")) {
                registeredUserApi.renewCode(username);
            }
            else {
                pendingUserApi.renewCode(username);
            }
            btnResend.setEnabled(false);
            this.timer(countdownText, countdown);
        });
    }

    private void timer(MutableLiveData<String> countdownText, Integer[] remainingTime) {
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            if (remainingTime[0] > 0) {
            String displayText = remainingTime[0] + "s";
            countdownText.postValue(displayText);
            remainingTime[0]--;
        } else {
            scheduledExecutorService.shutdown();
            countdownText.postValue(getString(R.string.email_verification_resend));
        }
        }, 0, 1, java.util.concurrent.TimeUnit.SECONDS);
    }
}