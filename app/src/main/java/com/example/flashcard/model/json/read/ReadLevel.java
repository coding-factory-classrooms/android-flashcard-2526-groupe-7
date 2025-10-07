package com.example.flashcard.model.json.read;

import android.content.Context;
import android.util.Log;

import com.example.flashcard.model.Level;
import com.example.flashcard.model.json.IJsonLevel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class ReadLevel implements IReadLevel, IJsonLevel {

    private final String TAG = "ReadLevel";
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private Level level;

    public ReadLevel(Context context) {
        this.level = readLevel(context);
    }

    @Override
    public Level readLevel(Context context) {
        File file = new File(context.getFilesDir(), IJsonLevel.jsonLevelFileName);

        try {
            if (!file.isFile() && !file.createNewFile()) {
                Log.w(TAG, "Impossible de créer le fichier JSON du niveau");
                return null;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (Reader reader = new FileReader(file)) {
            Log.i(TAG, "Récupération du niveau réussie");
            level = gson.fromJson(reader, Level.class);

            if(level == null){
                return new Level(0, 0, 10);
            }

            return level;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Level getLevel() {
        return level;
    }
}
