package com.example.flashcard.model.api;

import com.example.flashcard.model.callback.DailyChallengeCallback;

public interface IApiDailyChallenge {

    public void fetchApiAllDailyChallenge(String difficulty, DailyChallengeCallback callback);
}
