package com.example.advancedprojectandroidclient.entities;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Entity
public class Message {

    public String getWith() {
        return with;
    }

    public void setWith(String with) {
        this.with = with;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "with")
    private String with;

    public long getActualTime() {
        return actualTime;
    }

    public void setActualTime(long actualTime) {
        this.actualTime = actualTime;
    }

    long actualTime;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Message(String with, int conversationId, String created, String content, boolean sent, String type) {
        this.with = with;
        this.conversationId = conversationId;
        this.created = Message.parseDate(created);
        this.content = content;
        this.sent = sent;
        this.type = type;
        try {
            actualTime = LocalDateTime.parse(created).toEpochSecond(ZoneOffset.UTC);
        } catch (Exception ignored) {
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static long parseActualTime(String created) {
        LocalDateTime date = LocalDateTime.parse(created);
        return date.toEpochSecond(ZoneOffset.UTC);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String parseDate(String date) {
        try {
            LocalDateTime localDateTime = LocalDateTime.parse(date);
            return localDateTime.getHour() + ":" + localDateTime.getMinute();
        } catch (Exception e) {
            return date;
        }
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
