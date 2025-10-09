package com.example.flashcard.model.api;

import com.example.flashcard.model.QuizModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiQuizUrl {
    @GET("mamaquiz/themes/")
    Call<List<QuizModel>> getAllThemes();
}
