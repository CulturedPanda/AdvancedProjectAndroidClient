package com.example.advancedprojectandroidclient.entities;

/**
 * A user to be sent to the server when logging in.
 * Purely a data class, simply needed for retrofit.
 */
public class User {

    /**
     * Getter for the user's username.
     *
     * @return the user's username.
     */
    public String getUsername() {
        return username;
    }


    /**
     * Setter for the user's username.
     *
     * @param username the user's username.
     */
    public void setUsername(String username) {
        this.username = username;
    }


    /**
     * Getter for the user's password.
     *
     * @return the user's password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter for the user's password.
     *
     * @param password the user's password.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    private String username;
    private String password;


    /**
     * Getter for the user's email.
     *
     * @return the user's email.
     */
    public String getEmail() {
        return email;
    }


    /**
     * Setter for the user's email.
     *
     * @param email the user's email.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    private String email;


    /**
     * Constructor
     *
     * @param username the user's username
     * @param password the user's password
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
