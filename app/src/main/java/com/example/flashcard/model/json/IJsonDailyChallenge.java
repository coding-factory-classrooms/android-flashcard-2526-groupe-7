package com.example.flashcard.model.json;

import android.content.Context;

import com.example.flashcard.model.DailyChallenge;
import com.example.flashcard.model.DailyChallengeApi;

import java.io.File;
import java.util.List;

public interface IJsonDailyChallenge {

    public final String jsonDailyChallengeFileName = "daily_challenges.json";
    public List<DailyChallengeApi> readApiDailyChallenges(Context context, int numberToPick);

    public List<DailyChallengeApi> readLocalDailyChallenges(Context context);

    public void writeDailyChallenge(Context context, List<DailyChallengeApi> challenges);

    public void createLocalJson(Context context, File file);
}
