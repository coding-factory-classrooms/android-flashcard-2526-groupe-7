package com.example.flashcard.model.json;

import android.content.Context;

import com.example.flashcard.model.Question;

import java.util.List;

public interface IJsonQuestion {
    public List<Question> readQuestion(Context context, String jsonFile);
}
