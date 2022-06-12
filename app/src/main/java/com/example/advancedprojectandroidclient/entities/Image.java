package com.example.advancedprojectandroidclient.entities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * A database entity for the user's profile picture.
 */
@Entity
public class Image {

    @NonNull
    @PrimaryKey
    String id;

    String image;

    /**
     * Getter for the image's id.
     *
     * @return the image's id.
     */
    @NonNull
    public String getId() {
        return id;
    }

    /**
     * Setter for the image's id.
     *
     * @param id the image's id.
     */
    public void setId(@NonNull String id) {
        this.id = id;
    }

    /**
     * Getter for the image's data.
     *
     * @return the image's data, should be as a base64 string.
     */
    public String getImage() {
        return image;
    }


    /**
     * Setter for the image's data
     *
     * @param image A base 64 string.
     */
    public void setImage(String image) {
        this.image = image;
    }


    /**
     * Constructor.
     *
     * @param id    the image's id.
     * @param image the image's data as a base64 string.
     */
    public Image(@NonNull String id, String image) {
        this.id = id;
        this.image = image;
    }

    /**
     * Decodes the image.
     *
     * @return the image as a bitmap.
     */
    public Bitmap decode() {
        if (this.image != null) {
            byte[] decodedString = Base64.decode(image, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        }
        return null;
    }
}
