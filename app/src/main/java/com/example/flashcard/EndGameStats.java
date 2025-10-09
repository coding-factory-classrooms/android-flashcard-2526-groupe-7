package com.example.flashcard;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.flashcard.model.AnswerOption;
import com.example.flashcard.model.Level;
import com.example.flashcard.model.Question;
import com.example.flashcard.model.Quizz;
import com.example.flashcard.model.json.JsonLevel;

import org.w3c.dom.Text;

import java.util.List;

public class EndGameStats extends AppCompatActivity {
    List<Question> errorQuestionsList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_end_game_stats);

        // Get buttons
        findViewById(R.id.homeButton).setOnClickListener(v -> navigateTo(MainActivity.class));
        findViewById(R.id.retryErrorButton).setOnClickListener(v -> navigateTo(Game.class));

        int numberOfQuestions;
        int score;
        String difficulty;
        int xpToAdd;

        Intent srcIntent = getIntent();
        numberOfQuestions = srcIntent.getIntExtra("nbQuestion",0);
        score = srcIntent.getIntExtra("score",0);
        difficulty = srcIntent.getStringExtra("difficult");
        Object questionObject  = srcIntent.getSerializableExtra("errorQuestion");

        if (questionObject instanceof List<?>) {
            errorQuestionsList = (List<Question>) questionObject;
        }else{
            Log.e("Error","Error during error question list recuperation");
        }

        if(difficulty.equals("easy")){
            xpToAdd = 20;
        } else if (difficulty.equals("medium")) {
            xpToAdd = 40;
        } else if (difficulty.equals("hard")) {
            xpToAdd = 60;
        }else {
            xpToAdd = 0;
        }


        // Replace score text
        TextView scoreTextView = findViewById(R.id.scoreTextView);
        scoreTextView.setText(score + "/" + numberOfQuestions);

        // Replace difficulty text
        TextView difficultyTextView = findViewById(R.id.difficultyTextView);
        difficultyTextView.setText("Difficulté: " + difficulty);

        // Replace percentage winning text
        TextView percentageTextView = findViewById(R.id.percentageTextView);
        int successRate = (score * 100) / numberOfQuestions;
        percentageTextView.setText("Taux de réussite: " + successRate + "%");

        // New instance of json level
        JsonLevel jsonLevel = new JsonLevel();

        // fetch data from local level.json
        Level level = jsonLevel.readLevel(this);

        // add xp to level class and write in json
        level.addXp(xpToAdd);
        level.updateJson(this, level);

        // Replace xp gained
        TextView xpTextView = findViewById(R.id.xpTextView);
        xpTextView.setText("+ " + xpToAdd + " xp");

        // Replace user level
        TextView levelTextView = findViewById(R.id.levelTextView);
        levelTextView.setText("Niveau: " + level.getLevel());

        // Replace missing xp to level up
        TextView nextLevelXpTextView = findViewById(R.id.nextLevelXpTextView);
        nextLevelXpTextView.setText("Prochain niveau dans: " + level.getMissingXpToLevelUp() + " xp");


        String formatedShareMessage = "J'ai eu " + score + "/" + numberOfQuestions + " au quiz " + difficulty + " sur l'app Mama";
        findViewById(R.id.shareButton).setOnClickListener(v -> {
            shareGame(formatedShareMessage);
        });


    }

    /**
     * function to navigate to activity
     * @param targetClass
     */
    public void navigateTo(Class targetClass){
        Intent intent = new Intent(this, targetClass);
        startActivity(intent);
    }

    public void shareGame(String quizInfo){
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, quizInfo);
        sendIntent.setType("text/plain");

        Intent shareIntent = Intent.createChooser(sendIntent, null);
        startActivity(shareIntent);

    }
}