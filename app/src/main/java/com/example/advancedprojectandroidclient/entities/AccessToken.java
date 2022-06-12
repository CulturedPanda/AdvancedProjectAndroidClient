package com.example.advancedprojectandroidclient.entities;

/**
 * Access token for the user
 * Used solely as a data class when getting responses via retrofit.
 */
public class AccessToken {

    /**
     * Constructor
     *
     * @param accessToken  access token
     * @param refreshToken refresh token
     */
    public AccessToken(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    /**
     * getter
     *
     * @return the access token's value.
     */
    public String getAccessToken() {
        return accessToken;
    }

    /**
     * setter
     *
     * @param accessToken sets new access token
     */
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    /**
     * getter for refresh token
     *
     * @return the refresh token's value.
     */
    public String getRefreshToken() {
        return refreshToken;
    }

    /**
     * setter refresh token
     *
     * @param refreshToken the new refresh token
     */
    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    private String accessToken;
    private String refreshToken;

    /**
     * get username
     *
     * @return string the username of the user
     */
    public String getUsername() {
        return username;
    }

    /**
     * set username
     *
     * @param username the new username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    private String username;

    /**
     * Cosntructor of access token
     *
     * @param accessToken  the access token value.
     * @param refreshToken the refresh token value.
     * @param username     the username of the user.
     */
    public AccessToken(String accessToken, String refreshToken, String username) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.username = username;
    }
}
