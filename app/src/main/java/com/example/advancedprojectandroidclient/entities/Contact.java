package com.example.advancedprojectandroidclient.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class Contact {

    @PrimaryKey
    private String id;
    private String contactOf;

    public Contact(String id, String contactOf, String last, String server, String name, Date lastdate) {
        this.id = id;
        this.contactOf = contactOf;
        this.last = last;
        this.server = server;
        this.name = name;
        this.lastdate = lastdate;
    }

    private String last;
    private String server;
    private String name;

    public Contact() {
    }

    private Date lastdate;

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

    public Date getLastdate() {
        return lastdate;
    }

    public void setLastdate(Date lastdate) {
        this.lastdate = lastdate;
    }

}
