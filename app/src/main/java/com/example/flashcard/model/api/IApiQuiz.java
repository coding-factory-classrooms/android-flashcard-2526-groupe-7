package com.example.flashcard.model.api;

import com.example.flashcard.model.QuizModel;
import com.example.flashcard.model.callback.QuizCallback;

import java.util.List;

public interface IApiQuiz {
    public void fetchAllApiQuiz(QuizCallback callback);
}
