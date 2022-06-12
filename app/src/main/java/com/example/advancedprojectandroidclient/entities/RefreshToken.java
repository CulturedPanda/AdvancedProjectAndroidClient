package com.example.advancedprojectandroidclient.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * The refresh token entity for the refresh token table.
 */
@Entity(tableName = "refresh_token")
public class RefreshToken {

    @PrimaryKey
    @NonNull
    private String token;

    /**
     * Constructor
     *
     * @param token the refresh token's value
     */
    public RefreshToken(@NonNull String token) {
        this.token = token;
    }


    /**
     * Getter for the refresh token's value.
     *
     * @return the refresh token's value.
     */
    @NonNull
    public String getToken() {
        return token;
    }

    /**
     * Setter for the refresh token's value
     *
     * @param token the new refresh token's value
     */
    public void setToken(@NonNull String token) {
        this.token = token;
    }
}
