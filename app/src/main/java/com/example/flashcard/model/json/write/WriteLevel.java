package com.example.flashcard.model.json.write;

import android.content.Context;

import com.example.flashcard.model.Level;
import com.example.flashcard.model.json.IJsonLevel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class WriteLevel implements IWriteLevel, IJsonLevel {

    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public void writeLevel(Context context, Level level) {

        File file = new File(context.getFilesDir(), IJsonLevel.jsonLevelFileName);

        try(Writer writer = new FileWriter(file)){
            gson.toJson(level, writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
