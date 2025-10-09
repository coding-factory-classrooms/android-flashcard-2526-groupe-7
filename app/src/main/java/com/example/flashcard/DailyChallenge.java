package com.example.flashcard;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DailyChallenge extends AppCompatActivity {

    List<DailyChallengeApi> dailyChallenges;

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

        fetchDailyChallenges();

        Log.i("TAG", new Gson().toJson(dailyChallenges));

        DailyChallengeAdapter adapter = new DailyChallengeAdapter(dailyChallenges);

        RecyclerView recyclerView = findViewById(R.id.dailyChallengeRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setOnClickListener((position, dailyChallengeApi) -> {
            Intent intent = new Intent(this, Game.class);
            intent.putExtra("difficult", dailyChallengeApi.getDailyChallenge().getDifficulty());
            intent.putExtra("nbQuestion", Integer.valueOf(dailyChallengeApi.getDailyChallenge().getNumberOfQuestions()));
            intent.putExtra("isDailyChallenge", true);
            intent.putExtra("dailyChallengeQuestions", (Serializable) dailyChallengeApi.getDailyChallenge().getQuestions());
            startActivity(intent);
            this.startActivity(intent);
        });

        recyclerView.setAdapter(adapter);

    }

    public void fetchDailyChallenges(){
        SharedPreferences preferences = getSharedPreferences("Preferences", MODE_PRIVATE);
        Boolean isNewDay = preferences.getBoolean("isNewday", true);

        JsonDailyChallenge jsonDailyChallenge = new JsonDailyChallenge();

        Log.i("TAG", String.valueOf(isNewDay));

        if(isNewDay){
            dailyChallenges = jsonDailyChallenge.readApiDailyChallenges(this, 2);
        }
        else {
            dailyChallenges = jsonDailyChallenge.readLocalDailyChallenges(this);
        }

    }
}