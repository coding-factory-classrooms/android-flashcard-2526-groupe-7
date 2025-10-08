package com.example.flashcard;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioGroup;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LevelSelector extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_level_selector);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Intent intent = new Intent(this, Game.class);

        Button easyLevel = findViewById(R.id.easyRadio);
        Button mediumLevel = findViewById(R.id.mediumRadio);
        Button hardLevel = findViewById(R.id.hardRadio);
        RadioGroup radioGroup =findViewById(R.id.radioGroup);
        Button fiveQuestionsRadio = findViewById(R.id.fiveQuestionsRadio);
        Button tenQuestionsRadio = findViewById(R.id.tenQuestionsRadio);
        Button fiveteenQuestionsRadio = findViewById(R.id.fiveteenQuestionsRadio);
        RadioGroup radioGroupQuestions =findViewById(R.id.radioGroupNumber);
        Button play= findViewById(R.id.playButton);
        int selectId = radioGroup.getCheckedRadioButtonId();

        //User can choose the difficulty
        String level = "";
        if (selectId == R.id.easyRadio) {
            level = "easy";
        } else if (selectId == R.id.mediumRadio) {
            level = "medium";
        } else if (selectId == R.id.hardRadio) {
            level = "hard";
        }

        //User can chose the number of questions
        String questions = "";
        if (selectId == R.id.fiveQuestionsRadio) {
            questions = "five";
        } else if (selectId == R.id.tenQuestionsRadio) {
            questions = "ten";
        } else if (selectId == R.id.fiveteenQuestionsRadio) {
            questions = "fiveteen";
        }

        playButton.setOnClickListener view -> {
            intent.putExtra("LEVEL_KEY", level);
            intent.putExtra("LEVEL_KEY", questions);
            startActivity(intent);
        }

    }
    }

