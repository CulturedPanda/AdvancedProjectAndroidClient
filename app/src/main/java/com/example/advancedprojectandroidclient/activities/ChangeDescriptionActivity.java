package com.example.advancedprojectandroidclient.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;

import com.example.advancedprojectandroidclient.R;
import com.example.advancedprojectandroidclient.api.RegisteredUserApi;

/***
 * The activity for changing one's description (description is only shown on web version).
 */
public class ChangeDescriptionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_text_data);
        RegisteredUserApi registeredUserApi = new RegisteredUserApi(null);
        String username = getIntent().getStringExtra("username");

        // Sets the text in all the text views and hints in edit texts

        TextView currentTypeTv = findViewById(R.id.change_text_data_current_info_type_tv);
        currentTypeTv.setText(R.string.current_description);

        TextView currentDescriptionTv = findViewById(R.id.change_text_data_current_tv);

        // Get the current description from the server and sets it (done terribly).
        MutableLiveData<String> currentDescription = new MutableLiveData<>();
        currentDescription.observe(this, s -> currentDescriptionTv.setText(s));
        new Thread(() -> {
            registeredUserApi.getDescription(currentDescription, username);
        }).start();

        TextView newTypeTv = findViewById(R.id.change_text_data_new_info_type_tv);
        newTypeTv.setText(R.string.new_description);

        EditText newDescriptionEt = findViewById(R.id.change_text_data_new_et);
        newDescriptionEt.setHint(R.string.new_description_hint);

        // Submit to the server on click
        Button changeBtn = findViewById(R.id.change_text_data_btn_save);
        changeBtn.setOnClickListener(v -> {
            String newDescription = newDescriptionEt.getText().toString();
            // Makes sure description is not empty. If not, sends it to the server and finishes.
            if (newDescription.isEmpty()) {
                TextView newDescriptionErrorTv = findViewById(R.id.change_text_data_error_tv);
                String errorText = "description " + getResources().getString(R.string.empty);
                newDescriptionErrorTv.setText(errorText);
            } else {
                registeredUserApi.changeDescription(newDescription);
                finish();
            }
        });

        Button discardBtn = findViewById(R.id.change_text_data_btn_discard);
        discardBtn.setOnClickListener(v -> {
            finish();
        });
    }
}