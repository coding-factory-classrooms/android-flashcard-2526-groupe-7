package com.example.flashcard;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.flashcard.model.AnswerOption;
import com.example.flashcard.model.Level;
import com.example.flashcard.model.Question;
import com.example.flashcard.model.json.JsonLevel;

import org.w3c.dom.Text;

public class EndGameStats extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_end_game_stats);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_end_game_stats), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        findViewById(R.id.homeButton).setOnClickListener(v -> navigateTo(MainActivity.class));
        findViewById(R.id.retryErrorButton).setOnClickListener(v -> navigateTo(Game.class));

        AnswerOption[] answerOptions = new AnswerOption[1];
        AnswerOption option = new AnswerOption(1, "test");

        answerOptions[0] = option;

        Question question = new Question("Capitale de la France ?", answerOptions, 0, "http", "http", 20, "song");

        // A remplacer par du dynamique après
        int numberOfQuestions = question.getAnswerOptions().length;
        int score = numberOfQuestions - 1;
        String difficulty = "Facile";
        int xpToAdd = 20;
        //

        TextView scoreTextView = findViewById(R.id.scoreTextView);
        scoreTextView.setText(score + "/" + numberOfQuestions);

        TextView difficultyTextView = findViewById(R.id.difficultyTextView);
        difficultyTextView.setText("Difficulté: " + difficulty);

        TextView percentageTextView = findViewById(R.id.percentageTextView);
        int successRate = (score * 100) / numberOfQuestions;
        percentageTextView.setText("Taux de réussite: " + successRate + "%");

        JsonLevel jsonLevel = new JsonLevel();

        Level level = jsonLevel.readLevel(this);
        level.addXp(xpToAdd);
        level.updateJson(this, level);

        TextView xpTextView = findViewById(R.id.xpTextView);
        xpTextView.setText("+ " + xpToAdd + " xp");

        TextView levelTextView = findViewById(R.id.levelTextView);
        levelTextView.setText("Niveau: " + level.getLevel());

        TextView nextLevelXpTextView = findViewById(R.id.nextLevelXpTextView);
        nextLevelXpTextView.setText("Prochain niveau dans: " + level.getMissingXpToLevelUp() + " xp");
    }

    public void navigateTo(Class targetClass){
        Intent intent = new Intent(this, targetClass);
        startActivity(intent);
    }
}