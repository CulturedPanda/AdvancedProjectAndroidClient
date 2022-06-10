package com.example.advancedprojectandroidclient.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;

import com.example.advancedprojectandroidclient.R;
import com.example.advancedprojectandroidclient.api.RegisteredUserApi;

public class ChangeNicknameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_text_data);
        RegisteredUserApi registeredUserApi = new RegisteredUserApi(null);
        String username = getIntent().getStringExtra("username");

        TextView currentTypeTv = findViewById(R.id.change_text_data_current_info_type_tv);
        currentTypeTv.setText(R.string.current_nickname);

        TextView currentNicknameTv = findViewById(R.id.change_text_data_current_tv);

        MutableLiveData<String> currentNickname = new MutableLiveData<>();
        currentNickname.observe(this, s -> currentNicknameTv.setText(s));
        new Thread(()->{registeredUserApi.getNickname(currentNickname, username);}).start();

        TextView newTypeTv = findViewById(R.id.change_text_data_new_info_type_tv);
        newTypeTv.setText(R.string.new_nickname);

        EditText newNicknameEt = findViewById(R.id.change_text_data_new_et);
        newNicknameEt.setHint(R.string.new_nickname_hint);

        Button changeBtn = findViewById(R.id.change_text_data_btn_save);
        changeBtn.setOnClickListener(v -> {
            String newNickname = newNicknameEt.getText().toString();
            if (newNickname.isEmpty()) {
                TextView newDescriptionErrorTv = findViewById(R.id.change_text_data_error_tv);
                String errorText = "nickname " + getResources().getString(R.string.empty);
                newDescriptionErrorTv.setText(errorText);
            } else {
                registeredUserApi.changeNickname(newNickname);
                finish();
            }
        });

        Button discardBtn = findViewById(R.id.change_text_data_btn_discard);
        discardBtn.setOnClickListener(v -> {
            finish();
        });
    }
}