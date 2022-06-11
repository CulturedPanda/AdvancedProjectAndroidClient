package com.example.advancedprojectandroidclient.entities;

/**
 * Access token to user
 */
public class AccessToken {

    /**
     * Constructor
     * @param accessToken  access token
     * @param refreshToken refresh token
     */
    public AccessToken(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    /**
     * getter
     * @return string
     */
    public String getAccessToken() {
        return accessToken;
    }

    /**
     * setter
     * @param accessToken sets new access token
     */
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    /**
     * getter for refresh token
     * @return refresh token
     */
    public String getRefreshToken() {
        return refreshToken;
    }

    /**
     * setter refresh token
     * @param refreshToken
     */
    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    private String accessToken;
    private String refreshToken;

    /**
     * get username
     * @return string username
     */
    public String getUsername() {
        return username;
    }

    /**
     * set username
     * @param username sets username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    private String username;

    /**
     * Cosntructor of access token
     * @param accessToken
     * @param refreshToken
     * @param username
     */
    public AccessToken(String accessToken, String refreshToken, String username) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.username = username;
    }
}
