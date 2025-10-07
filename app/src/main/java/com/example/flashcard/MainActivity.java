package com.example.flashcard;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.flashcard.json.read.IReadLevel;
import com.example.flashcard.json.read.ReadLevel;
import com.example.flashcard.model.Level;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        findViewById(R.id.playButton).setOnClickListener(
                v -> navigateTo(ThemeSelector.class));

        findViewById(R.id.statButton).setOnClickListener(
                v -> navigateTo(Statistics.class));

        findViewById(R.id.allQuestionsButton).setOnClickListener(
                v -> navigateTo(AllQuestions.class));

        findViewById(R.id.aboutButton).setOnClickListener(
                v -> navigateTo(About.class));

        Level level = new Level(0, 0, 10);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        File file = new File(this.getFilesDir(), "test.json");
        try(Writer writer = new FileWriter(file)){
            gson.toJson(level, writer);
        }catch (IOException e) {
            throw new RuntimeException(e);
        }

        IReadLevel readLevel = new ReadLevel();

        Level level2 = readLevel.readLevel(this, "test.json");
    }

    public void navigateTo(Class targetClass){
        Intent intent = new Intent(this, targetClass);
        startActivity(intent);
    }
}