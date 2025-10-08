package com.example.flashcard.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flashcard.R;
import com.example.flashcard.model.Quizz;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class quizzAdapter extends RecyclerView.Adapter<quizzAdapter.QuizzViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(String quizzName);
    }

    private final List<Quizz> quizz;
    private final OnItemClickListener listener;

    private ImageView pictureImageView;


    public quizzAdapter(List<Quizz> quizz, OnItemClickListener listener) {
        this.quizz = quizz;
        this.listener = listener;
    }

    public static class QuizzViewHolder extends RecyclerView.ViewHolder {
        public ImageView pictureQuizzImageView;
        public TextView nameQuizzTextView;

        public QuizzViewHolder(@NonNull View itemView) {
            super(itemView);
            pictureQuizzImageView = itemView.findViewById(R.id.pictureQuizzImageView);
            nameQuizzTextView = itemView.findViewById(R.id.nameQuizzTextView);
        }
    }


    @NonNull
    @Override
    public QuizzViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.quizz_item, parent, false);
        return new QuizzViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuizzViewHolder holder, int position) {
        Quizz uniqueQuizz = quizz.get(position);
        String linkQuizz = uniqueQuizz.getLink();
        // Set name quizz
        holder.nameQuizzTextView.setText(uniqueQuizz.getName());

        Context context = holder.itemView.getContext();
        int id = context.getResources().getIdentifier("bgopen", "drawable", context.getPackageName());
        if(id != 0){
            holder.pictureQuizzImageView.setImageResource(id);
        }else {
            Log.d("Find quizz image", "Error");
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Notifie l'Activity du clic
                listener.onItemClick(linkQuizz);
            }
        });
    }
    @Override
    public int getItemCount() {
        return quizz.size();
    }

}
