package com.example.advancedprojectandroidclient.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.advancedprojectandroidclient.R;

public class ChangeServerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_text_data);

        TextView currentTypeTv = findViewById(R.id.change_text_data_current_info_type_tv);
        currentTypeTv.setText(R.string.current_server);

        TextView currentDescriptionTv = findViewById(R.id.change_text_data_current_tv);
        currentDescriptionTv.setText("abvdehhagagahsdjf");

        TextView newTypeTv = findViewById(R.id.change_text_data_new_info_type_tv);
        newTypeTv.setText(R.string.new_server);

        EditText newDescriptionEt = findViewById(R.id.change_text_data_new_et);
        newDescriptionEt.setHint(R.string.new_server_hint);

        Button changeBtn = findViewById(R.id.change_text_data_btn_save);
        changeBtn.setOnClickListener(v -> {
            finish();
        });

        Button discardBtn = findViewById(R.id.change_text_data_btn_discard);
        discardBtn.setOnClickListener(v -> {
            finish();
        });
    }
}