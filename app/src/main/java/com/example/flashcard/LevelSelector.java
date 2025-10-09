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

public class LevelSelector extends AppCompatActivity {

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
        RadioGroup radioGroup = findViewById(R.id.radioGroup);
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

        String timer = "off";
        if (selectedTimerId == R.id.timerRadio){
            timer = "on";
            }

            Intent intent = new Intent(this, Game.class);
            intent.putExtra("difficult", level);
            intent.putExtra("nbQuestion", questions);
            intent.putExtra("name",nameQuestionnary);
            intent.putExtra("timer",timer);
            startActivity(intent);
        });
    }
}


