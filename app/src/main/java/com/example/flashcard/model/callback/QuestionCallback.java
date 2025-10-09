package com.example.flashcard.model.callback;

import com.example.flashcard.model.Question;
import com.example.flashcard.model.QuizModel;

import java.util.List;

public interface QuestionCallback {
    void onSuccess(List<Question> questions);
    void onError(Throwable t);
}
