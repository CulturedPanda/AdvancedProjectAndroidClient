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

/**
 * This activity is used to verify the email address of the user.
 */
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

        // Single element array because I need to pass an integer by reference
        Integer[] countdown = {60};
        // A terribly hacky way to get the countdown timer to work.
        MutableLiveData<String> countdownText = new MutableLiveData<>();
        countdownText.observe(this, s -> {
            btnResend.setText(s);
            if (s != null && s.equals(getString(R.string.email_verification_resend))) {
                btnResend.setEnabled(true);
                // Reset the countdown timer in case of another call
                countdown[0] = 60;
            }
        });

        // More abuse of mutable live data
        MutableLiveData<Boolean> isSuccessful = new MutableLiveData<>();
        MutableLiveData<Boolean> signUpFinishedSuccessfully = new MutableLiveData<>();
        isSuccessful.observe(this, aBoolean -> {
            if (aBoolean) {
                // If this activity was reached from the forgot password activity and the request succeeded,
                // go to the reset password activity
                if (from.equals("forgotPassword")) {
                    Intent i = new Intent(this, ResetPasswordActivity.class);
                    i.putExtra("username", username);
                    startActivity(i);
                    finish();
                } else {
                    // If from signup, first finish the signup then, on success, open the contacts activity
                    // Also delays it until the server confirms sign up finished.
                    pendingUserApi.finishSignUp(signUpFinishedSuccessfully);
                    signUpFinishedSuccessfully.observe(this, aBoolean1 -> {
                        if (aBoolean1) {
                            // Finish the log in screen so when the user hits back on the contacts screen
                            // it won't go back to log in.
                            MainActivity.fa.finish();
                            Intent i = new Intent(this, ContactsActivity.class);
                            i.putExtra("username", username);
                            startActivity(i);
                            finish();
                        } else {
                            // Will only happen in case of server error
                            btnResend.setError(getString(R.string.add_contact_something_went_wrong));
                        }
                    });
                }
            } else {
                // If the user's code was wrong.
                codeEt.setError(getString(R.string.invalid_code));
            }
        });

        Button btnSubmit = findViewById(R.id.email_verification_submit_btn);
        // Send the user's verification code to the server for verification.
        btnSubmit.setOnClickListener(v -> {
            String code = codeEt.getText().toString();
            if (code.isEmpty()) {
                codeEt.setError(getString(R.string.email_verification_code_empty_error));
            } else if (code.length() != 6) {
                codeEt.setError(getString(R.string.email_verification_code_length_error));
            } else {
                // Make sure the code's format is correct
                Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
                Matcher m = p.matcher(code);
                boolean b = m.find();
                if (b) {
                    codeEt.setError(getString(R.string.email_verification_code_format_error));
                } else {
                    // Send the code to the server via the correct link, depending on context.
                    if (from.equals("forgotPassword")) {
                        registeredUserApi.verifyCode(username, code, isSuccessful);
                    } else {
                        pendingUserApi.verifyCode(username, code, isSuccessful);
                    }
                }
            }
        });

        btnResend.setOnClickListener(v -> {
            // Resend code to the user's email.
            if (from.equals("forgotPassword")) {
                registeredUserApi.renewCode(username);
            } else {
                pendingUserApi.renewCode(username);
            }
            // Begin the countdown, to prevent spamming the reset email button.
            btnResend.setEnabled(false);
            this.timer(countdownText, countdown);
        });
    }

    /**
     * This method is used to start a countdown timer.
     *
     * @param countdownText The MutableLiveData that will be updated with the countdown text.
     * @param remainingTime The integer that will be decremented every second.
     */
    private void timer(MutableLiveData<String> countdownText, Integer[] remainingTime) {
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            // Change the text every second
            if (remainingTime[0] > 0) {
                String displayText = remainingTime[0] + "s";
                countdownText.postValue(displayText);
                remainingTime[0]--;
            } else {
                // Shutdown the thread when the countdown is over
                scheduledExecutorService.shutdown();
                countdownText.postValue(getString(R.string.email_verification_resend));
            }
        }, 0, 1, java.util.concurrent.TimeUnit.SECONDS);
    }
}