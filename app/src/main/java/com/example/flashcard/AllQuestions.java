package com.example.flashcard;
import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flashcard.model.Quizz;
import com.example.flashcard.model.json.JsonQuestion;
import com.example.flashcard.model.json.JsonQuizz;

import java.util.ArrayList;
import java.util.List;

public class AllQuestions extends AppCompatActivity {

    List<Quizz> quizz;
    List<Question> questions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_questions);

        RecyclerView recyclerView = findViewById(R.id.questionRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        JsonQuestion jsonQuestion = new JsonQuestion();
        questions = jsonQuestion.readQuestion(this, "questions");

        JsonQuizz jsonQuizz = new JsonQuizz();
        quizz = jsonQuizz.readQuizz(this);


        //link questions to recyclerview
        com.example.flashcard.QuestionAdapter adapter = new com.example.flashcard.QuestionAdapter(questions);
        recyclerView.setAdapter(adapter);
    }
}