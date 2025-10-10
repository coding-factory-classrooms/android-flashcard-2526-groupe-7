package com.example.flashcard.model;

import android.content.Context;
import android.util.Log;

import com.example.flashcard.Statistics;
import com.example.flashcard.model.json.JsonStatistic;

public class Statistic implements IStatistic{
    private int totalGamesPlayed;
    private int totalCorrectAnswers;
    private int totalWrongAnswers;
    private float totalTimePlayed;
    private float averageTimeGame;

    public Statistic(int totalGamesPlayed, int totalCorrectAnswers, int totalWrongAnswers, float totalTimePlayed, float averageTimeGame) {
        this.totalGamesPlayed = totalGamesPlayed;
        this.totalCorrectAnswers = totalCorrectAnswers;
        this.totalWrongAnswers = totalWrongAnswers;
        this.totalTimePlayed = totalTimePlayed;
        this.averageTimeGame = averageTimeGame;
    }

    @Override
    public void updateStatistic(Context context, QuizStatistic quizStatistic) {
        JsonStatistic jsonStatistic = new JsonStatistic();

        Statistic statistic = jsonStatistic.readStatistic(context);

        statistic.totalGamesPlayed ++;
        statistic.totalCorrectAnswers += quizStatistic.getScore();
        int wrongAnswers = quizStatistic.getTotalQuestions() - quizStatistic.getScore();
        statistic.totalWrongAnswers += wrongAnswers;
        statistic.totalTimePlayed += quizStatistic.getTimeElapsed();

        statistic.averageTimeGame = totalTimePlayed / totalGamesPlayed;


        Log.i("TAG", String.valueOf(totalWrongAnswers));

        jsonStatistic.writeStatistic(context, statistic);
    }


    public int getTotalCorrectAnswers() {
        return totalCorrectAnswers;
    }

    public int getTotalGamesPlayed() {
        return totalGamesPlayed;
    }

    public int getTotalWrongAnswers() {
        return totalWrongAnswers;
    }

    public float getTotalTimePlayed() {
        return totalTimePlayed;
    }

    public float getAverageTimeGame() {
        return averageTimeGame;
    }
}
