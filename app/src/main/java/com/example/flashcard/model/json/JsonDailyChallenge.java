package com.example.flashcard.model.json;

import android.content.Context;
import android.util.Log;

import com.example.flashcard.R;
import com.example.flashcard.model.DailyChallenge;
import com.example.flashcard.model.DailyChallengeApiModel;
import com.example.flashcard.model.api.ApiDailyChallenge;
import com.example.flashcard.model.callback.DailyChallengeCallback;
import com.example.flashcard.utils.DateComparaison;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class JsonDailyChallenge implements IJsonDailyChallenge{
    private final String TAG = "JsonDailyChallenge";
    private Gson gson = new GsonBuilder().setPrettyPrinting().setDateFormat("yyyy-MM-dd").create();
    private List<DailyChallenge> challenges;
    private List<DailyChallengeApiModel> challengeDatas;

    @Override
    public List<DailyChallengeApiModel> readLocalDailyChallenges(Context context) {
        File file = new File(context.getFilesDir(), IJsonDailyChallenge.jsonDailyChallengeFileName);

        createLocalJson(context, file);

        try (Reader reader = new FileReader(file)) {
            Log.i(TAG, "Récupération des defis journalier en local réussie");

            com.google.gson.JsonElement jsonElement = com.google.gson.JsonParser.parseReader(reader);
            Type dailyChallengeApiTypeList = new TypeToken<List<DailyChallengeApiModel>>(){}.getType();
            challengeDatas = gson.fromJson(reader, dailyChallengeApiTypeList);


            if(challengeDatas == null){
                ApiDailyChallenge apiDailyChallenge = new ApiDailyChallenge();

                apiDailyChallenge.fetchApiAllDailyChallenge(context,"", new DailyChallengeCallback() {
                    @Override
                    public void onSuccess(List<DailyChallengeApiModel> challengeApisFromApi) {
                        challengeDatas = challengeApisFromApi;
                    }

                    @Override
                    public void onError(Throwable t) {

                    }
                });
            }

            if (jsonElement.isJsonArray()) {
                challengeDatas = gson.fromJson(jsonElement, dailyChallengeApiTypeList);
            } else {
                challengeDatas = gson.fromJson(
                        jsonElement.getAsJsonObject().getAsJsonArray("dailyChallenges"),
                        dailyChallengeApiTypeList
                );
            }

            return challengeDatas;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void writeDailyChallenge(Context context, List<DailyChallengeApiModel> challenges) {
        File file = new File(context.getFilesDir(), IJsonDailyChallenge.jsonDailyChallengeFileName);

        createLocalJson(context, file);

        try(Writer writer = new FileWriter(file)){
            gson.toJson(challenges, writer);
            Log.i(TAG, "writeDailyChallenge: Ecriture en local des défis du jour");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void createLocalJson(Context context, File file) {
        try {
            if (!file.isFile() && !file.createNewFile()) {
                Log.w(TAG, "Impossible de créer le fichier JSON des défis du jour");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
