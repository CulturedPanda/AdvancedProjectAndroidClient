package com.example.advancedprojectandroidclient.entities;

/**
 * A pending user entity for sending to the server when signing up.
 */
public class PendingUser {

    /**
     * Constructor
     *
     * @param username       the user's username
     * @param password       the user's password
     * @param phone          the user's phone number
     * @param email          the user's email
     * @param nickname       the user's nickname
     * @param secretQuestion the user's secret question
     */
    public PendingUser(String username, String password, String phone, String email, String nickname, SecretQuestion secretQuestion) {
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.email = email;
        this.nickname = nickname;
        this.secretQuestion = secretQuestion;
    }

    /**
     * Getter for the user's username
     *
     * @return the user's username
     */
    public String getUsername() {
        return username;
    }


    /**
     * Setter for the user's username
     *
     * @param username the user's username
     */
    public void setUsername(String username) {
        this.username = username;
    }


    /**
     * Getter for the user's password
     *
     * @return the user's password
     */
    public String getPassword() {
        return password;
    }


    /**
     * Setter for the user's password
     *
     * @param password the user's password
     */
    public void setPassword(String password) {
        this.password = password;
    }


    /**
     * Getter for the user's phone number
     *
     * @return the user's phone number
     */
    public String getPhone() {
        return phone;
    }


    /**
     * Setter for the user's phone number
     *
     * @param phone the user's phone number
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Getter for the user's email
     *
     * @return the user's email
     */
    public String getEmail() {
        return email;
    }


    /**
     * Setter for the user's email
     *
     * @param email the user's email
     */
    public void setEmail(String email) {
        this.email = email;
    }


    /**
     * Getter for the user's nickname
     *
     * @return the user's nickname
     */
    public String getNickname() {
        return nickname;
    }


    /**
     * Setter for the user's nickname
     *
     * @param nickname the user's nickname
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }


    /**
     * Getter for the user's secret question
     *
     * @return the user's secret question
     */
    public SecretQuestion getSecretQuestion() {
        return secretQuestion;
    }


    /**
     * Setter for the user's secret question
     *
     * @param secretQuestion the user's secret question
     */
    public void setSecretQuestion(SecretQuestion secretQuestion) {
        this.secretQuestion = secretQuestion;
    }

    private String username;
    private String password;
    private String phone;
    private String email;
    private String nickname;
    private SecretQuestion secretQuestion;
}
