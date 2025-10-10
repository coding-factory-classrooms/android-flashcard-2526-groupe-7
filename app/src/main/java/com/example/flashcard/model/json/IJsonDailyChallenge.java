package com.example.flashcard.model.json;

import android.content.Context;

import com.example.flashcard.model.DailyChallengeApiModel;

import java.io.File;
import java.util.List;

public interface IJsonDailyChallenge {

    public final String jsonDailyChallengeFileName = "daily_challenges.json";

    public List<DailyChallengeApiModel> readLocalDailyChallenges(Context context);

    public void writeDailyChallenge(Context context, List<DailyChallengeApiModel> challenges);

    public void createLocalJson(Context context, File file);
}
