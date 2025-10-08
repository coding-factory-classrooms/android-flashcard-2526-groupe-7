package com.example.flashcard;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flashcard.adapter.DailyChallengeAdapter;
import com.example.flashcard.model.DailyChallengeApi;
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

        List<DailyChallengeApi> dailyChallenges = jsonDailyChallenge.readDailyChallenges(this, 2);

        Log.i("TAG", new Gson().toJson(dailyChallenges));

        DailyChallengeAdapter adapter = new DailyChallengeAdapter(dailyChallenges);

        RecyclerView recyclerView = findViewById(R.id.dailyChallengeRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    }
}