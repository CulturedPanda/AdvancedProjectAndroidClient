package com.example.advancedprojectandroidclient.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.advancedprojectandroidclient.R;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        ListView lstSettings = findViewById(R.id.settings_list_view);
        lstSettings.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                new String[]{"Change server", "Change description", "Change nickname"}));

        lstSettings.setOnItemClickListener((parent, view, position, id) -> {
            switch (position) {
                case 0:
                    startActivity(new Intent(this, ChangeServerActivity.class));
                    break;
                case 1:
                    startActivity(new Intent(this, ChangeDescriptionActivity.class));
                    break;
                case 2:
                    startActivity(new Intent(this, ChangeNicknameActivity.class));
                    break;
            }
        });
    }
}