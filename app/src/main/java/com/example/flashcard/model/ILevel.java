package com.example.flashcard.model;

import android.content.Context;

public interface ILevel {
    public void addXp(int xpToAdd);

    public void updateJson(Context context, Level level);
}
