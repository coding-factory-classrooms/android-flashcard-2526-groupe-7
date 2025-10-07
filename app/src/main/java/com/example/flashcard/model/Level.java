package com.example.flashcard.model;

public class Level {
    int level;
    int xp;
    int goalXp;

    public Level(int level, int xp, int goalXp) {
        this.level = level;
        this.xp = xp;
        this.goalXp = goalXp;
    }

    public int getLevel() {
        return level;
    }

    public int getXp() {
        return xp;
    }

    public int getGoalXp() {
        return goalXp;
    }
}
