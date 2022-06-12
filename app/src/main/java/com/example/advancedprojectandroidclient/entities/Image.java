package com.example.advancedprojectandroidclient.entities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Image of contact class.
 */
@Entity
public class Image {

    @NonNull
    @PrimaryKey
    String id;

    String image;

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
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
    public Image(@NonNull String id, String image) {
        this.id = id;
        this.image = image;
    }

    public Bitmap decode() {
        byte[] decodedString = Base64.decode(image, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }
}
