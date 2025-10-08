package com.example.flashcard.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flashcard.R;
import com.example.flashcard.model.Quizz;

import java.util.List;

public class quizzAdapter extends RecyclerView.Adapter<quizzAdapter.listView> {

    private final List<Quizz> quizz;


    public quizzAdapter(List<Quizz> quizz) {
        this.quizz = quizz;
    }

    public static class listView extends RecyclerView.ViewHolder{
        public TextView nameTextView;
        public ImageView pictureImageView;
        public listView(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameQuizzTextView);
            pictureImageView = itemView.findViewById(R.id.pictureQuizzImageView);
        }
    }

    @NonNull
    @Override
    public listView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.quizz_item, parent, false);

        return new listView(view);
    }
    @Override
    public void onBindViewHolder(@NonNull listView holder, int position) {
        Quizz uniqueQuizz = quizz.get(position);

        holder.nameTextView.setText(uniqueQuizz.getName());
        holder.pictureImageView.setImageResource(uniqueQuizz.getPicture());
    }

    @Override
    public int getItemCount() {
        return quizz.size();
    }
}
