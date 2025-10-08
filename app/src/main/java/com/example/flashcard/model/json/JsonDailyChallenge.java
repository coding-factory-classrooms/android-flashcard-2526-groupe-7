package com.example.flashcard.model.json;

import android.content.Context;
import android.util.Log;

import com.example.flashcard.R;
import com.example.flashcard.model.DailyChallenge;
import com.example.flashcard.model.Question;
import com.example.flashcard.utils.DateComparaison;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class JsonDailyChallenge implements IJsonDailyChallenge{

    private final String TAG = "JsonDailyChallenge";
    private Gson gson = new GsonBuilder().setPrettyPrinting().setDateFormat("yyyy-MM-dd").create();
    private List<DailyChallenge> challenges;

    @Override
    public List<DailyChallenge> readDailyChallenges(Context context) {
        InputStream inputStream = context.getResources().openRawResource(R.raw.daily_challenges);

        try (Reader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"))) {
            Log.i(TAG, "Récupération des challenges du jour réussie");
            Type questionListType = new TypeToken<List<DailyChallenge>>(){}.getType();
            challenges = gson.fromJson(reader, questionListType);
            return challenges;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<DailyChallenge> readRandomDailyChallenges(Context context, int numberToPick) {
        InputStream inputStream = context.getResources().openRawResource(R.raw.daily_challenges);

        try (Reader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"))) {
            Log.i(TAG, "Récupération des challenges du jour réussie");
            Type questionListType = new TypeToken<List<DailyChallenge>>(){}.getType();
            List<DailyChallenge> allChallenges = gson.fromJson(reader, questionListType);

            List<DailyChallenge> filteredChallenges = new ArrayList<>();
            Date todayDate = new Date();

            for(DailyChallenge challenge : allChallenges){
                // If the daily challenge never appears before then add it
                if(challenge.getLatestDateAppears() == null ){
                    filteredChallenges.add(challenge);
                }
                else if(DateComparaison.isAtLeastOneMonthApart(todayDate, challenge.getLatestDateAppears())){
                    filteredChallenges.add(challenge);
                }
            }

            Collections.shuffle(filteredChallenges);

            // Return random filtered challenges
            return filteredChallenges.subList(0, numberToPick);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
