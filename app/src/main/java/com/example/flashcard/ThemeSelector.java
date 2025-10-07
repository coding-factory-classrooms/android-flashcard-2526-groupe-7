package com.example.flashcard;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flashcard.quizz.quizzAdapter;
import com.example.flashcard.quizz.quizzModel;

import java.util.ArrayList;
import java.util.List;

public class ThemeSelector extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme_selector);

        List<quizzModel> listeDesPays = new ArrayList<>();
        listeDesPays.add(new quizzModel("Test", "Https",R.drawable.ic_launcher_background));
        listeDesPays.add(new quizzModel("Test", "Https",R.drawable.ic_launcher_background));
        listeDesPays.add(new quizzModel("Test", "Https",R.drawable.ic_launcher_background));
        listeDesPays.add(new quizzModel("Test", "Https",R.drawable.ic_launcher_background));
        listeDesPays.add(new quizzModel("Test", "Https",R.drawable.ic_launcher_background));
        listeDesPays.add(new quizzModel("Test", "Https",R.drawable.ic_launcher_background));
        listeDesPays.add(new quizzModel("Test", "Https",R.drawable.ic_launcher_background));
        listeDesPays.add(new quizzModel("Test", "Https",R.drawable.ic_launcher_background));
        listeDesPays.add(new quizzModel("Test", "Https",R.drawable.ic_launcher_background));
        listeDesPays.add(new quizzModel("Test", "Https",R.drawable.ic_launcher_background));
        listeDesPays.add(new quizzModel("Test", "Https",R.drawable.ic_launcher_background));





        RecyclerView recyclerView = findViewById(R.id.themeRecyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        quizzAdapter adapter = new quizzAdapter(listeDesPays);
        recyclerView.setAdapter(adapter);
    }
}