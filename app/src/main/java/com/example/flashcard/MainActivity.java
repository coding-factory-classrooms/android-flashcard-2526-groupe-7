package com.example.flashcard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.flashcard.model.Level;
import com.example.flashcard.model.QuizStatistic;
import com.example.flashcard.model.Statistic;
import com.example.flashcard.model.json.JsonStatistic;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Button playButton = findViewById(R.id.playButton);
        Button statButton = findViewById(R.id.statButton);
        Button aboutButton = findViewById(R.id.aboutButton);
        Button allQuestionsButton = findViewById(R.id.allQuestionsButton);
        Button parameters = findViewById(R.id.parameters);

        findViewById(R.id.playButton).setOnClickListener(
                v -> navigateTo(LevelSelector.class));

        findViewById(R.id.statButton).setOnClickListener(
                v -> navigateTo(Statistics.class));

        findViewById(R.id.allQuestionsButton).setOnClickListener(
                v -> navigateTo(AllQuestions.class));

        findViewById(R.id.aboutButton).setOnClickListener(
                v -> navigateTo(About.class));

        findViewById(R.id.dailyChallengeButton).setOnClickListener(v ->
                navigateTo(DailyChallenge.class));

        parameters.setOnClickListener(v -> {
            if (aboutButton.getVisibility() == View.GONE) {
                aboutButton.setVisibility(View.VISIBLE);
                statButton.setVisibility(View.VISIBLE);
            } else {
                aboutButton.setVisibility(View.GONE);
                statButton.setVisibility(View.GONE);
            }
        });
    }

    public void navigateTo(Class targetClass){
        Intent intent = new Intent(this, targetClass);
        startActivity(intent);
    }
}