package com.example.flashcard.model.json;

import android.content.Context;
import android.util.Log;

import com.example.flashcard.model.QuizModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.List;

public class JsonQuizz implements IJsonQuizz{
    private final String TAG = "JsonQuizz";
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private List<QuizModel> quizModels;

    @Override
    public List<QuizModel> readQuizz(Context context) {
        InputStream inputStream = context.getResources().openRawResource(IJsonQuizz.jsonQuizzFileLink);

        try (Reader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"))) {
            Log.i(TAG, "Récupération des quiz réussie");

            Type quizzListType = new TypeToken<List<QuizModel>>(){}.getType();
            quizModels = gson.fromJson(reader, quizzListType);
            return quizModels;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
