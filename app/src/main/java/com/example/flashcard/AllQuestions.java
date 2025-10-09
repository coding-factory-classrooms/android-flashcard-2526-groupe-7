package com.example.flashcard;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flashcard.model.Question;
import com.example.flashcard.model.QuizModel;
import com.example.flashcard.model.api.ApiQuestion;
import com.example.flashcard.model.callback.QuestionCallback;
import com.example.flashcard.adapter.QuestionAdapter;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.List;

public class AllQuestions extends AppCompatActivity implements QuestionAdapter.OnItemClickListener {

    List<QuizModel> quizz;
    List<Question> questions;
    List<Question> newQuestions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_questions);

        RecyclerView recyclerView = findViewById(R.id.questionRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ApiQuestion apiQuestion = new ApiQuestion();

        apiQuestion.fetchApiAllQuestionsFromQuiz(new QuestionCallback() {
            @Override
            public void onSuccess(List<Question> questions) {
                Log.i("ALL QUESTIONS", new Gson().toJson(questions));
                QuestionAdapter adapter = new QuestionAdapter(questions, AllQuestions.this::onItemClick);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onError(Throwable t) {

            }
        });
    }

    public void onItemClick(List<Question> question, View view) {
        Intent intent = new Intent(view.getContext(), Game.class);
        intent.putExtra("difficult", "");
        intent.putExtra("nbQuestion", 1);
        intent.putExtra("name","oneQuestion");
        intent.putExtra("oneQuestionBool",true);
        intent.putExtra("oneQuestion", (Serializable) question);
        startActivity(intent);
    }
}