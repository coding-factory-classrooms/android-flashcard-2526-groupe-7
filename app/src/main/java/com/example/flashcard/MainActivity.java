package com.example.flashcard;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.flashcard.model.Level;
import com.example.flashcard.model.QuizStatistic;
import com.example.flashcard.model.Statistic;
import com.example.flashcard.model.json.JsonLevel;
import com.example.flashcard.model.json.JsonStatistic;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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
        ImageButton parameters = findViewById(R.id.parameters);
        ImageButton profilButton = findViewById(R.id.profilButton);

        findViewById(R.id.playButton).setOnClickListener(
                v -> navigateTo(ThemeSelector.class));

        findViewById(R.id.statButton).setOnClickListener(
                v -> navigateTo(Statistics.class));

        findViewById(R.id.allQuestionsButton).setOnClickListener(
                v -> navigateTo(AllQuestions.class));

        findViewById(R.id.aboutButton).setOnClickListener(
                v -> navigateTo(DailyChallenge.class));

        findViewById(R.id.dailyChallengeButton).setOnClickListener(v ->
                navigateTo(DailyChallenge.class));

        parameters.setOnClickListener(v -> {
            if (aboutButton.getVisibility() == View.GONE) {
                // Appear of statbutton and after aboutbutton
                statButton.setAlpha(0f);
                statButton.setVisibility(View.VISIBLE);
                statButton.animate()
                        .alpha(1f)
                        .setDuration(300)
                        .withEndAction(() -> {
                            aboutButton.setAlpha(0f);
                            aboutButton.setVisibility(View.VISIBLE);
                            aboutButton.animate().alpha(1f).setDuration(300).start();
                        }).start();
            } else {
                // Disappear of aboutbutton and after statbutton
                aboutButton.animate()
                        .alpha(0f)
                        .setDuration(300)
                        .withEndAction(() -> {
                            aboutButton.setVisibility(View.GONE);
                            statButton.animate()
                                    .alpha(0f)
                                    .setDuration(300)
                                    .withEndAction(() -> statButton.setVisibility(View.GONE))
                                    .start();
                        }).start();
            }
        });

        profilButton.setOnClickListener(v -> {
            // New instance of json level
            JsonLevel jsonlevel = new JsonLevel();

            // fetch data level from level.json
            Level level = jsonlevel.readLevel(this);

            // new instance of bottom sheet dialog
            BottomSheetDialog dialog = new BottomSheetDialog(this);
            dialog.setContentView(R.layout.dialog_user_info);

            TextView levelTextView = dialog.findViewById(R.id.levelTextView);
            levelTextView.setText("Niveau " +  String.valueOf(level.getLevel()));

            TextView xpTextView = dialog.findViewById(R.id.xpTextView);
            xpTextView.setText(level.getStringXpToFixed());

            TextView goalXpTextView = dialog.findViewById(R.id.goalXpTextView);
            goalXpTextView.setText(level.getStringGoalXpToFixed());

            LinearProgressIndicator progressBar = dialog.findViewById(R.id.progressBar);
            progressBar.setProgress(level.getProgress());

            // show the dialog
            dialog.show();
        });

        checkLastLoginDate();
    }

    public void navigateTo(Class targetClass){
        Intent intent = new Intent(this, targetClass);
        startActivity(intent);
    }

    public void saveLastLoginDate(){
        SharedPreferences prefs = getSharedPreferences("Preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        // Stock the current date to format yyyy-MM-dd
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String todayDate = sdf.format(new Date());

        editor.putString("lastLoginDate", todayDate);
        editor.apply();
    }

    public void checkLastLoginDate(){
        SharedPreferences prefs = getSharedPreferences("Preferences", MODE_PRIVATE);
        String lastLogin = prefs.getString("lastLoginDate", null);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String today = sdf.format(new Date());

        if(lastLogin == null || !lastLogin.equals(today)){
            saveLastLoginDate();

            prefs.edit().putBoolean("isNewday", true).apply();
        }
        else{
            prefs.edit().putBoolean("isNewday", false).apply();
        }
    }
}