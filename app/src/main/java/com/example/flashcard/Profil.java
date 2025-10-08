package com.example.flashcard;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.flashcard.model.Level;
import com.example.flashcard.model.json.JsonLevel;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.gson.Gson;

public class Profil extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profil);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        JsonLevel jsonlevel = new JsonLevel();

        Level level = jsonlevel.readLevel(this);

        /*findViewById(R.id.addXpButton).setOnClickListener(v -> {
            level.addXp(10);
            TextView levelTextView = findViewById(R.id.levelTextView);
            Log.i("Theme selector", new Gson().toJson(level));
            levelTextView.setText(String.valueOf(level.getLevel()));
            level.updateJson(this, level);
        });*/

        findViewById(R.id.dialogUserInfoButton).setOnClickListener(v -> {
            BottomSheetDialog dialog = new BottomSheetDialog(this);
            dialog.setContentView(R.layout.dialog_user_info);

            TextView levelTextView = dialog.findViewById(R.id.levelTextView);
            levelTextView.setText("Niveau " +  String.valueOf(level.getLevel()));

            TextView xpTextView = dialog.findViewById(R.id.xpTextView);
            xpTextView.setText(level.getStringXpToFixed());

            TextView goalXpTextView = dialog.findViewById(R.id.goalXpTextView);
            goalXpTextView.setText(level.getStringGoalXpToFixed());

            LinearProgressIndicator progressBar = dialog.findViewById(R.id.progressBar);
            progressBar.setProgress(level.getProgress());

            dialog.show();
        });
    }
}