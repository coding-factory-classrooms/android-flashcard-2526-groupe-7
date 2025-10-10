package com.example.flashcard;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flashcard.adapter.DailyChallengeAdapter;
import com.example.flashcard.model.DailyChallengeApiModel;
import com.example.flashcard.model.api.ApiDailyChallenge;
import com.example.flashcard.model.callback.DailyChallengeCallback;
import com.example.flashcard.model.json.JsonDailyChallenge;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DailyChallenge extends AppCompatActivity {

    List<DailyChallengeApiModel> dailyChallenges = new ArrayList<>();

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

        // call fetch function
        fetchDailyChallenges(this);

        Log.i("TAG", new Gson().toJson(dailyChallenges));
        ImageButton arrowBack = findViewById(R.id.backButton);

        arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    // Fetch daily challenges data from either local storage or api
    public void fetchDailyChallenges(Context context){
        SharedPreferences preferences = getSharedPreferences("Preferences", MODE_PRIVATE);
        Boolean isNewDay = preferences.getBoolean("isNewday", true);

        JsonDailyChallenge jsonDailyChallenge = new JsonDailyChallenge();
        ApiDailyChallenge apiDailyChallenge = new ApiDailyChallenge();

        Log.i("TAG", String.valueOf(isNewDay));

        List<DailyChallengeApiModel> testDailychallenges =
                dailyChallenges = jsonDailyChallenge.readLocalDailyChallenges(this);

        if(isNewDay || dailyChallenges == null){
            apiDailyChallenge.fetchApiAllDailyChallenge(context,"", new DailyChallengeCallback() {
                @Override
                public void onSuccess(List<DailyChallengeApiModel> challengeApis) {
                    dailyChallenges = challengeApis;
                    Log.i("API", "API QUESTIONS");

                    createRecyclerView();
                }

                @Override
                public void onError(Throwable t) {
                    Log.e("API", "Erreur API", t);
                }
            });
        }
        else {
            dailyChallenges = jsonDailyChallenge.readLocalDailyChallenges(this);
            createRecyclerView();
        }

    }

    // Create recycler view with daily challenges adapter
    public void createRecyclerView(){

        DailyChallengeAdapter adapter = new DailyChallengeAdapter(dailyChallenges);

        RecyclerView recyclerView = findViewById(R.id.dailyChallengeRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setOnClickListener((position, dailyChallengeApi) -> {
            Intent intent = new Intent(this, Game.class);
            intent.putExtra("difficult", dailyChallengeApi.getDailyChallenge().getDifficulty());
            intent.putExtra("nbQuestion", Integer.valueOf(dailyChallengeApi.getDailyChallenge().getNumberOfQuestions()));
            intent.putExtra("isDailyChallenge", true);
            intent.putExtra("questions", (Serializable) dailyChallengeApi.getDailyChallenge().getQuestions());
            intent.putExtra("quizIndex", position);
            startActivity(intent);
        });

        recyclerView.setAdapter(adapter);
    }
}