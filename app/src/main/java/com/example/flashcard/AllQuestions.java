package com.example.flashcard;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

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

    List<Question> questions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_questions);

        ImageButton arrowBack = findViewById(R.id.backButton);

        arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



        // Fetch all questions from all quiz and display them
        ApiQuestion apiQuestion = new ApiQuestion();

        apiQuestion.fetchApiAllQuestionsFromQuiz(new QuestionCallback() {
            @Override
            public void onSuccess(List<Question> allQuestions) {
                questions = allQuestions;
                if(questions != null){
                    createRecyclerView();
                }
            }



            @Override
            public void onError(Throwable t) {

            }
        });
    }




    public void onItemClick(List<Question> question, View view) {
        Intent intent = new Intent(view.getContext(), Game.class);
        intent.putExtra("difficult", "easy");
        intent.putExtra("nbQuestion", 1);
        intent.putExtra("name","oneQuestion");
        intent.putExtra("oneQuestionBool",true);
        intent.putExtra("oneQuestion", (Serializable) question);
        intent.putExtra("timer","off");
        startActivity(intent);
    }

    public void createRecyclerView(){
        Log.i("ALl questions", "createRecyclerView: " + new Gson().toJson(questions));
        QuestionAdapter adapter = new QuestionAdapter(questions, AllQuestions.this::onItemClick);
        RecyclerView recyclerView = findViewById(R.id.questionRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}