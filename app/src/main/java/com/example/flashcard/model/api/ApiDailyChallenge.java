package com.example.flashcard.model.api;

import android.content.Context;
import android.util.Log;

import com.example.flashcard.model.DailyChallenge;
import com.example.flashcard.model.DailyChallengeApiModel;
import com.example.flashcard.model.callback.DailyChallengeCallback;
import com.example.flashcard.model.json.JsonDailyChallenge;
import com.example.flashcard.utils.DateComparaison;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiDailyChallenge implements IApiDailyChallenge{


    @Override
    public void fetchApiAllDailyChallenge(Context context, String difficulty, DailyChallengeCallback callback) {

        Retrofit retrofit =new Retrofit.Builder()
                .baseUrl("https://students.gryt.tech/api/L2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        DailyChallengeApiUrl dailyChallenge = retrofit.create(DailyChallengeApiUrl.class);


        Call<List<DailyChallenge>> call = dailyChallenge.getAllDailyChallenge("");

        Log.i("API_URL", call.request().url().toString());

        dailyChallenge.getAllDailyChallenge("").enqueue(new Callback<List<DailyChallenge>>() {
            @Override
            public void onResponse(Call<List<DailyChallenge>> call, Response<List<DailyChallenge>> response) {
                if(response.isSuccessful() || !response.body().isEmpty()){
                    List<DailyChallenge> challenges = response.body();

                    Log.i("API", new Gson().toJson(response.body()));

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
                    String today = sdf.format(new Date());

                    long seed = today.hashCode();
                    Random random = new Random(seed);
                    Date todayDate = new Date();

                    List<DailyChallengeApiModel> filteredChallenges = new ArrayList<>();

                    for(DailyChallenge challenge : challenges){

                        Date latestDate = null;
                        String dateStr = challenge.getLatestDateAppears();
                        if (dateStr != null) {
                            try {
                                latestDate = sdf.parse(dateStr);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }

                        // If the daily challenge never appears before then add it
                        if(challenge.getLatestDateAppears() == null ){
                            filteredChallenges.add(new DailyChallengeApiModel(false, challenge));
                        }
                        else if(DateComparaison.isAtLeastOneMonthApart(todayDate, latestDate)){
                            filteredChallenges.add(new DailyChallengeApiModel(false, challenge));
                        }
                    }

                    Collections.shuffle(filteredChallenges, random);

                    List<DailyChallengeApiModel> selectedFilteredDailyChallenges = filteredChallenges.subList(0, Math.min(2, filteredChallenges.size()));

                    JsonDailyChallenge jsonDailyChallenge = new JsonDailyChallenge();
                    jsonDailyChallenge.writeDailyChallenge(context, selectedFilteredDailyChallenges);

                    callback.onSuccess(selectedFilteredDailyChallenges);
                }
            }

            @Override
            public void onFailure(Call<List<DailyChallenge>> call, Throwable t) {
                callback.onError(t);
            }
        });
    }
}
