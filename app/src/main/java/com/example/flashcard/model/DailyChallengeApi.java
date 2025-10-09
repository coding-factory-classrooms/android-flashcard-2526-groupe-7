package com.example.flashcard.model;

public class DailyChallengeApi {
    private boolean isCompleted;
    private DailyChallenge dailyChallenge;

    public DailyChallengeApi(boolean isCompleted, DailyChallenge dailyChallenge) {
        this.isCompleted = isCompleted;
        this.dailyChallenge = dailyChallenge;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public DailyChallenge getDailyChallenge() {
        return dailyChallenge;
    }
}
