package com.example.flashcard;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flashcard.model.Question;
import com.example.flashcard.model.Quizz;
import com.example.flashcard.model.json.JsonQuestion;
import com.example.flashcard.model.json.JsonQuizz;
import com.example.flashcard.adapter.QuestionAdapter;

import java.util.ArrayList;
import java.util.List;

public class AllQuestions extends AppCompatActivity {

    List<Quizz> quizz;
    List<Question> questions;
    List<Question> newQuestions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_questions);

        RecyclerView recyclerView = findViewById(R.id.questionRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        questions = new ArrayList<>();

        JsonQuizz jsonQuizz = new JsonQuizz();
        JsonQuestion jsonQuestion = new JsonQuestion();

        quizz = jsonQuizz.readQuizz(this);


        if (quizz != null) {
            for (Quizz item : quizz) {
                // S'assurer que l'item et son nom ne sont pas null avant de logger
                String quizzName = (item != null && item.getName() != null)
                        ? item.getLink()
                        : "Nom Quizz Inconnu/NULL";

                        newQuestions = jsonQuestion.readQuestion(this, quizzName);
                        questions.addAll(newQuestions);
            }
        } else {
        }
            QuestionAdapter adapter = new QuestionAdapter(questions);
            recyclerView.setAdapter(adapter);
    }
}