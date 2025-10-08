package com.example.flashcard;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.flashcard.question.questionModel;

import java.util.ArrayList;
import java.util.List;

public class Game extends AppCompatActivity {

    private List<questionModel> questions;
    private int currentIndex = 0;
    private int goodAnswer = 0;
    private TextView questionText;
    private TextView scoreText;
    private RadioGroup optionsGroup;
    private RadioButton opt1, opt2, opt3;
    private Button validateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //Setup all components
        questionText = findViewById(R.id.questionTextTextView);
        scoreText = findViewById(R.id.scoreTextView);
        optionsGroup = findViewById(R.id.optionsRadioGroup);
        opt1 = findViewById(R.id.opt1RadioButton);
        opt2 = findViewById(R.id.opt2RadioButton);
        opt3 = findViewById(R.id.opt3RadioButton);
        validateButton = findViewById(R.id.validateButton);


        //Create arrayList -> question Model
        questions = new ArrayList<>();
        questions.add(new questionModel("Capitale de la France ?", new String[]{"Paris", "Rome", "Madrid"}, 0,"http","http",20,"song"));
        questions.add(new questionModel("Capitale de l'Allemagne ?", new String[]{"Berlin", "Munich", "Hambourg"}, 0,"http","http",20,"song"));
        questions.add(new questionModel("Capitale de l'Espagne ?", new String[]{"Madrid", "Barcelone", "Valence"}, 0,"http","http",20,"song"));

        // Setup score to 0/question number
        scoreText.setText(goodAnswer + "/" + questions.size());

        //Show first question
        showQuestion();

        // Logic for validate button
        validateButton.setOnClickListener(v -> {
            int checkedId = optionsGroup.getCheckedRadioButtonId();
            int selectedIndex = -1;

            //Logic for find what answer as checked
            if (checkedId == R.id.opt1RadioButton) {
                selectedIndex = 0;
            }
            else if (checkedId == R.id.opt2RadioButton){
                selectedIndex = 1;
            }
            else if (checkedId == R.id.opt3RadioButton){
                selectedIndex = 2;
            }

            // If logic bug go retrun no crash
            if (selectedIndex == -1){

                return;
            }

            // Logic for win
            if (selectedIndex == questions.get(currentIndex).answerCorrectIndex) {
                //Increment score
                goodAnswer++;
                //Draw new score
                scoreText.setText(goodAnswer + "/" + questions.size());
                Advance();
            }
            else{
                showCorrectAnswerPopup(questions.get(currentIndex).answerOptions[questions.get(currentIndex).answerCorrectIndex], new Runnable() {
                    @Override
                    public void run() {
                        // This code runs AFTER the popup is dismissed
                        Advance();
                    }
                });
            }


            // Next question if not finish

        });
    }
    public void showCorrectAnswerPopup(String correctAnswer, Runnable afterDismiss) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_correct_answer, null);

        PopupWindow popupWindow = new PopupWindow(
                popupView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                true
        );

        TextView answerTextView = popupView.findViewById(R.id.textViewAnswer);
        Button closeButton = popupView.findViewById(R.id.buttonClose);

        answerTextView.setText(correctAnswer);

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                if (afterDismiss != null) {
                    afterDismiss.run(); // this is where your "next" logic goes
                }
            }
        });

        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
    }
    private void Advance() {
        currentIndex++;
        if (currentIndex < questions.size()) {
            showQuestion();
        } else {
            //Logic for finish
            questionText.setText("Quiz terminé !");
            optionsGroup.clearCheck();
            opt1.setEnabled(false);
            opt2.setEnabled(false);
            opt3.setEnabled(false);
            validateButton.setEnabled(false);
        }
    }

    private void showQuestion() {
        questionModel question = questions.get(currentIndex);
        questionText.setText(question.questionTitle);
        opt1.setText(question.answerOptions[0]);
        opt2.setText(question.answerOptions[1]);
        opt3.setText(question.answerOptions[2]);
        optionsGroup.clearCheck();
    }
}
