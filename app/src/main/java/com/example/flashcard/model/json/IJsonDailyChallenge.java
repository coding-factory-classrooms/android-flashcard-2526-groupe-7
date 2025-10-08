package com.example.flashcard.model.json;

import android.content.Context;

import com.example.flashcard.model.DailyChallenge;

import java.util.List;

public interface IJsonDailyChallenge {
    public List<DailyChallenge> readDailyChallenges(Context context);

    public List<DailyChallenge> readRandomDailyChallenges(Context context, int numberToPick);
}
