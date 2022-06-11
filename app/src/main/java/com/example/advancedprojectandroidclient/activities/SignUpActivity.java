package com.example.advancedprojectandroidclient.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.example.advancedprojectandroidclient.R;
import com.example.advancedprojectandroidclient.api.PendingUserApi;
import com.example.advancedprojectandroidclient.daos.AppDB;
import com.example.advancedprojectandroidclient.daos.ImageDao;
import com.example.advancedprojectandroidclient.entities.PendingUser;
import com.example.advancedprojectandroidclient.entities.SecretQuestion;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {
    private AppDB db;
    private ImageDao imageDao;
    Bitmap bmpImage;
    ImageView userImgIv;
    PendingUserApi pendingUserApi;

    int SELECT_IMAGE_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        pendingUserApi = new PendingUserApi();

        EditText emailEt = findViewById(R.id.signup_email_field_et);
        EditText usernameEt = findViewById(R.id.signup_username_field_et);
        EditText passwordEt = findViewById(R.id.sign_up_pass_et);
        EditText passwordConfirmEt = findViewById(R.id.signup_confirm_pass_field_et);
        EditText nicknameEt = findViewById(R.id.signup_nickname_field_et);
        EditText phoneEt = findViewById(R.id.signup_phone_field_et);
        EditText answerEt = findViewById(R.id.signup_answer_field_et);
        TextView tosTv = findViewById(R.id.signup_tos_tv);
        TextView privacyPolicyTv = findViewById(R.id.signup_privacy_policy_tv);
        CheckBox tosCb = findViewById(R.id.signup_tos_cb);
        CheckBox privacyPolicyCb = findViewById(R.id.signup_privacy_policy_cb);

        //get the spinner of security questions from the xml.
        Spinner questionsList = findViewById(R.id.signup_security_question_spinner);
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

        tosTv.setOnClickListener(v -> {
            Intent intent = new Intent(SignUpActivity.this, TermsOfServiceActivity.class);
            startActivity(intent);
        });

        privacyPolicyTv.setOnClickListener(v -> {
            Intent intent = new Intent(SignUpActivity.this, PrivacyPolicyActivity.class);
            startActivity(intent);
        });

        MutableLiveData<Boolean> doesUserExistByUsername = new MutableLiveData<>();
        MutableLiveData<Boolean> doesUserExistByEmail = new MutableLiveData<>();
        MutableLiveData<Boolean> doesUserExistByPhone = new MutableLiveData<>();
        MutableLiveData<Boolean> signUpSuccess = new MutableLiveData<>();
        // Can confirm user does not exist by any of the above categories if all first 3 are set to true.
        // 4th is a control to make sure the requests are not sent out twice.
        boolean[] checks = {false, false, false, false};

        doesUserExistByEmail.observe(this, aBoolean -> {
            if (aBoolean) {
                checks[0] = false;
                emailEt.setError("Email already exists");
            }
            else{
                checks[0] = true;
                emailEt.setError(null);
                if (checks[1] && checks[2] && !checks[3]) {
                    PendingUser user = new PendingUser(usernameEt.getText().toString(), passwordEt.getText().toString(),
                            phoneEt.getText().toString(), emailEt.getText().toString(), nicknameEt.getText().toString(),
                            new SecretQuestion(Integer.toString(questionsList.getSelectedItemPosition() + 1), answerEt.getText().toString()));
                    pendingUserApi.signUpUser(user, signUpSuccess);
                    checks[3] = true;
                }
            }
        });

        doesUserExistByUsername.observe(this, aBoolean -> {
            if (aBoolean) {
                checks[1] = false;
                usernameEt.setError("Username already exists");
            }
            else {
                checks[1] = true;
                usernameEt.setError(null);
                if (checks[0] && checks[2] && !checks[3]) {
                    PendingUser user = new PendingUser(usernameEt.getText().toString(), passwordEt.getText().toString(),
                            phoneEt.getText().toString(), emailEt.getText().toString(), nicknameEt.getText().toString(),
                            new SecretQuestion(Integer.toString(questionsList.getSelectedItemPosition() + 1), answerEt.getText().toString()));
                    pendingUserApi.signUpUser(user, signUpSuccess);
                    checks[3] = true;
                }
            }
        });

        doesUserExistByPhone.observe(this, aBoolean -> {
            if (aBoolean) {
                checks[2] = false;
                phoneEt.setError("Phone number already exists");
            } else {
                checks[2] = true;
                phoneEt.setError(null);
                if (checks[0] && checks[1] && !checks[3]) {
                    PendingUser user = new PendingUser(usernameEt.getText().toString(), passwordEt.getText().toString(),
                            phoneEt.getText().toString(), emailEt.getText().toString(), nicknameEt.getText().toString(),
                            new SecretQuestion(Integer.toString(questionsList.getSelectedItemPosition() + 1), answerEt.getText().toString()));
                    pendingUserApi.signUpUser(user, signUpSuccess);
                    checks[3] = true;
                }
            }
        });

        signUpSuccess.observe(this, aBoolean -> {
            if (aBoolean) {
                Intent intent = new Intent(this, EmailVerificationActivity.class);
                intent.putExtra("username", usernameEt.getText().toString());
                intent.putExtra("from", "signup");
                startActivity(intent);
                finish();
            }
            else{
                checks[3] = false;
            }
        });


        bmpImage = null;
        userImgIv = findViewById(R.id.sign_up_profile_pic_iv);
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
            userImgIv = findViewById(R.id.sign_up_profile_pic_iv);
        });

        //sign up button
        Button btnSignUp = findViewById(R.id.sign_up_btn);
        btnSignUp.setOnClickListener(v -> {
            boolean error = false;
            if (emailEt.getText().toString().isEmpty()) {
                emailEt.setError("Email is required");
                error = true;
            }
            if (usernameEt.getText().toString().isEmpty()) {
                usernameEt.setError("Username is required");
                error = true;
            }
            String password = passwordEt.getText().toString();
            String passwordConfirm = passwordConfirmEt.getText().toString();
            if (password.isEmpty()) {
                passwordEt.setError("Password is required");
                error = true;
            }
            if (passwordConfirm.isEmpty()) {
                passwordConfirmEt.setError("You must confirm your password to sign up");
                error = true;
            }
            if (nicknameEt.getText().toString().isEmpty()) {
                nicknameEt.setError("Nickname is required");
                error = true;
            }
            if (answerEt.getText().toString().isEmpty()) {
                answerEt.setError("Answer is required");
                error = true;
            }
            if (!tosCb.isChecked()){
                tosCb.setError(getString(R.string.must_agreen_tos));
                error = true;
            }
            if (!privacyPolicyCb.isChecked()){
                privacyPolicyCb.setError(getString(R.string.must_agreen_privacy_policy));
                error = true;
            }
            if (!error){
                if (password.length() < 8){
                    passwordEt.setError(getString(R.string.password_len_error));
                }
                else {
                    Pattern p = Pattern.compile("(?=[A-Za-z0-9@#$%^&+!=]+$)^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[@#$%^&+!=])(?=.{8,}).*$");
                    Matcher m = p.matcher(password);
                    boolean b = m.find();
                    if (!b) {
                        passwordEt.setError(getString(R.string.password_format_error));
                    }
                    else if (!password.equals(passwordConfirm)) {
                        passwordEt.setError(getString(R.string.password_match_error));
                        passwordConfirmEt.setError(getString(R.string.password_match_error));
                    }
                    else{
                        //check if user already exists
                        pendingUserApi.doesPendingUserExistByX("Username", usernameEt.getText().toString(), doesUserExistByUsername);
                        pendingUserApi.doesPendingUserExistByX("Email", emailEt.getText().toString(), doesUserExistByEmail);
                        if (!phoneEt.getText().toString().isEmpty()) {
                            pendingUserApi.doesPendingUserExistByX("Phone", phoneEt.getText().toString(), doesUserExistByPhone);
                        }
                        else{
                            // Skip the check for the phone number
                            checks[2] = true;
                        }
                    }
                }
            }
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
                userImgIv.setImageBitmap(bmpImage);
            }

        }
    }
}