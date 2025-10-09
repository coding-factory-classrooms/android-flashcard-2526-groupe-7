package com.example.flashcard.model.api;

import android.util.Log;

import com.example.flashcard.model.DailyChallenge;
import com.example.flashcard.model.DailyChallengeApiModel;
import com.example.flashcard.model.QuizModel;
import com.example.flashcard.model.callback.QuizCallback;
import com.example.flashcard.utils.DateComparaison;
import com.google.gson.Gson;

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

public class ApiQuiz implements IApiQuiz{

    @Override
    public void fetchAllApiQuiz(QuizCallback callback) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://students.gryt.tech/api/L2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiQuizUrl dailyChallenge = retrofit.create(ApiQuizUrl.class);

        dailyChallenge.getAllThemes().enqueue(new Callback<List<QuizModel>>() {
            @Override
            public void onResponse(Call<List<QuizModel>> call, Response<List<QuizModel>> response) {
                if (response.isSuccessful() || !response.body().isEmpty()) {
                    callback.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<QuizModel>> call, Throwable t) {
                callback.onError(t);
            }
        });
    }
}
