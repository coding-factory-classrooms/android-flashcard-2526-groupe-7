package com.example.flashcard.model.json;

import android.content.Context;
import android.util.Log;

import com.example.flashcard.R;
import com.example.flashcard.model.Question;
import com.example.flashcard.model.Quizz;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class JsonQuestion implements IJsonQuestion{

    private final String TAG = "JsonQuestion";
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private List<Question> questions;
    @Override
    public List<Question> readQuestion(Context context, String jsonFile) {
        InputStream inputStream = context.getResources().openRawResource(
            context.getResources().getIdentifier(jsonFile, "raw", context.getPackageName())
        );

        try (Reader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"))) {
            Log.i(TAG, "Récupération des questions réussie");
            Type questionListType = new TypeToken<List<Question>>(){}.getType();
            questions = gson.fromJson(reader, questionListType);
            return questions;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
