package com.example.flashcard.model.json;

import android.content.Context;

import com.example.flashcard.model.DailyChallenge;
import com.example.flashcard.model.DailyChallengeApi;

import java.util.List;

public interface IJsonDailyChallenge {
    public List<DailyChallengeApi> readDailyChallenges(Context context, int numberToPick);
}
