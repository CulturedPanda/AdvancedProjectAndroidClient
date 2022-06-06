package com.example.advancedprojectandroidclient.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "refresh_token")
public class RefreshToken {

    @PrimaryKey
    @NonNull
    private String token;
    public static String accessToken;

    public RefreshToken(@NonNull String token) {
        this.token = token;
    }

    @NonNull
    public String getToken() {
        return token;
    }

    public void setToken(@NonNull String token) {
        this.token = token;
    }
}
