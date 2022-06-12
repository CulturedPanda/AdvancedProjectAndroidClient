package com.example.advancedprojectandroidclient.entities;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * Database entity for the contact table.
 */
@Entity(primaryKeys = {"id", "contactOf"})
public class Contact {

    @NonNull
    private String id;
    @NonNull
    private String contactOf;

    /**
     * Constructor
     *
     * @param id        id the user's id
     * @param contactOf who the contact is a contact of.
     * @param last      the last message sent by the contact.
     * @param server    server the contact's server.
     * @param name      name the contact's display name.
     * @param lastdate  the contact's last seen date.
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public Contact(@NonNull String id, @NonNull String contactOf, String last, String server, String name, String lastdate) {
        this.id = id;
        this.contactOf = contactOf;
        this.last = last;
        this.server = server;
        this.name = name;
        this.lastdate = Contact.parseDate(lastdate);
    }

    /**
     * Parses a date into a human readable format.
     *
     * @param date the date to parse.
     * @return human readable format as "seen x time ago".
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String parseDate(String date) {
        try {
            LocalDateTime localDateTime = LocalDateTime.parse(date);
            long timeDelta = new Date().getTime() - localDateTime.toInstant(java.time.ZoneOffset.UTC).toEpochMilli();
            if (timeDelta < 1000 * 60) {
                return "Just now";
            } else if (timeDelta < 1000 * 60 * 60) {
                long minutes = timeDelta / (1000 * 60);
                return minutes + " minutes ago";
            } else if (timeDelta < 1000 * 60 * 60 * 24) {
                long hours = timeDelta / (1000 * 60 * 60);
                return hours + " hours ago";
            } else {
                long days = timeDelta / (1000 * 60 * 60 * 24);
                return days + " days ago";
            }
        } catch (Exception e) {
            return date;
        }
    }

    private String last;
    private String server;
    private String name;

    @ColumnInfo(name = "lastdate")
    private String lastdate;

    /**
     * Getter for the last message sent by the contact.
     *
     * @return the contact's id.
     */
    public String getId() {
        return id;
    }

    /**
     * Setter for the contact's id.
     *
     * @param id the contact's new id.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Getter for the contact's contactOf.
     *
     * @return who the contact is a contact of.
     */
    public String getContactOf() {
        return contactOf;
    }

    /**
     * Setter for who the contact is a contact of.
     *
     * @param contactOf sets who the contact is a contact of.
     */
    public void setContactOf(String contactOf) {
        this.contactOf = contactOf;
    }

    /**
     * Getter for the last message sent by the contact.
     *
     * @return returns the user's last message's content.
     */
    public String getLast() {
        return last;
    }

    /**
     * Setter for the contact's last message
     *
     * @param last sets the user's last message content.
     */
    public void setLast(String last) {
        this.last = last;
    }

    /**
     * Getter for the contact's server.
     *
     * @return the user's server.
     */
    public String getServer() {
        return server;
    }

    /**
     * Setter for the contact's server.
     *
     * @param server the server to set to.
     */
    public void setServer(String server) {
        this.server = server;
    }

    /**
     * Getter for the contact's display name.
     *
     * @return the contact's display name.
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for the contact's display name.
     *
     * @param name the contact's display name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for the contact's last seen date.
     *
     * @return lastdate
     */
    public String getLastdate() {
        return lastdate;
    }

    /**
     * Setter for the contact's last seen date.
     *
     * @param lastdate the new date the contact was last seen at.
     */
    public void setLastdate(String lastdate) {
        this.lastdate = lastdate;
    }

}
