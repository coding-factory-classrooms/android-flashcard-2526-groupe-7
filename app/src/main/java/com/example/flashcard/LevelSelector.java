package com.example.flashcard;
import static com.example.flashcard.MainActivity.TAG;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.flashcard.model.Question;
import com.example.flashcard.model.api.ApiQuestion;
import com.example.flashcard.model.callback.QuestionCallback;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LevelSelector extends AppCompatActivity {


    List<Question> questionsOfquiz = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_level_selector);
        Intent srcIntent = getIntent();
        String nameQuestionnary = srcIntent.getStringExtra("name");


        Button easyLevel = findViewById(R.id.easyRadio);
        Button mediumLevel = findViewById(R.id.mediumRadio);
        Button hardLevel = findViewById(R.id.hardRadio);
        Button hardcoreLevel = findViewById(R.id.hardcoreRadio);
        RadioGroup radioGroup = findViewById(R.id.radioGroupDifficulty);
        Button fiveQuestionsRadio = findViewById(R.id.fiveQuestionsRadio);
        Button tenQuestionsRadio = findViewById(R.id.tenQuestionsRadio);
        Button fiveteenQuestionsRadio = findViewById(R.id.fiveteenQuestionsRadio);
        Button timerRadio = findViewById(R.id.timerRadio);
        RadioGroup radioGroupTimer = findViewById(R.id.radioGroupTimer);
        RadioGroup radioGroupQuestions = findViewById(R.id.radioGroupNumber);
        Button play = findViewById(R.id.playButton);
        ImageButton arrowBack = findViewById(R.id.backButton);

        arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ApiQuestion apiQuestion = new ApiQuestion();

        Log.i("GAME", "APPEL API");

        apiQuestion.fetchApiQuestion(nameQuestionnary, new QuestionCallback() {
            @Override
            public void onSuccess(List<Question> all_questions) {
                questionsOfquiz = all_questions;
            }

            @Override
            public void onError(Throwable t) {
                Log.e("Game", String.valueOf(t));
            }
        });

        play.setOnClickListener (view -> {
            int selectId = radioGroup.getCheckedRadioButtonId();
            int selectedQuestionsId = radioGroupQuestions.getCheckedRadioButtonId();
            int selectedTimerId = radioGroupTimer.getCheckedRadioButtonId();

        //User can choose the difficulty
        String level = "";
        if (selectId == R.id.easyRadio) {
            level = "easy";
        } else if (selectId == R.id.mediumRadio) {
            level = "medium";
        } else if (selectId == R.id.hardRadio) {
            level = "hard";
        }else {
            level = "hardcore";
        }

        //User can chose the number of questions
        int questions = 0;
        if (selectedQuestionsId == R.id.fiveQuestionsRadio) {
            questions = 5;
        } else if (selectedQuestionsId == R.id.tenQuestionsRadio) {
            questions = 10;
        } else if (selectedQuestionsId == R.id.fiveteenQuestionsRadio) {
            questions = 15;
        }

        String timer = "";
        if (selectedTimerId == R.id.timerRadio){
            timer = "on";
            }

            Intent intent = new Intent(this, Game.class);
            intent.putExtra("difficult", level);
            intent.putExtra("nbQuestion", questions);
            intent.putExtra("name",nameQuestionnary);
            intent.putExtra("time",timer);
            intent.putExtra("questions", (Serializable) questionsOfquiz);

            startActivity(intent);
        });
    }
}


