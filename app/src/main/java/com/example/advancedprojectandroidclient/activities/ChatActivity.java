package com.example.advancedprojectandroidclient.activities;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
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

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = (displayMetrics.heightPixels * 75) / 100;
        ConstraintLayout constraintLayout = findViewById(R.id.chat_constraint_layout);
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(constraintLayout);
        constraintSet.constrainMaxHeight(R.id.chat_recycle_view, height);
        constraintSet.applyTo(constraintLayout);

        messageViewModel.getMessages(contactId).observe(this, messages -> {
            adapter.setMessages(messages);
            lstMessages.scrollToPosition(adapter.getItemCount() - 1);
        });
        lstMessages.scrollToPosition(adapter.getItemCount() - 1);

        Button sendBtn = findViewById(R.id.chat_btn_send);
        sendBtn.setOnClickListener(v -> {
            String message = ((TextView) findViewById(R.id.chat_et_message)).getText().toString();
            Message msg = new Message(contactId, 0, new Date().toString(), message, true, "text");
            messageViewModel.insert(msg);
            ((TextView) findViewById(R.id.chat_et_message)).setText("");
        });
    }
}