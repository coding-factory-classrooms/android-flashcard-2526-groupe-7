package com.example.flashcard;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

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

public class ThemeSelector extends AppCompatActivity implements quizzAdapter.OnItemClickListener  {

    List<Quizz> quizz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme_selector);
        ImageButton arrowBack = findViewById(R.id.backButton);



        arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        JsonQuizz jsonQuizz = new JsonQuizz();
        quizz = jsonQuizz.readQuizz(this);

        RecyclerView recyclerView = findViewById(R.id.themeRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        quizzAdapter adapter = new quizzAdapter(quizz, this);
        recyclerView.setAdapter(adapter);
    }
    public void onItemClick(String quizzName) {
        Log.d("quizzname :",quizzName);
        Intent intent = new Intent(this, LevelSelector.class);
        intent.putExtra("name",quizzName);
        startActivity(intent);
    }
}