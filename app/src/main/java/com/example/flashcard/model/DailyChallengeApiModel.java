package com.example.flashcard.model;

public class DailyChallengeApiModel {
    private boolean isCompleted;
    private DailyChallenge dailyChallenge;

    public DailyChallengeApiModel(boolean isCompleted, DailyChallenge dailyChallenge) {
        this.isCompleted = isCompleted;
        this.dailyChallenge = dailyChallenge;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public DailyChallenge getDailyChallenge() {
        return dailyChallenge;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
}
