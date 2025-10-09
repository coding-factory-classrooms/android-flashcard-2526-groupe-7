package com.example.flashcard.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flashcard.R;
import com.example.flashcard.model.QuizModel;

import java.util.List;

public class quizzAdapter extends RecyclerView.Adapter<quizzAdapter.QuizzViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(String quizzName);
    }

    private final List<QuizModel> quizModels;
    private final OnItemClickListener listener;

    private ImageView pictureImageView;


    public quizzAdapter(List<QuizModel> quizModels, OnItemClickListener listener) {
        this.quizModels = quizModels;
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
        QuizModel uniqueQuizModel = quizModels.get(position);
        String linkQuizz = uniqueQuizModel.getLink();
        // Set name quizz
        holder.nameQuizzTextView.setText(uniqueQuizModel.getName());

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
                listener.onItemClick(linkQuizz);
            }
        });
    }
    @Override
    public int getItemCount() {
        return quizModels.size();
    }

}
