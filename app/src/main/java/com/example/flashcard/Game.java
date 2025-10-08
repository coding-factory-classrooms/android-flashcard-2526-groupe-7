package com.example.flashcard;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewKt;

import com.example.flashcard.model.AnswerOption;
import com.example.flashcard.model.Quizz;
import com.example.flashcard.model.json.JsonQuestion;
import com.example.flashcard.model.json.JsonQuizz;
import com.example.flashcard.model.Question;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Game extends AppCompatActivity {
    List<Question> questions;
    private int currentIndex = 0;
    private int goodAnswer = 0;
    private TextView questionText;
    private TextView scoreText;
    private RadioGroup optionsGroup;
    private RadioButton opt1, opt2, opt3;
    private Button validateButton;
    private ImageButton leaveButton;
    List<Question> ErrorQuestions;
    int numberQuestion = 5;

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
        leaveButton = findViewById(R.id.backButton);
        this.ErrorQuestions = new ArrayList<>();

        leaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("finish","Leave button");
                finish();
            }
        });

        JsonQuestion jsonQuestion = new JsonQuestion();
        questions = jsonQuestion.readQuestion(this, "questions");

        // Shuffle question for rng
        Collections.shuffle(questions);

        //Take numberQuestion question only
        questions = new ArrayList<>(questions.subList(0, numberQuestion));

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
                showGoodAnswerPopup();
                Advance();

            }
            else{
                String correctAnswerText = questions.get(currentIndex).answerOptions[questions.get(currentIndex).answerCorrectIndex].reponse;
                opt1.setEnabled(false);
                opt2.setEnabled(false);
                opt3.setEnabled(false);
                ErrorQuestions.add(questions.get(currentIndex));
                showWrongAnswerPopup(questions.get(currentIndex).answerOptions[questions.get(currentIndex).answerCorrectIndex].reponse, new Runnable() {
                    @Override
                    public void run() {
                        // This code runs AFTER the popup is dismissed
                        Advance();
                    }
                });
            }
        });
    }

    public void showGoodAnswerPopup() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade);

        View popupView = inflater.inflate(R.layout.popup_good_answer, null);
        popupView.startAnimation(fadeIn);

        PopupWindow popupWindow = new PopupWindow(
                popupView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                true
        );
        popupWindow.setOutsideTouchable(false);
        popupWindow.setTouchable(false);
        popupWindow.setFocusable(false);
        popupWindow.showAtLocation(popupView, Gravity.TOP, 0, 175);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                popupWindow.dismiss();
            }
        }, 1000); // 1.5 seconds
    }

    public void showWrongAnswerPopup(String correctAnswer, Runnable afterDismiss) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View popupView = inflater.inflate(R.layout.popup_wrong_answer, null);

        PopupWindow popupWindow = new PopupWindow(
                popupView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                true
        );
        popupWindow.setOutsideTouchable(false);
        popupWindow.setFocusable(false);
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
        //Clear radio checked
        optionsGroup.clearCheck();

        Question question = questions.get(currentIndex);
        questionText.setText(question.questionTitle);

        // Create list option response for shuffle
        List<AnswerOption>  optionList = Arrays.asList(question.answerOptions);
        //Shuffle option list for RNG
        Collections.shuffle(optionList);
        //Set option in select
        opt1.setText(optionList.get(0).reponse);
        opt2.setText(optionList.get(1).reponse);
        opt3.setText(optionList.get(2).reponse);
        //Active select for play
        opt1.setEnabled(true);
        opt2.setEnabled(true);
        opt3.setEnabled(true);

    }
}
