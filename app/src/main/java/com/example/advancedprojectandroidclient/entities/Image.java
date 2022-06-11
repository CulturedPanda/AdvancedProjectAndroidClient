package com.example.advancedprojectandroidclient.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(primaryKeys = {"id", "image"})
public class Image {
    @NonNull
    String id;
    @NonNull
    String image;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Image(String id, String image) {
        this.id = id;
        this.image = image;
    }
}
