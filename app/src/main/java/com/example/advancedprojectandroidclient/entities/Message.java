package com.example.advancedprojectandroidclient.entities;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * Message class
 */
@Entity
public class Message {

    /**
     * get with user function
     * @return string of conversation with someone
     */
    public String getWith() {
        return with;
    }

    /**
     * set with user function
     * @param with string
     */
    public void setWith(String with) {
        this.with = with;
    }

    /**
     * getter id
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * setter id
     * @param id id
     */
    public void setId(int id) {
        this.id = id;
    }

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "with")
    private String with;

    /**
     * time function
     * @return time
     */
    public long getActualTime() {
        return actualTime;
    }

    /**
     * sets time
     * @param actualTime set the time
     */
    public void setActualTime(long actualTime) {
        this.actualTime = actualTime;
    }

    long actualTime;

    /**
     * Message constructor
     * @param with with
     * @param conversationId id convo
     * @param created string
     * @param content string
     * @param sent bool if sent or not
     * @param type type of message
     */
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

    /**
     * parse time
     * @param created string
     * @return long
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static long parseActualTime(String created) {
        LocalDateTime date = LocalDateTime.parse(created);
        return date.toEpochSecond(ZoneOffset.UTC);
    }

    /**
     * parse date
     * @param date string
     * @return string
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String parseDate(String date) {
        try {
            LocalDateTime localDateTime = LocalDateTime.parse(date);
            return localDateTime.getHour() + ":" + localDateTime.getMinute();
        } catch (Exception e) {
            return date;
        }
    }

    /**
     * conversation id getter
     * @return int conversation id
     */
    public int getConversationId() {
        return conversationId;
    }

    /**
     * conversation id setter
     * @param conversationId conversation id
     */
    public void setConversationId(int conversationId) {
        this.conversationId = conversationId;
    }

    /**
     * created getter
     * @return string
     */
    public String getCreated() {
        return created;
    }

    /**
     * setter created
     * @param created created
     */
    public void setCreated(String created) {
        this.created = created;
    }

    /**
     * content getter
     * @return content
     */
    public String getContent() {
        return content;
    }

    /**
     * content setter
     * @param content content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * function checks if message sent
     * @return bool
     */
    public boolean isSent() {
        return sent;
    }

    /**
     * message sent setter
     * @param sent sent
     */
    public void setSent(boolean sent) {
        this.sent = sent;
    }

    /**
     * function gets type of message
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     * set type
     * @param type type
     */
    public void setType(String type) {
        this.type = type;
    }

    private int conversationId;
    private String created;
    private String content;
    private boolean sent;
    private String type;
}
