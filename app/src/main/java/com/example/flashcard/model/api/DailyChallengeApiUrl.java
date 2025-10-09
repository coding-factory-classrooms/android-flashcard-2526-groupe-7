package com.example.flashcard.model.api;

import com.example.flashcard.model.DailyChallenge;
import com.example.flashcard.model.callback.DailyChallengeCallback;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface DailyChallengeApiUrl {
    @GET("mamaquiz/")
    Call<List<DailyChallenge>> getAllDailyChallenge(@Query("difficulty") String difficulty);
}
