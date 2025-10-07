package com.example.flashcard.model;

import android.content.Context;

import com.example.flashcard.model.json.JsonLevel;

public class Level implements ILevel{
    private int level;
    private float xp;
    private float goalXp;
    private final transient float xpMultiplicator = 1.03f;

    public Level(int level, int xp, int goalXp) {
        this.level = level;
        this.xp = xp;
        this.goalXp = goalXp;
    }

    public int getLevel() {
        return level;
    }

    public float getXp() {
        return xp;
    }

    public float getGoalXp() {
        return goalXp;
    }

    @Override
    public void addXp(int xpToAdd) {
        xp += xpToAdd;
        while(xp >= goalXp){
            xp -= goalXp;
            level++;
            goalXp *= xpMultiplicator;
        }

    }

    @Override
    public void updateJson(Context context, Level level) {
        JsonLevel jsonLevel = new JsonLevel();
        jsonLevel.writeLevel(context, level);
    }
}
