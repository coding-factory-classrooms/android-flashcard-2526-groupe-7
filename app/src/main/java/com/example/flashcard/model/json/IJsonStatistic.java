package com.example.flashcard.model.json;

import android.content.Context;

import com.example.flashcard.model.Level;
import com.example.flashcard.model.Statistic;

public interface IJsonStatistic {

    public final String jsonStatisticFileName = "statistic.json";

    public void writeStatistic(Context context, Statistic statistic);
    public Statistic readStatistic(Context context);
}
