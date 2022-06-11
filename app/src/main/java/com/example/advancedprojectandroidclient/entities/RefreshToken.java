package com.example.advancedprojectandroidclient.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Refresh token class.
 */
@Entity(tableName = "refresh_token")
public class RefreshToken {

    @PrimaryKey
    @NonNull
    private String token;
    public static String accessToken;

    /**
     * Constructor
     * @param token token
     */
    public RefreshToken(@NonNull String token) {
        this.token = token;
    }

    /**
     * token getter
     * @return token
     */
    @NonNull
    public String getToken() {
        return token;
    }

    /**
     * token setter
     * @param token token
     */
    public void setToken(@NonNull String token) {
        this.token = token;
    }
}
