package com.example.flashcard.model.api;

import com.example.flashcard.model.Question;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;

public interface QuestionApiUrl {
    @GET("mamaquiz/")
    public List<Question> fetchQuestions(@Query("theme") String theme);
}
