package com.example.flashcard.model.callback;

import com.example.flashcard.model.DailyChallengeApiModel;
import com.example.flashcard.model.QuizModel;

import java.util.List;

public interface QuizCallback {

    void onSuccess(List<QuizModel> quizModels);
    void onError(Throwable t);
}
