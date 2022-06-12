package com.example.advancedprojectandroidclient.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import com.example.advancedprojectandroidclient.MyApplication;
import com.example.advancedprojectandroidclient.R;
import com.example.advancedprojectandroidclient.api.RegisteredUserApi;
import com.example.advancedprojectandroidclient.entities.User;
import com.example.advancedprojectandroidclient.services.FirebaseService;
import com.example.advancedprojectandroidclient.utils.LoginScreenTextWatcher;
import com.example.advancedprojectandroidclient.view_models.RefreshTokenViewModel;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.Date;

/**
 * The log in activity.
 * Named MainActivity because that was the default name and I did not care enough to both change it
 * and figure out how to keep it as the start up activity.
 */
public class MainActivity extends AppCompatActivity {

    private RegisteredUserApi registeredUserApi;
    private RefreshTokenViewModel refreshTokenViewModel;
    public static Activity fa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Keeping the instance of this public to allow other activities to finish it.
        fa = this;
        // If getting here from reset password, display little green text on top (normally hidden).
        boolean passwordReset = getIntent().getBooleanExtra("resetPassword", false);
        if (passwordReset) {
            TextView textView = findViewById(R.id.password_reset_success_tv);
            textView.setVisibility(TextView.VISIBLE);
        }
        this.registeredUserApi = new RegisteredUserApi();
        refreshTokenViewModel = new ViewModelProvider(this).get(RefreshTokenViewModel.class);


        EditText usernameEt = findViewById(R.id.login_et_username);
        EditText passwordEt = findViewById(R.id.login_et_pwd);
        TextView usernameErrorTv = findViewById(R.id.login_tv_username_empty);
        String errorText = "username " + getResources().getString(R.string.empty);
        usernameErrorTv.setText(errorText);

        // MutableLiveData abuse
        MutableLiveData<Boolean> loggedInRefreshToken = new MutableLiveData<>();
        // If successfully logged in with the refresh token, go to contacts activity.
        loggedInRefreshToken.observe(this, aBoolean -> {
            if (aBoolean) {
                Intent intent = new Intent(this, ContactsActivity.class);
                intent.putExtra("username", MyApplication.username);
                startActivity(intent);
                finish();
            }
        });

        Date now = new Date();

        MutableLiveData<Boolean> canProceed = new MutableLiveData<>();
        // Try logging in via refresh token for 200 miliseconds at most.
        // If the database did not retrieve the refresh token within this amount of time, assume there is none.
        while (new Date().getTime() - now.getTime() < 200 && (canProceed.getValue() == null || !canProceed.getValue())) {
            refreshTokenViewModel.logInWithAccessToken(canProceed, loggedInRefreshToken);
        }

        usernameEt.addTextChangedListener(new LoginScreenTextWatcher(findViewById(R.id.login_tv_username_empty)));
        passwordEt.addTextChangedListener(new LoginScreenTextWatcher(findViewById(R.id.login_tv_pwd_empty)));

        RadioGroup radioGroup = findViewById(R.id.login_rg_email_or_login);
        // Change the hint of the username field depending on the option chosen by the user.
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton rb = findViewById(checkedId);
            String text = rb.getText().toString();
            usernameEt.setHint(text);
            String errText = text + " " + getResources().getString(R.string.empty);
            usernameErrorTv.setText(errText);
        });

        // Abuse of live data to determine log in success.
        MutableLiveData<Boolean> loggedIn = new MutableLiveData<>();
        loggedIn.observe(this, aBoolean -> {
            // If login successful
            if (aBoolean) {
                // Get the token from Firebase and send it to the server.
                MutableLiveData<Boolean> cleaningFinished = new MutableLiveData<>();
                FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(this, instanceIdResult -> {
                    String token = instanceIdResult.getToken();
                    FirebaseService.sendRegistrationToServer(token);
                });
                new Thread(() -> {
                    // Cleans up the database when a new user logs in, so as not to expose the
                    // previous user's data.
                    MyApplication.appDB.contactDao().deleteAll();
                    MyApplication.appDB.messageDao().deleteTable();
                    cleaningFinished.postValue(true);
                }).start();
                // Once cleaning is finished, log in the user.
                cleaningFinished.observe(this, aBoolean1 -> {
                    RadioButton checked = findViewById(radioGroup.getCheckedRadioButtonId());
                    // Go to contacts activity with the username as an extra.
                    if (checked.getText().toString().equals("username")) {
                        Intent intent = new Intent(this, ContactsActivity.class);
                        intent.putExtra("username", usernameEt.getText().toString());
                        startActivity(intent);
                        finish();
                    } else {
                        Intent intent = new Intent(this, ContactsActivity.class);
                        intent.putExtra("username", MyApplication.username);
                        startActivity(intent);
                        finish();
                    }
                });
            } else {
                TextView errorTv = findViewById(R.id.login_tv_error);
                errorTv.setVisibility(TextView.VISIBLE);
            }
        });


        Button loginBtn = findViewById(R.id.login_btn_login);
        // Tries to log the user in on click.
        loginBtn.setOnClickListener(v -> {

            String username = usernameEt.getText().toString();
            String password = passwordEt.getText().toString();

            boolean error = false;
            // Checks that the fields are not empty.
            if (username.isEmpty()) {
                usernameErrorTv.setVisibility(TextView.VISIBLE);
                error = true;
            }
            if (password.isEmpty()) {
                TextView errorTv = findViewById(R.id.login_tv_pwd_empty);
                errorTv.setVisibility(TextView.VISIBLE);
                error = true;
            }
            if (error) {
                return;
            }

            // Tries to log in the user via their chosen method.
            RadioButton checked = findViewById(radioGroup.getCheckedRadioButtonId());
            if (checked.getText().toString().equals("username")) {
                User user = new User(username, password);
                registeredUserApi.loginUser(user, loggedIn);
            } else {
                User user = new User(username, password);
                user.setEmail(username);
                registeredUserApi.logInEmail(user, loggedIn);
            }
        });

        TextView signupTv = findViewById(R.id.login_tv_sign_up_link);
        // Go to sign up on click.
        signupTv.setOnClickListener(v -> {
            Intent intent = new Intent(this, SignUpActivity.class);
            startActivity(intent);
        });

        TextView forgotPassTv = findViewById(R.id.login_forgot_pass_tv);
        // Go to forgot password on click.
        forgotPassTv.setOnClickListener(v -> {
            Intent i = new Intent(this, ForgotPasswordActivity.class);
            startActivity(i);
        });
    }
}