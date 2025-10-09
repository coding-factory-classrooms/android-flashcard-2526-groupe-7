package com.example.flashcard.model.json;

import android.content.Context;

import com.example.flashcard.R;
import com.example.flashcard.model.QuizModel;

import java.util.List;

public interface IJsonQuizz {
    public final int jsonQuizzFileLink = R.raw.quizz;
    public List<QuizModel> readQuizz(Context context);
}
