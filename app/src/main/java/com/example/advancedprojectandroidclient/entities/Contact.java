package com.example.advancedprojectandroidclient.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class Contact {

    @PrimaryKey
    @NonNull
    private String id;
    private String contactOf;

    public Contact(@NonNull String id, String contactOf, String last, String server, String name, String lastdate) {
        this.id = id;
        this.contactOf = contactOf;
        this.last = last;
        this.server = server;
        this.name = name;
        this.lastdate = this.parseDate(lastdate);
    }

    private String parseDate(String date){
        try {
            Date lastDate = new Date(date);
            long timeDelta = System.currentTimeMillis() - lastDate.getTime();
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContactOf() {
        return contactOf;
    }

    public void setContactOf(String contactOf) {
        this.contactOf = contactOf;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastdate() {
        return lastdate;
    }

    public void setLastdate(String lastdate) {
        this.lastdate = lastdate;
    }

}
