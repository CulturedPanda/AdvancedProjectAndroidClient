package com.example.advancedprojectandroidclient.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Image {

    @PrimaryKey(autoGenerate = true)
    int id;
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    Byte[] image;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Byte[] getImage() {
        return image;
    }

    public void setImage(Byte[] image) {
        this.image = image;
    }

    public Image(int id, Byte[] image) {
        this.id = id;
        this.image = image;
    }
}
