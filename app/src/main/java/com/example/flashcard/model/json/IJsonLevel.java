package com.example.flashcard.model.json;

import android.content.Context;

import com.example.flashcard.model.Level;

public interface IJsonLevel {
    public final String jsonLevelFileName = "level.json";
    public void writeLevel(Context context, Level level);
    public Level readLevel(Context context);
}
