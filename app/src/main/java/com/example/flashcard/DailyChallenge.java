package com.example.flashcard;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.flashcard.model.json.JsonDailyChallenge;
import com.google.gson.Gson;

import java.util.List;

public class DailyChallenge extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_daily_challenge);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        JsonDailyChallenge jsonDailyChallenge = new JsonDailyChallenge();

        List<com.example.flashcard.model.DailyChallenge> dailyChallenges = jsonDailyChallenge.readRandomDailyChallenges(this, 2);

        Log.i("TAG", new Gson().toJson(dailyChallenges));
    }
}