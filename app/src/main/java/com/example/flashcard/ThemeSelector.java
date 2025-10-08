package com.example.flashcard;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flashcard.adapter.quizzAdapter;
import com.example.flashcard.model.Question;
import com.example.flashcard.model.Quizz;
import com.example.flashcard.model.json.JsonQuestion;
import com.example.flashcard.model.json.JsonQuizz;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ThemeSelector extends AppCompatActivity {

    List<Quizz> listeDesPays;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme_selector);



        JsonQuizz jsonQuizz = new JsonQuizz();
        listeDesPays = jsonQuizz.readQuizz(this);

        listeDesPays.add(new Quizz("Test", "Https","bgopen"));


        RecyclerView recyclerView = findViewById(R.id.themeRecyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        quizzAdapter adapter = new quizzAdapter(listeDesPays);
        recyclerView.setAdapter(adapter);
    }
}