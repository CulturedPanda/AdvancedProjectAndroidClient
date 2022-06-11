package com.example.advancedprojectandroidclient.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Image of contact class.
 */
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

    /**
     * get image function
     * @return image
     */
    public String getImage() {
        return image;
    }

    /**
     * set image function
     * @param image
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * Constructor of Image
     * @param id id of image
     * @param image string image
     */
    public Image(String id, String image) {
        this.id = id;
        this.image = image;
    }
}
