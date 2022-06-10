package com.example.advancedprojectandroidclient.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.advancedprojectandroidclient.MyApplication;
import com.example.advancedprojectandroidclient.R;
import com.example.advancedprojectandroidclient.adapters.SettingsListAdapter;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        String username = getIntent().getStringExtra("username");

        ListView lstSettings = findViewById(R.id.settings_list_view);
        lstSettings.setAdapter(new SettingsListAdapter(MyApplication.context,
                new String[]{"Change server", "Change description", "Change nickname"}));

        lstSettings.setOnItemClickListener((parent, view, position, id) -> {
            switch (position) {
                case 0:
                    Intent iServ = new Intent(SettingsActivity.this, ChangeServerActivity.class);
                    iServ.putExtra("username", username);
                    startActivity(iServ);
                    break;
                case 1:
                    Intent iDesc = new Intent(SettingsActivity.this, ChangeDescriptionActivity.class);
                    iDesc.putExtra("username", username);
                    startActivity(iDesc);
                    break;
                case 2:
                    Intent iNick = new Intent(SettingsActivity.this, ChangeNicknameActivity.class);
                    iNick.putExtra("username", username);
                    startActivity(iNick);
                    break;
            }
        });
    }
}