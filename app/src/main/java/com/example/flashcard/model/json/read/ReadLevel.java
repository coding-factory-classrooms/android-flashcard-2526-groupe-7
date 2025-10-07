package com.example.flashcard.json.read;

import android.content.Context;
import android.util.Log;

import com.example.flashcard.model.Level;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class ReadLevel implements IReadLevel{

    private final String TAG = "ReadLevel";
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private Level level;

    public Level readLevel(Context context, String jsonName) {

        File file = new File(context.getFilesDir(), jsonName);

        try(Reader reader = new FileReader(file)){
            Log.i(TAG, "Récuperation du niveau réussi");
            level = gson.fromJson(reader, Level.class);
            return level;
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Level getLevel() {
        return level;
    }
}
