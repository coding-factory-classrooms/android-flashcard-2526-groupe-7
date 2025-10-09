package com.example.flashcard.model.json;

import android.content.Context;
import android.util.Log;

import com.example.flashcard.model.Level;
import com.example.flashcard.model.Statistic;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

public class JsonStatistic implements IJsonStatistic{

    private final String TAG = "JsonStatistic";
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private Statistic statistic;


    @Override
    public Statistic readStatistic(Context context) {
        File file = new File(context.getFilesDir(), IJsonStatistic.jsonStatisticFileName);

        try {
            if (!file.isFile() && !file.createNewFile()) {
                Log.w(TAG, "Impossible de créer le fichier JSON des statistiques.");
                return null;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (Reader reader = new FileReader(file)) {
            Log.i(TAG, "Récupération du niveau réussie");
            statistic = gson.fromJson(reader, Statistic.class);

            if(statistic == null){
                statistic = new Statistic(0, 0, 0, 0, 0);
                return statistic;
            }

            return statistic;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void writeStatistic(Context context, Statistic statistic) {
        File file = new File(context.getFilesDir(), IJsonStatistic.jsonStatisticFileName);

        Log.i(TAG, new Gson().toJson(statistic));
        try(Writer writer = new FileWriter(file)){
            gson.toJson(statistic, writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
