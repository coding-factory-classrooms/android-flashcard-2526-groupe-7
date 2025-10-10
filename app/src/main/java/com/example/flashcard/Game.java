package com.example.flashcard;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
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

import com.example.flashcard.model.AnswerOption;
import com.example.flashcard.model.Question;
import com.google.gson.Gson;

import java.io.Serializable;
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
    int nbQuestion;
    String difficultQuestionnary;
    private List<AnswerOption>currentShuffledOptions;
    private String correctResponse;
    private int correctOptionId;
    private boolean replay;
    private boolean isDailyChallenge;
    private int quizIndex;
    private boolean oneQuestion;
    private String timer;
    private int remainingTime = 0;
    private TextView timeTextView;
    private Handler timerHandler = new Handler();
    private Runnable timerRunnable;
    private boolean timerRunning = false;
    private int maxOption;

    private int timerDuration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_game);
        //Setup all components
        questionText = findViewById(R.id.questionTextTextView);
        scoreText = findViewById(R.id.scoreTextView);
        optionsGroup = findViewById(R.id.optionsRadioGroup);
        validateButton = findViewById(R.id.validateButton);
        leaveButton = findViewById(R.id.backButton);
        timeTextView = findViewById(R.id.timeTextView);
        this.ErrorQuestions = new ArrayList<>();
        Intent srcIntent = getIntent();

        timer = srcIntent.getStringExtra("timer");




        //optionsGroup.setOnCheckedChangeListener(listener);


        String nameQuestionnary = srcIntent.getStringExtra("name");
        difficultQuestionnary = srcIntent.getStringExtra("difficult");
        nbQuestion = srcIntent.getIntExtra("nbQuestion",0);
        replay = srcIntent.getBooleanExtra("replay",false);
        isDailyChallenge = srcIntent.getBooleanExtra("isDailyChallenge", false);
        quizIndex = srcIntent.getIntExtra("quizIndex", 0);
        Object questionsFromQuiz = srcIntent.getSerializableExtra("questions");

        oneQuestion = srcIntent.getBooleanExtra("oneQuestionBool",false);
        timer = srcIntent.getStringExtra("time");

        if ("easy".equals(difficultQuestionnary)){
            maxOption = 3;
        } else if ("medium".equals(difficultQuestionnary)) {
            maxOption = 4;
        } else if ("hard".equals(difficultQuestionnary)) {
            maxOption = 5;
        } else if ("hardcore".equals(difficultQuestionnary)){
            maxOption = 3;
        }else {
            maxOption =0;
        }
        if ("easy".equals(difficultQuestionnary)){
            timerDuration = 15;
        } else if ("medium".equals(difficultQuestionnary)) {
            timerDuration = 10;
        } else if ("hard".equals(difficultQuestionnary)) {
            timerDuration = 7;
        } else if ("hardcore".equals(difficultQuestionnary)){
            timerDuration = 5;
        }else {
            timerDuration =10;
        }

        startTimer(timerDuration);

        //Logic for leaving button
        leaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("finish","Leave button");
                finish();
            }
        });
        redOverlay = findViewById(R.id.red_overlay);

        if(replay){
            Object questionObject  = srcIntent.getSerializableExtra("replayQuestion");
            if (questionObject instanceof List<?>) {
                questions = (List<Question>) questionObject;
            }else{
                Log.e("Error","Error during error question list recuperation");
            }
        } else if (oneQuestion) {
            Object questionObject  = srcIntent.getSerializableExtra("oneQuestion");
            if (questionObject instanceof List<?>) {
                questions = (List<Question>) questionObject;
                maxOption = 5;
            }
            else{
                Log.e("Error","Error during error question list recuperation");
            }
        } else{
            if(questionsFromQuiz instanceof List<?>){
                questions = (List<Question>) questionsFromQuiz;
                maxOption = 3;
            }else{
                Log.e("Error","Error during error question list recuperation");
            }
        }

        // Shuffle question for rng
        Collections.shuffle(questions);

            //Take numberQuestion question only
            questions = new ArrayList<>(questions.subList(0,nbQuestion));


        //Show first question
        showQuestion();

        // Logic for validate button
        validateButton.setOnClickListener(v -> {
            stopTimer();
            int checkedId = optionsGroup.getCheckedRadioButtonId();
            int selectedIndex = -1;
            int selectedOptionId = -1;

            if (checkedId == -1){
                return;
            }

            RadioButton checkedRadioButton = findViewById(checkedId);
            int selected = optionsGroup.indexOfChild(checkedRadioButton);

            if (selected != -1) {
                selectedOptionId = currentShuffledOptions.get(selected).id;
            } else {
                return;
            }
            // Logic for win
            if (selectedOptionId == correctOptionId) {
                //Increment score
                goodAnswer++;
                //Draw new score
                showGoodAnswerPopup();
                Advance();

            }
            else{
                for (int i = 0; i < optionsGroup.getChildCount(); i++) {
                    View child = optionsGroup.getChildAt(i);
                    if (child instanceof RadioButton) {
                        child.setEnabled(false);
                    }
                }
                validateButton.setEnabled(false);
                // Stock question in ErrorQuestion[]
                ErrorQuestions.add(questions.get(currentIndex));
                // Show error PopUp
                showWrongAnswerPopup(correctResponse, new Runnable() {
                    @Override
                    public void run() {
                        Advance();
                    }
                });
            }
        });
        ImageButton imageView = findViewById(R.id.imageButton);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("finish","Leave button");
                showImgPopup();
            }
        });
    }

    private  TextView timerText;
    private boolean pulsing = false;
    private View  redOverlay;
    private ObjectAnimator pulseAnimator ;

    private void startPulsingRedOverlay() {
        redOverlay.setVisibility(View.VISIBLE);
        pulseAnimator = ObjectAnimator.ofFloat(redOverlay, "alpha", 0f, 0.5f, 0f) ;
        pulseAnimator.setDuration(1000);
        pulseAnimator.setRepeatCount(ValueAnimator.INFINITE);
        pulseAnimator.setRepeatMode(ValueAnimator.RESTART);
        pulseAnimator.start();
    }

    private void stopPulsingRedOverlay() {
        pulseAnimator.cancel();
        pulseAnimator = null;
        redOverlay.setAlpha(0f);
    }

    //Function to start the timer (15 seconds here)
    private void startTimer(int seconds) {
        remainingTime = seconds;
        timerRunning = true;
        updateTimerDisplay();

        timerRunnable = new Runnable() {
            @Override
            public void run() {
                remainingTime--;
                updateTimerDisplay();
                if (remainingTime ==4 ){
                    timerHandler.postDelayed(this, 1000);
                    startPulsingRedOverlay();
                }else if (remainingTime > -1) {
                    timerHandler.postDelayed(this, 1000);
                } else {
                    timerRunning = false;
                    validateButton.setEnabled(false);
                    new Handler().postDelayed(() -> Advance(), 1000);
                    showTempsEcoulePopup();
                    stopPulsingRedOverlay();

                }
            }
        };
            timerHandler.postDelayed(timerRunnable, 1000);
    }

    private void stopTimer() {
        timerRunning = false;
        timerHandler.removeCallbacks(timerRunnable);
    }
    private void updateTimerDisplay() {
        int minutes = remainingTime / 60;
        int seconds = remainingTime % 60;
        String timeFormatted = String.format("%d:%02d", minutes, seconds);
        timeTextView.setText(timeFormatted);
    }
    public void showTempsEcoulePopup() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade);

        View popupView = inflater.inflate(R.layout.popup_temps_ecoule, null);
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
        popupWindow.showAtLocation(popupView, Gravity.TOP, 0, 0);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                popupWindow.dismiss();
            }
        }, 1000);
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
        }, 1500);
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
        TextView answerTextView = popupView.findViewById(R.id.textViewActualCorrectAnswer);
        Button closeButton = popupView.findViewById(R.id.buttonCloseError);

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
            if ("on".equals(timer)) {
                stopTimer();
                startTimer(timerDuration);
            }

        } else {
            //Logic for finish
            questionText.setText("Quiz terminé !");
            optionsGroup.clearCheck();

            for (int i = 0; i < optionsGroup.getChildCount(); i++) {
                View child = optionsGroup.getChildAt(i);
                if (child instanceof RadioButton) {
                    child.setEnabled(false);
                }
            }
            validateButton.setEnabled(false);

            Intent intent = new Intent(this, EndGameStats.class);
            intent.putExtra("score",goodAnswer);
            intent.putExtra("nbQuestion",nbQuestion);
            intent.putExtra("difficult",difficultQuestionnary);
            intent.putExtra("isDailyChallenge", isDailyChallenge);
            intent.putExtra("errorQuestion", (Serializable) ErrorQuestions);
            Log.i("TAG", "Advance: " + new Gson().toJson(questions));
            intent.putExtra("quizIndex", quizIndex);
            startActivity(intent);
        }
    }
    public void showImgPopup() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View popupView = inflater.inflate(R.layout.popup_img, null);

        // Get screen dimensions
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenWidth = displayMetrics.widthPixels;
        int screenHeight = displayMetrics.heightPixels;

        // Set image size to 80% of screen width and height
        ImageView imgView = popupView.findViewById(R.id.popupimg);
        ViewGroup.LayoutParams layoutParams = imgView.getLayoutParams();
        layoutParams.width = (int) (screenWidth * 0.8);
        layoutParams.height = (int) (screenHeight * 0.8);
        imgView.setLayoutParams(layoutParams);

        // Set the image (from drawable resource name)
        int resID = getResources().getIdentifier("bggame", "drawable", getPackageName());
        if (resID != 0) {
            imgView.setImageResource(resID);
        }

        // Create full-screen popup
        final PopupWindow popupWindow = new PopupWindow(
                popupView,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT,
                true
        );

        // Dismiss when clicking outside (on the dim background)
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setTouchable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // Optional: dismiss popup when tapping anywhere
        popupView.setOnClickListener(v -> popupWindow.dismiss());

        // Show popup
        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
    }
    private void showQuestion() {
        scoreText.setText(currentIndex + "/" + questions.size());
        //Clear radio checked
        optionsGroup.clearCheck();
        Question question = questions.get(currentIndex);
        questionText.setText(question.questionTitle);
        correctOptionId = question.getAnswerCorrectIndex();

        for (AnswerOption option : question.answerOptions) {
            if (option.id == correctOptionId) {
                correctResponse = option.reponse;
                break;
            }
        }

        // Create list option response for shuffle
        List<AnswerOption>  optionList = Arrays.asList(question.answerOptions);
        //Shuffle option list for RNG
        Collections.shuffle(optionList);

        currentShuffledOptions = optionList;

        boolean shuffleText = "hardcore".equals(difficultQuestionnary);
        createRadioButtons(optionsGroup, currentShuffledOptions, shuffleText, correctOptionId,maxOption);

        validateButton.setEnabled(true);

        // Get context
        Context context = getApplicationContext();

        // Stock correct Answer id
//        correctOptionId = question.getAnswerCorrectIndex();

        // Logic for load image (@drawable)
        int resID = context.getResources().getIdentifier(question.questionImage, "drawable", context.getPackageName());
        if (resID != 0) {
            ImageButton imageView = findViewById(R.id.imageButton);
            imageView.setImageResource(resID);
        } else {
            Log.e("ImageLoad", "Image resource not found: " + question.questionImage);
        }

    }

    private void createRadioButtons(RadioGroup radioGroup,List<AnswerOption> initialOptionList,boolean shuffleText,int correctOptionId,int maxOptions) {

        List<AnswerOption> finalOptionList = new ArrayList<>();
        AnswerOption correctAnswer = null;

        for (AnswerOption option : initialOptionList) {
            if (option.id == correctOptionId) {
                correctAnswer = option;
                break;
            }
        }

        if (correctAnswer == null) {
            Log.e("Error Quizz", "Error good answer not find");
            return;
        }

        finalOptionList.add(correctAnswer);

        for (AnswerOption option : initialOptionList) {
            if (option.id != correctOptionId && finalOptionList.size() < maxOptions) {
                finalOptionList.add(option);
            }
        }

        Collections.shuffle(finalOptionList);
        radioGroup.removeAllViews();
        Context context = radioGroup.getContext();
        final float scale = context.getResources().getDisplayMetrics().density;

        for (int i = 0; i < finalOptionList.size(); i++) {
            RadioButton radioButton = new RadioButton(context);
            String optionText = finalOptionList.get(i).reponse;

            if (shuffleText) {
                StringBuilder shuffled = new StringBuilder();
                List<Character> characters = new ArrayList<>();
                for (char c : optionText.toCharArray()) {
                    characters.add(c);
                }
                Collections.shuffle(characters);
                for (char c : characters) {
                    shuffled.append(c);
                }
                optionText = shuffled.toString();
            }

            radioButton.setText(optionText);

            RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(
                    RadioGroup.LayoutParams.MATCH_PARENT,
                    RadioGroup.LayoutParams.WRAP_CONTENT
            );
            int marginBottomPx = (int) (12 * scale + 0.5f);
            params.bottomMargin = marginBottomPx;
            radioButton.setLayoutParams(params);
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
                radioButton.setId(View.generateViewId());
            } else {
                radioButton.setId(i + 1000);
            }
            radioButton.setBackgroundResource(R.drawable.radio_option_background);
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                radioButton.setButtonTintList(
                        ColorStateList.valueOf(Color.parseColor("#FF5722"))
                );
            }
            int paddingVerticalPx = (int) (14 * scale + 0.5f);
            int paddingStartPx = (int) (20 * scale + 0.5f);
            int paddingEndPx = (int) (16 * scale + 0.5f);
            radioButton.setPaddingRelative(paddingStartPx, paddingVerticalPx, paddingEndPx, paddingVerticalPx);
            radioButton.setTextColor(Color.BLACK);
            radioButton.setTextSize(17);
            radioGroup.addView(radioButton);
            radioButton.setEnabled(true);
        }
        currentShuffledOptions = finalOptionList;
    }
}
