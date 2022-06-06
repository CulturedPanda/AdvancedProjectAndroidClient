package com.example.advancedprojectandroidclient.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Message {

    public String getWith() {
        return with;
    }

    public void setWith(String with) {
        this.with = with;
    }

    @PrimaryKey
    @NonNull
    private String with;

    public Message(@NonNull String with, int conversationId, String created, String content, boolean sent, String type) {
        this.with = with;
        this.conversationId = conversationId;
        this.created = created;
        this.content = content;
        this.sent = sent;
        this.type = type;
    }

    public int getConversationId() {
        return conversationId;
    }

    public void setConversationId(int conversationId) {
        this.conversationId = conversationId;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isSent() {
        return sent;
    }

    public void setSent(boolean sent) {
        this.sent = sent;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private int conversationId;
    private String created;
    private String content;
    private boolean sent;
    private String type;
}
