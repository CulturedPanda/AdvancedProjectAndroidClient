package com.example.advancedprojectandroidclient.entities;

/**
 * User class.
 */
public class User {

    /**
     * username getter
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * username setter
     * @param username username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * password getter
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * password setter
     * @param password password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    private String username;
    private String password;

    /**
     * email getter
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * email setter
     * @param email email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    private String email;

    /**
     * Constructor
     * @param username username
     * @param password password
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
