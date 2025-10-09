package com.example.flashcard.model.json;

import android.content.Context;
import android.util.Log;

import com.example.flashcard.R;
import com.example.flashcard.model.DailyChallenge;
import com.example.flashcard.model.DailyChallengeApiModel;
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

    @Override
    public List<DailyChallengeApiModel> readApiDailyChallenges(Context context, int numberToPick) {
        InputStream inputStream = context.getResources().openRawResource(R.raw.daily_challenges);

        try (Reader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"))) {
            Log.i(TAG, "Récupération des challenges du jour réussie");
            Type questionListType = new TypeToken<List<DailyChallenge>>(){}.getType();
            challenges = gson.fromJson(reader, questionListType);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
            String today = sdf.format(new Date());

            long seed = today.hashCode();
            Random random = new Random(seed);
            Date todayDate = new Date();

            List<DailyChallengeApiModel> filteredChallenges = new ArrayList<>();

            for(DailyChallenge challenge : challenges){
                // If the daily challenge never appears before then add it
                if(challenge.getLatestDateAppears() == null ){
                    filteredChallenges.add(new DailyChallengeApiModel(false, challenge));
                }
                else if(DateComparaison.isAtLeastOneMonthApart(todayDate, challenge.getLatestDateAppears())){
                    filteredChallenges.add(new DailyChallengeApiModel(false, challenge));
                }
                else if(DateComparaison.isSameDay(todayDate, challenge.getLatestDateAppears())){
                    filteredChallenges.add(new DailyChallengeApiModel(true, challenge));
                }
            }

            Collections.shuffle(filteredChallenges, random);

            List<DailyChallengeApiModel> selectedFilteredDailyChallenges = filteredChallenges.subList(0, Math.min(numberToPick, filteredChallenges.size()));

            writeDailyChallenge(context, selectedFilteredDailyChallenges);

            return selectedFilteredDailyChallenges;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<DailyChallengeApiModel> readLocalDailyChallenges(Context context) {
        File file = new File(context.getFilesDir(), IJsonDailyChallenge.jsonDailyChallengeFileName);

        createLocalJson(context, file);

        try (Reader reader = new FileReader(file)) {
            Log.i(TAG, "Récupération des defis journalier en local réussie");

            com.google.gson.JsonElement jsonElement = com.google.gson.JsonParser.parseReader(reader);
            Type dailyChallengeApiTypeList = new TypeToken<List<DailyChallengeApiModel>>(){}.getType();
            List<DailyChallengeApiModel> challengeApis = gson.fromJson(reader, dailyChallengeApiTypeList);


            if(challengeApis == null){
                return readApiDailyChallenges(context, 2);
            }

            if (jsonElement.isJsonArray()) {
                challengeApis = gson.fromJson(jsonElement, dailyChallengeApiTypeList);
            } else {
                challengeApis = gson.fromJson(
                        jsonElement.getAsJsonObject().getAsJsonArray("dailyChallenges"),
                        dailyChallengeApiTypeList
                );
            }

            return challengeApis;
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
