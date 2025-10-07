package com.example.flashcard.model.json.write;

import android.content.Context;

import com.example.flashcard.model.Level;
import com.example.flashcard.model.json.IJsonLevel;
import com.example.flashcard.model.json.read.ReadLevel;

public interface IWriteLevel{

    public void writeLevel(Context context, Level level);
}
