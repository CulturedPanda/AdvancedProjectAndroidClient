package com.example.advancedprojectandroidclient.entities;

/**
 * The secret question sent as part of PendingUser to the server.
 */
public class SecretQuestion {


    /**
     * Getter for the question's value.
     *
     * @return the question's value.
     */
    public String getQuestion() {
        return question;
    }

    /**
     * Setter for the question's value.
     *
     * @param question the question's value.
     */
    public void setQuestion(String question) {
        this.question = question;
    }

    /**
     * Getter for the answer's value.
     *
     * @return the answer's value.
     */
    public String getAnswer() {
        return answer;
    }


    /**
     * Setter for the answer's value.
     *
     * @param answer the answer's value.
     */
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
