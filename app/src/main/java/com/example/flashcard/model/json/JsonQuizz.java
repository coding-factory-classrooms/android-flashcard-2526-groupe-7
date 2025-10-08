package com.example.flashcard.model.json;

import android.content.Context;
import android.util.Log;

import com.example.flashcard.model.Level;
import com.example.flashcard.model.Quizz;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

public class JsonQuizz implements IJsonQuizz{
    private final String TAG = "JsonQuizz";
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private List<Quizz> quizz;

    @Override
    public List<Quizz> readQuizz(Context context) {
        InputStream inputStream = context.getResources().openRawResource(IJsonQuizz.jsonQuizzFileLink);

        try (Reader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"))) {
            Log.i(TAG, "Récupération des quiz réussie");
            quizz = gson.fromJson(reader, List.class);
            return quizz;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
