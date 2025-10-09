package com.example.flashcard;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.flashcard.model.Statistic;
import com.example.flashcard.model.json.JsonStatistic;

public class Statistics extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_statistics);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_statistics), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        JsonStatistic jsonStatistic = new JsonStatistic();

        Statistic statistic = jsonStatistic.readStatistic(this);

        TextView totalGamesValue = findViewById(R.id.totalGamesValue);
        totalGamesValue.setText(String.valueOf(statistic.getTotalGamesPlayed()));

        TextView totalCorrectValue = findViewById(R.id.totalCorrectValue);
        totalCorrectValue.setText(String.valueOf(statistic.getTotalCorrectAnswers()));

        TextView totalWrongValue = findViewById(R.id.totalWrongValue);
        totalWrongValue.setText(String.valueOf(statistic.getTotalWrongAnswers()));

        TextView averageTimeValue = findViewById(R.id.averageTimeValue);
        averageTimeValue.setText(String.valueOf(statistic.getAverageTimeGame()));
    }
}