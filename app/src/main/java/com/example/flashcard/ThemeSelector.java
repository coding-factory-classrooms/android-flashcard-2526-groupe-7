package com.example.flashcard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flashcard.adapter.quizzAdapter;
import com.example.flashcard.model.QuizModel;
import com.example.flashcard.model.api.ApiQuiz;
import com.example.flashcard.model.callback.QuizCallback;

import java.util.List;

public class ThemeSelector extends AppCompatActivity implements quizzAdapter.OnItemClickListener  {

    List<QuizModel> quizModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme_selector);
        ImageButton arrowBack = findViewById(R.id.backButton);

        arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        fetchAllThemes(this);
    }

    // Add on click for theme
    public void onItemClick(String quizzName) {
        Log.d("quizzname :",quizzName);
        Intent intent = new Intent(this, LevelSelector.class);
        intent.putExtra("name",quizzName);
        startActivity(intent);
    }

    // Fetch all themes and display them
    public void fetchAllThemes(Context context){
        ApiQuiz apiQuiz = new ApiQuiz();
        apiQuiz.fetchAllApiQuiz(new QuizCallback() {
            @Override
            public void onSuccess(List<QuizModel> quizModels) {

                RecyclerView recyclerView = findViewById(R.id.themeRecyclerView);
                recyclerView.setLayoutManager(new LinearLayoutManager(context));

                quizzAdapter adapter = new quizzAdapter(quizModels, ThemeSelector.this::onItemClick);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onError(Throwable t) {

            }
        });
    }
}