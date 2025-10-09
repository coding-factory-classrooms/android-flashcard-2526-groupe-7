package com.example.flashcard.model.callback;

import com.example.flashcard.model.DailyChallengeApiModel;

import java.util.List;

public interface DailyChallengeCallback {
    void onSuccess(List<DailyChallengeApiModel> challengeApis);
    void onError(Throwable t);
}
