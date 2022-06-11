package com.example.advancedprojectandroidclient.entities;

public class PendingUser {

    public PendingUser(String username, String password, String phone, String email, String nickname, SecretQuestion secretQuestion) {
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.email = email;
        this.nickname = nickname;
        this.secretQuestion = secretQuestion;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public SecretQuestion getSecretQuestion() {
        return secretQuestion;
    }

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
