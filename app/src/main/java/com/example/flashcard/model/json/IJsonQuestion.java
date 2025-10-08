package com.example.flashcard.model.json;

import android.content.Context;

import com.example.flashcard.R;
import com.example.flashcard.model.Question;
import com.example.flashcard.model.Quizz;

import java.util.List;

public interface IJsonQuestion {
    public final int jsonQuestionFileLink = R.raw.questions;
    public List<Question> readQuestion(Context context);
}
