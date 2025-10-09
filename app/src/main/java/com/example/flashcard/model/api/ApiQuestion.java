package com.example.flashcard.model.api;

import android.util.Log;

import com.example.flashcard.model.Question;
import com.example.flashcard.model.QuizModel;
import com.example.flashcard.model.callback.QuestionCallback;
import com.example.flashcard.model.callback.QuizCallback;
import com.google.gson.Gson;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiQuestion implements IApiQuestion{

    @Override
    public void fetchApiQuestion(String quizName, QuestionCallback callback) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://students.gryt.tech/api/L2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        QuestionApiUrl questionApi = retrofit.create(QuestionApiUrl.class);

        questionApi.fetchQuestions(quizName).enqueue(new Callback<List<Question>>() {
            @Override
            public void onResponse(Call<List<Question>> call, Response<List<Question>> response) {
                if (response.isSuccessful() || !response.body().isEmpty()) {

                    Log.i("API QUESTION", new Gson().toJson(response.body()));
                    callback.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Question>> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

    @Override
    public void fetchApiAllQuestionsFromQuiz(QuestionCallback callback) {
        ApiQuiz apiQuiz = new ApiQuiz();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://students.gryt.tech/api/L2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        QuestionApiUrl questionApi = retrofit.create(QuestionApiUrl.class);

        apiQuiz.fetchAllApiQuiz(new QuizCallback() {
            @Override
            public void onSuccess(List<QuizModel> quizModels) {

                for(QuizModel quiz : quizModels){
                    questionApi.fetchQuestions(quiz.getLink()).enqueue(new Callback<List<Question>>() {
                        @Override
                        public void onResponse(Call<List<Question>> call, Response<List<Question>> response) {
                            if(response.isSuccessful() || !response.body().isEmpty()){
                                callback.onSuccess(response.body());
                            }
                        }

                        @Override
                        public void onFailure(Call<List<Question>> call, Throwable t) {
                            callback.onError(t);
                        }
                    });
                }
            }


            @Override
            public void onError(Throwable t) {

            }
        });
    }
}
