package com.example.flashcard.model;

public class QuizStatistic {
    private float timeElapsed;
    private int score;
    private int totalQuestions;

    public QuizStatistic(float timeElapsed, int score, int totalQuestions) {
        this.timeElapsed = timeElapsed;
        this.score = score;
        this.totalQuestions = totalQuestions;
    }

    public float getTimeElapsed() {
        return timeElapsed;
    }

    public int getScore() {
        return score;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }
}
