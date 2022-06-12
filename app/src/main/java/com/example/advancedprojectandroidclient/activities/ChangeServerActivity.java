package com.example.advancedprojectandroidclient.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;

import com.example.advancedprojectandroidclient.R;
import com.example.advancedprojectandroidclient.api.RegisteredUserApi;

/**
 * The activity for changing your server.
 * Identical to ChangeDescriptionActivity, short of names, hence short on documentation.
 */
public class ChangeServerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_text_data);
        RegisteredUserApi registeredUserApi = new RegisteredUserApi(null);
        String username = getIntent().getStringExtra("username");

        TextView currentTypeTv = findViewById(R.id.change_text_data_current_info_type_tv);
        currentTypeTv.setText(R.string.current_server);

        TextView currentServerTv = findViewById(R.id.change_text_data_current_tv);

        MutableLiveData<String> currentNickname = new MutableLiveData<>();
        currentNickname.observe(this, s -> currentServerTv.setText(s));
        new Thread(() -> {
            registeredUserApi.getServer(currentNickname, username);
        }).start();

        TextView newTypeTv = findViewById(R.id.change_text_data_new_info_type_tv);
        newTypeTv.setText(R.string.new_server);

        EditText newServerEt = findViewById(R.id.change_text_data_new_et);
        newServerEt.setHint(R.string.new_server_hint);

        Button changeBtn = findViewById(R.id.change_text_data_btn_save);
        changeBtn.setOnClickListener(v -> {
            String newNickname = newServerEt.getText().toString();
            if (newNickname.isEmpty()) {
                TextView newDescriptionErrorTv = findViewById(R.id.change_text_data_error_tv);
                String errorText = "server " + getResources().getString(R.string.empty);
                newDescriptionErrorTv.setText(errorText);
            } else {
                registeredUserApi.changeServer(newNickname);
                finish();
            }
        });

        Button discardBtn = findViewById(R.id.change_text_data_btn_discard);
        discardBtn.setOnClickListener(v -> {
            finish();
        });
    }
}