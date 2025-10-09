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

        // New instance of json level
        JsonLevel jsonlevel = new JsonLevel();

        // fetch data level from level.json
        Level level = jsonlevel.readLevel(this);

        // Define on click to display user info
        findViewById(R.id.dialogUserInfoButton).setOnClickListener(v -> {
            // new instance of bottom sheet dialog
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

            // show the dialog
            dialog.show();
        });
    }
}