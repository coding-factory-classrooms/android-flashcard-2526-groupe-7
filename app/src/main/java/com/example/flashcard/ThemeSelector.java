package com.example.flashcard;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

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

import com.example.flashcard.model.Level;
import com.example.flashcard.model.json.JsonLevel;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.gson.Gson;

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