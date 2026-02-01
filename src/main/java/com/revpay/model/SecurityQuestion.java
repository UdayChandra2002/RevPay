package com.revpay.model;

public class SecurityQuestion {

    private int sqId;
    private int userId;
    private String question;
    private String answerHash;

    public SecurityQuestion() {}

    public SecurityQuestion(int sqId, int userId,
                            String question, String answerHash) {
        this.sqId = sqId;
        this.userId = userId;
        this.question = question;
        this.answerHash = answerHash;
    }

    public int getSqId() { return sqId; }
    public void setSqId(int sqId) { this.sqId = sqId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getQuestion() { return question; }
    public void setQuestion(String question) { this.question = question; }

    public String getAnswerHash() { return answerHash; }
    public void setAnswerHash(String answerHash) { this.answerHash = answerHash; }
}
