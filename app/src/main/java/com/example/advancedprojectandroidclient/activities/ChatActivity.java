package com.example.advancedprojectandroidclient.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.advancedprojectandroidclient.R;
import com.example.advancedprojectandroidclient.adapters.MessageListAdapter;
import com.example.advancedprojectandroidclient.entities.Message;
import com.example.advancedprojectandroidclient.view_models.MessagesViewModel;

import java.util.Date;

public class ChatActivity extends AppCompatActivity {

    private MessagesViewModel messageViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        String contactName = getIntent().getStringExtra("contactName");
        TextView headerUsernameTv = findViewById(R.id.chat_header_username_tv);
        headerUsernameTv.setText(contactName);
        String contactId = getIntent().getStringExtra("contactId");

        messageViewModel = new ViewModelProvider(this).get(MessagesViewModel.class);
        messageViewModel.setWith(contactId);
        RecyclerView lstMessages = findViewById(R.id.chat_recycle_view);
        final MessageListAdapter adapter = new MessageListAdapter(this);
        lstMessages.setLayoutManager(new LinearLayoutManager(this));
        lstMessages.setAdapter(adapter);

        messageViewModel.getMessages(contactId).observe(this, adapter::setMessages);

        Button sendBtn = findViewById(R.id.chat_btn_send);
        sendBtn.setOnClickListener(v -> {
            String message = ((TextView) findViewById(R.id.chat_et_message)).getText().toString();
            Message msg = new Message(contactId, 0, new Date().toString(), message, true, "text");
            messageViewModel.insert(msg);
            ((TextView) findViewById(R.id.chat_et_message)).setText("");
        });
    }
}