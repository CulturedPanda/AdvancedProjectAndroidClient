package com.example.advancedprojectandroidclient.activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.advancedprojectandroidclient.R;

public class ChangeDescriptionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_text_data);

        TextView currentTypeTv = findViewById(R.id.change_text_data_current_info_type_tv);
        currentTypeTv.setText(R.string.current_description);

        TextView currentDescriptionTv = findViewById(R.id.change_text_data_current_info_tv);
    }
}