package com.example.flashcard.model.api;

import com.example.flashcard.model.callback.QuestionCallback;
import com.example.flashcard.model.callback.QuizCallback;

import java.util.List;

public interface IApiQuestion {
    public void fetchApiQuestion(String quizName, QuestionCallback callback);

    public void fetchApiAllQuestionsFromQuiz(QuestionCallback callback);
}
