package com.example.flashcard.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flashcard.R;
import com.example.flashcard.model.DailyChallengeApi;

import java.util.List;

public class DailyChallengeAdapter extends RecyclerView.Adapter<DailyChallengeAdapter.ViewHolder>{
    private List<DailyChallengeApi> dailyChallenges;

    public DailyChallengeAdapter(List<DailyChallengeApi> dailyChallenges) {
        this.dailyChallenges = dailyChallenges;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView challengeNameTextView;
        private final TextView numberOfQuestionsTextView;
        private final TextView difficultyTextView;
        public ViewHolder(View view) {
            super(view);
            challengeNameTextView = view.findViewById(R.id.challengeNameTextView);
            numberOfQuestionsTextView = view.findViewById(R.id.numberOfQuestionsTextView);
            difficultyTextView = view.findViewById(R.id.difficultyTextView);

        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.daily_challenge_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DailyChallengeApi dailyChallenge = dailyChallenges.get(position);
        holder.challengeNameTextView.setText("Défi du jour: " + dailyChallenge.getDailyChallenge().getChallengeName());
        holder.numberOfQuestionsTextView.setText(dailyChallenge.getDailyChallenge().getNumberOfQuestions() + " questions");
        holder.difficultyTextView.setText("• " + dailyChallenge.getDailyChallenge().getDifficulty());

    }

    @Override
    public int getItemCount() {
        return dailyChallenges.size();
    }

}
