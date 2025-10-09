package com.example.flashcard;
import static java.security.AccessController.getContext;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flashcard.adapter.quizzAdapter;
import com.example.flashcard.model.Question;
import com.example.flashcard.model.Quizz;
import com.example.flashcard.model.json.JsonQuestion;
import com.example.flashcard.model.json.JsonQuizz;
import com.example.flashcard.adapter.QuestionAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AllQuestions extends AppCompatActivity implements QuestionAdapter.OnItemClickListener {

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
                String quizzLink= item.getLink();
                newQuestions = jsonQuestion.readQuestion(this, quizzLink);
                questions.addAll(newQuestions);
            }
        } else {
            return;
        }
            QuestionAdapter adapter = new QuestionAdapter(questions, this);
            recyclerView.setAdapter(adapter);
    }

    public void onItemClick(List<Question> question, View view) {
        Intent intent = new Intent(view.getContext(), Game.class);
        intent.putExtra("difficult", "easy");
        intent.putExtra("nbQuestion", 1);
        intent.putExtra("name","oneQuestion");
        intent.putExtra("oneQuestionBool",true);
        intent.putExtra("oneQuestion", (Serializable) question);
        startActivity(intent);
    }
}