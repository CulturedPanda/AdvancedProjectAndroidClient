package com.example.advancedprojectandroidclient.entities;

/**
 * secret question class
 */
public class SecretQuestion {

    /**
     * question getter
     * @return string question
     */
    public String getQuestion() {
        return question;
    }

    /**
     * set question
     * @param question question
     */
    public void setQuestion(String question) {
        this.question = question;
    }

    /**
     * answer getter
     * @return answer
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * answer setter
     * @param answer asnwer
     */
    public void setAnswer(String answer) {
        this.answer = answer;
    }

    /**
     * constructor
     * @param question question
     * @param answer answer
     */
    public SecretQuestion(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    private String question;
    private String answer;
}
