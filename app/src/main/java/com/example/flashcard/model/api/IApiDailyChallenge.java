package com.example.flashcard.model.api;

import android.content.Context;

import com.example.flashcard.model.callback.DailyChallengeCallback;

public interface IApiDailyChallenge {

    public void fetchApiAllDailyChallenge(Context context, String difficulty, DailyChallengeCallback callback);
}
