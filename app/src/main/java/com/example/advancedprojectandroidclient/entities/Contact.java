package com.example.advancedprojectandroidclient.entities;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * Contant class
 */
@Entity(primaryKeys = {"id", "contactOf"})
public class Contact {

    @NonNull
    private String id;
    @NonNull
    private String contactOf;

    /**
     * Constructor
     * @param id id
     * @param contactOf contactOf
     * @param last last
     * @param server server
     * @param name name
     * @param lastdate lastdate
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
     * parse date function
     * @param date date
     * @return string
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String parseDate(String date){
        try {
            LocalDateTime localDateTime = LocalDateTime.parse(date);
            long timeDelta = new Date().getTime() - localDateTime.toInstant(java.time.ZoneOffset.UTC).toEpochMilli();
            if (timeDelta < 1000 * 60){
                return "Just now";
            }
            else if (timeDelta < 1000 * 60 * 60) {
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
     * id getter
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * id setter
     * @param id id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * contact of getter
     * @return string
     */
    public String getContactOf() {
        return contactOf;
    }

    /**
     *  contactof setter
     * @param contactOf contactof
     */
    public void setContactOf(String contactOf) {
        this.contactOf = contactOf;
    }

    /**
     * last getter
     * @return string last
     */
    public String getLast() {
        return last;
    }

    /**
     * last setter
     * @param last last
     */
    public void setLast(String last) {
        this.last = last;
    }

    /**
     * server getter
     * @return string server
     */
    public String getServer() {
        return server;
    }

    /**
     * server setter
     * @param server server
     */
    public void setServer(String server) {
        this.server = server;
    }

    /**
     * name getter of contact
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * name setter of contact
     * @param name name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * last date getter of contact
     * @return lastdate
     */
    public String getLastdate() {
        return lastdate;
    }

    /**
     * last date setter of contact
     * @param lastdate lastdate
     */
    public void setLastdate(String lastdate) {
        this.lastdate = lastdate;
    }

}
