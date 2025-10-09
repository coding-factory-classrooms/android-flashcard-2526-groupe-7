package com.example.flashcard;
import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class AllQuestions extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_questions);

        RecyclerView recyclerView = findViewById(R.id.questionRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Add to list following questions
        ArrayList<Question> questions = new ArrayList<>();
        questions.add(new Question("cacaoooaoaoaoa"));
        questions.add(new Question("acaoacaocoac"));
        questions.add(new Question("efegegegegegeg"));
        questions.add(new Question("egegegegegegegegeg"));

        //link questions to recyclerview
        com.example.flashcard.QuestionAdapter adapter = new com.example.flashcard.QuestionAdapter(questions);
        recyclerView.setAdapter(adapter);
    }
}