package com.example.advancedprojectandroidclient.entities;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * The database entity for the message table.
 */
@Entity
public class Message {

    /**
     * Getter for who the message is from.
     *
     * @return the message's sender.
     */
    public String getWith() {
        return with;
    }

    /**
     * Setter for who the message is from.
     *
     * @param with the message's sender.
     */
    public void setWith(String with) {
        this.with = with;
    }

    /**
     * Getter for the message's id.
     *
     * @return the message's id.
     */
    public int getId() {
        return id;
    }

    /**
     * Setter for the message's id.
     *
     * @param id the message's id.
     */
    public void setId(int id) {
        this.id = id;
    }

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "with")
    private String with;


    /**
     * Getter for the time from epoch of the message.
     *
     * @return the message's time from epoch.
     */
    public long getActualTime() {
        return actualTime;
    }

    /**
     * Setter for the time from epoch of the message.
     *
     * @param actualTime the message's time from epoch.
     */
    public void setActualTime(long actualTime) {
        this.actualTime = actualTime;
    }

    long actualTime;


    /**
     * Constructor.
     *
     * @param with           the id of the user the message is with.
     * @param conversationId the id of the conversation the message is in.
     * @param created        the time the message was created.
     * @param content        the content of the message.
     * @param sent           whether the message was sent or received by the user.
     * @param type           the type of the message.
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
     * Parses the actual time as seconds from epoch. Used for sorting messages when retrieving them.
     *
     * @param created the time the message was created.
     * @return the time the message was created in seconds from epoch.
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static long parseActualTime(String created) {
        LocalDateTime date = LocalDateTime.parse(created);
        return date.toEpochSecond(ZoneOffset.UTC);
    }


    /**
     * Parses the time the message was created.
     *
     * @param date the time the message was created.
     * @return the time the message was created in a human readable format, as "hh:mm".
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
     * Getter for the message's conversation id.
     *
     * @return the message's conversation id.
     */
    public int getConversationId() {
        return conversationId;
    }


    /**
     * Setter for the message's conversation id.
     *
     * @param conversationId the message's conversation id.
     */
    public void setConversationId(int conversationId) {
        this.conversationId = conversationId;
    }

    /**
     * Getter for when the message was created.
     *
     * @return the time the message was created.
     */
    public String getCreated() {
        return created;
    }


    /**
     * Setter for when the message was created.
     *
     * @param created the time the message was created.
     */
    public void setCreated(String created) {
        this.created = created;
    }

    /**
     * Getter for the message's content.
     *
     * @return the message's content.
     */
    public String getContent() {
        return content;
    }


    /**
     * Setter for the message's content.
     *
     * @param content the message's content.
     */
    public void setContent(String content) {
        this.content = content;
    }


    /**
     * Getter for whether the message was sent or received by the user.
     *
     * @return whether the message was sent or received by the user.
     */
    public boolean isSent() {
        return sent;
    }

    /**
     * Setter for whether the message was sent or received by the user.
     *
     * @param sent whether the message was sent or received by the user.
     */
    public void setSent(boolean sent) {
        this.sent = sent;
    }


    /**
     * Getter for the message's type.
     *
     * @return the message's type.
     */
    public String getType() {
        return type;
    }


    /**
     * Setter for the message's type.
     *
     * @param type the message's type.
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
