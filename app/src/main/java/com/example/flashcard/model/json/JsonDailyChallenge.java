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
    public List<DailyChallenge> readDailyChallenges(Context context, int numberToPick) {
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


            List<DailyChallenge> filteredChallenges = new ArrayList<>();

            for(DailyChallenge challenge : challenges){
                // If the daily challenge never appears before then add it
                if(challenge.getLatestDateAppears() == null ){
                    filteredChallenges.add(challenge);
                }
                else if(DateComparaison.isAtLeastOneMonthApart(todayDate, challenge.getLatestDateAppears())){
                    filteredChallenges.add(challenge);
                }
            }

            Collections.shuffle(filteredChallenges, random);

            return filteredChallenges.subList(0, Math.min(numberToPick, filteredChallenges.size()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
