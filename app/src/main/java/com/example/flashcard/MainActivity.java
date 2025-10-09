package com.example.flashcard;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

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
                v -> navigateTo(Game.class));

        findViewById(R.id.statButton).setOnClickListener(
                v -> navigateTo(Statistics.class));

        findViewById(R.id.allQuestionsButton).setOnClickListener(
                v -> navigateTo(AllQuestions.class));

        findViewById(R.id.aboutButton).setOnClickListener(
                v -> navigateTo(About.class));

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