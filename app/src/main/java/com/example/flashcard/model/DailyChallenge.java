package com.example.flashcard.model;

import java.util.Date;
import java.util.List;

public class DailyChallenge {

    private String challengeName;
    private String numberOfQuestions;
    private String difficulty;
    private List<Question> questions;
    private String latestDateAppears;

    public String getChallengeName() {
        return challengeName;
    }

    public String getNumberOfQuestions() {
        return numberOfQuestions;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public String getLatestDateAppears() {
        return latestDateAppears;
    }

    public void setLatestDateAppears(String latestDateAppears) {
        this.latestDateAppears = latestDateAppears;
    }
}
