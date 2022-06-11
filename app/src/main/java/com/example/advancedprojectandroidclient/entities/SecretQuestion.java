package com.example.advancedprojectandroidclient.entities;

public class SecretQuestion {

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public SecretQuestion(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    private String question;
    private String answer;
}
