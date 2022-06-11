package com.example.advancedprojectandroidclient.entities;

/**
 * Pending user class
 */
public class PendingUser {

    /**
     * Pending user constructor
     * @param username username
     * @param password password
     * @param phone phone num
     * @param email email
     * @param nickname nickname
     * @param secretQuestion secret question
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
     * get username
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * set username
     * @param username username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * get password
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * set password
     * @param password password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * phone getter
     * @return phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * phone setter
     * @param phone phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

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

    /**
     * nickname getter
     * @return nickname
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * nickname setter
     * @param nickname nickname
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * secret question getter
     * @return secret question
     */
    public SecretQuestion getSecretQuestion() {
        return secretQuestion;
    }

    /**
     *secret question setter
     * @param secretQuestion secret question
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
