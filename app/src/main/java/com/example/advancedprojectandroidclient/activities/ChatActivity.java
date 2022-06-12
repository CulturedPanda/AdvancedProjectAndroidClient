package com.example.advancedprojectandroidclient.activities;

import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
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
import com.example.advancedprojectandroidclient.view_models.RefreshTokenViewModel;

import java.util.Date;

/**
 * The chat itself.
 */
public class ChatActivity extends AppCompatActivity {

    private MessagesViewModel messageViewModel;
    private RefreshTokenViewModel refreshTokenViewModel;
    private String contactId;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        // Refreshes the user's tokens and begins auto refreshing them so long as user is in this activity.
        refreshTokenViewModel = new ViewModelProvider(this).get(RefreshTokenViewModel.class);
        refreshTokenViewModel.refreshTokens();
        refreshTokenViewModel.beginAutoRefresh();

        // Hides the action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // Sets basic text info for the chat
        String contactName = getIntent().getStringExtra("contactName");
        TextView headerUsernameTv = findViewById(R.id.chat_header_username_tv);
        headerUsernameTv.setText(contactName);
        contactId = getIntent().getStringExtra("contactId");

        // Gets the messages and lets the adapter use them to populate the recycler view
        messageViewModel = new ViewModelProvider(this).get(MessagesViewModel.class);
        messageViewModel.setWith(contactId);
        RecyclerView lstMessages = findViewById(R.id.chat_recycle_view);
        final MessageListAdapter adapter = new MessageListAdapter(this);
        lstMessages.setLayoutManager(new LinearLayoutManager(this));
        lstMessages.setAdapter(adapter);

        // Limit the height of the recycler view to 75% of the screen, so it doesn't go too high up
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = (displayMetrics.heightPixels * 75) / 100;
        ConstraintLayout constraintLayout = findViewById(R.id.chat_constraint_layout);
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(constraintLayout);
        constraintSet.constrainMaxHeight(R.id.chat_recycle_view, height);
        constraintSet.applyTo(constraintLayout);

        // Observe for changes in the message list and update to adapter when there are messages
        messageViewModel.getMessages(contactId).observe(this, messages -> {
            adapter.setMessages(messages);
            // Scrolls to the bottom of the recycler view
            lstMessages.scrollToPosition(adapter.getItemCount() - 1);
        });
        lstMessages.scrollToPosition(adapter.getItemCount() - 1);

        Button sendBtn = findViewById(R.id.chat_btn_send);
        // Sends a new message when the send button is clicked
        sendBtn.setOnClickListener(v -> {
            String message = ((TextView) findViewById(R.id.chat_et_message)).getText().toString();
            Message msg = new Message(contactId, 0, new Date().toString(), message, true, "text");
            messageViewModel.insert(msg);
            ((TextView) findViewById(R.id.chat_et_message)).setText("");
        });
    }

    @Override
    protected void onResume() {
        // Update the chat on resume. Made it here because notifications can open more chat activities when clicked.
        super.onResume();
        messageViewModel.setWith(contactId);
        messageViewModel.getLiveAll();
    }
}