package com.example.advancedprojectandroidclient.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
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

import com.example.advancedprojectandroidclient.MyApplication;
import com.example.advancedprojectandroidclient.R;
import com.example.advancedprojectandroidclient.api.PendingUserApi;
import com.example.advancedprojectandroidclient.entities.Image;
import com.example.advancedprojectandroidclient.entities.PendingUser;
import com.example.advancedprojectandroidclient.entities.SecretQuestion;
import com.example.advancedprojectandroidclient.services.FirebaseService;
import com.google.firebase.iid.FirebaseInstanceId;

import java.io.ByteArrayOutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The activity used for signing up.
 */
public class SignUpActivity extends AppCompatActivity {
    ImageView userImgIv;
    PendingUserApi pendingUserApi;
    boolean imgSet;

    int SELECT_IMAGE_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        pendingUserApi = new PendingUserApi();
        // Used to determine if the user set a profile picture or not.
        imgSet = false;

        // ALLLLLLLLLLLLLLLLLL the fields.
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
            // Goes to the wonderful TOS activity
            Intent intent = new Intent(SignUpActivity.this, TermsOfServiceActivity.class);
            startActivity(intent);
        });

        privacyPolicyTv.setOnClickListener(v -> {
            // Goes to the wonderful privacy policy activity.
            Intent intent = new Intent(SignUpActivity.this, PrivacyPolicyActivity.class);
            startActivity(intent);
        });

        // YET MORE ABUSE OF LIVE DATA
        MutableLiveData<Boolean> doesUserExistByUsername = new MutableLiveData<>();
        MutableLiveData<Boolean> doesUserExistByEmail = new MutableLiveData<>();
        MutableLiveData<Boolean> doesUserExistByPhone = new MutableLiveData<>();
        MutableLiveData<Boolean> signUpSuccess = new MutableLiveData<>();
        // Can confirm user does not exist by any of the above categories if all first 3 are set to true.
        // 4th is a control to make sure the requests are not sent out twice.
        boolean[] checks = {false, false, false, false};

        doesUserExistByEmail.observe(this, aBoolean -> {
            // If the user already exists by mail, set the check to false and display an error.
            if (aBoolean) {
                checks[0] = false;
                emailEt.setError("Email already exists");
            }
            // Otherwise, set the check to true.
            else {
                checks[0] = true;
                emailEt.setError(null);
                // If all checks are true and no one set the control to true, send the request.
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
            // Same as email, but for username.
            if (aBoolean) {
                checks[1] = false;
                usernameEt.setError("Username already exists");
            } else {
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
            // Same as email, but for phone.
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
            // Checks if adding the pending user was successful. If it was, go to the verify email activity.
            if (aBoolean) {
                // Checks if the user set a profile picture.
                if (imgSet) {
                    //convert image to base64 string.
                    userImgIv = findViewById(R.id.sign_up_profile_pic_iv);
                    userImgIv.buildDrawingCache();
                    BitmapDrawable drawable = (BitmapDrawable) userImgIv.getDrawable();
                    Bitmap bitmap = drawable.getBitmap();
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte[] image = stream.toByteArray();
                    String img_str = Base64.encodeToString(image, Base64.DEFAULT);

                    //save image in DB as string
                    Image imageFinal = new Image(usernameEt.getText().toString(), img_str);
                    new Thread(() -> {
                        MyApplication.appDB.imageDao().insert(imageFinal);
                    }).start();
                }
                // Send the firebase token to the server.
                FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(this, instanceIdResult -> {
                    String token = instanceIdResult.getToken();
                    FirebaseService.sendRegistrationToServerOnSignup(usernameEt.getText().toString(), token);
                });
                Intent intent = new Intent(this, EmailVerificationActivity.class);
                intent.putExtra("username", usernameEt.getText().toString());
                intent.putExtra("from", "signup");
                startActivity(intent);
                finish();
            }
            // On failure, make sure that trying again is possible.
            // This should only happeen if the server goes down or the like.
            else {
                checks[3] = false;
            }
        });

        //Choose profile img button and display in box
        Button btnImage = findViewById(R.id.sign_up_choose_img_btn);
        btnImage.setOnClickListener(v -> {
            Intent i = new Intent();
            i.setType("image/*");
            i.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(i, "Title"), SELECT_IMAGE_CODE);
        });

        //sign up button
        Button btnSignUp = findViewById(R.id.sign_up_btn);
        // On click, perform checks then sign up the user if they all pass.
        btnSignUp.setOnClickListener(v -> {
            boolean error = false;
            // Check that all required fields are filled in.
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
            // Check that both checkboxes are checked.
            if (!tosCb.isChecked()) {
                tosCb.setError(getString(R.string.must_agreen_tos));
                error = true;
            }
            if (!privacyPolicyCb.isChecked()) {
                privacyPolicyCb.setError(getString(R.string.must_agreen_privacy_policy));
                error = true;
            }
            if (!error) {
                // Check password rules
                if (password.length() < 8) {
                    passwordEt.setError(getString(R.string.password_len_error));
                } else {
                    Pattern p = Pattern.compile("(?=[A-Za-z0-9@#$%^&+!=]+$)^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[@#$%^&+!=])(?=.{8,}).*$");
                    Matcher m = p.matcher(password);
                    boolean b = m.find();
                    if (!b) {
                        passwordEt.setError(getString(R.string.password_format_error));
                    } else if (!password.equals(passwordConfirm)) {
                        passwordEt.setError(getString(R.string.password_match_error));
                        passwordConfirmEt.setError(getString(R.string.password_match_error));
                    }
                    // If all checks passed, ask the server for confirmation about the user's details.
                    else {
                        //check if user already exists
                        pendingUserApi.doesPendingUserExistByX("Username", usernameEt.getText().toString(), doesUserExistByUsername);
                        pendingUserApi.doesPendingUserExistByX("Email", emailEt.getText().toString(), doesUserExistByEmail);
                        if (!phoneEt.getText().toString().isEmpty()) {
                            pendingUserApi.doesPendingUserExistByX("Phone", phoneEt.getText().toString(), doesUserExistByPhone);
                        } else {
                            // Skip the check for the phone number if the user did not input any.
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

        if (requestCode == 1) {
            // Sets the image to the image view, and sets imgSet to true to indicate that the image has been set.
            if (data != null) {
                Uri uri = data.getData();
                if (uri != null) {
                    ImageView profileImg = findViewById(R.id.sign_up_profile_pic_iv);
                    profileImg.setImageURI(uri);
                    imgSet = true;
                }
            }
        }
    }
}