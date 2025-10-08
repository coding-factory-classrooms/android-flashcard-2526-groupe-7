package com.example.flashcard;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flashcard.question.questionAdapter;
import com.example.flashcard.question.questionModel;

import java.util.ArrayList;
import java.util.List;

public class Game extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // Récupération des vues
        TextView score= findViewById(R.id.scoreTextView);
        TextView questionTitle = findViewById(R.id.questionTitleTextView);

        // Initialisation du score
        score.setText("0");

        // Configuration du RecyclerView
        RecyclerView recyclerView = findViewById(R.id.gameRecyclerView);
        recyclerView.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false){

                    }
        );
        recyclerView.setHasFixedSize(true);

        // Création des données de questions
        List<questionModel> questions = new ArrayList<>();
        // Note: L'ordre des arguments pour questionModel est important
        questions.add(new questionModel("Capitale de la France ?", new String[]{"Paris", "Rome", "Madrid"}, 0,"http","http",20,"song"));
        questions.add(new questionModel("Capitale de la Test2 ?", new String[]{"test2", "test2", "test2"}, 0,"http","http",20,"song"));
        questions.add(new questionModel("Capitale de la Test3 ?", new String[]{"est3", "test3", "test3"}, 0,"http","http",20,"song"));

        // Création et assignation de l'adaptateur
        questionAdapter adapter = new questionAdapter(questions, recyclerView,score,questionTitle);
        recyclerView.setAdapter(adapter);
    }
}