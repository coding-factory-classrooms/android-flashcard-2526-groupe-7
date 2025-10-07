package com.example.flashcard.json.read;

import android.content.Context;

import com.example.flashcard.model.Level;

public interface IReadLevel {
    public Level readLevel(Context context, String jsonName);
}
