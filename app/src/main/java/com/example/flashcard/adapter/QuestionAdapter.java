package com.example.flashcard.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flashcard.model.Question;
import com.example.flashcard.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder> {
    static List<Question> question;

    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(List<Question> question,View view);
    }


    private List<Question> questionList;

    // Constructor: initializes the adapter with the list of questions
    public QuestionAdapter(List<Question> questionList,  OnItemClickListener listener) {
        this.listener = listener;
        this.questionList = questionList;

    }


    @NonNull
    @Override
    // Method called to create a new ViewHolder when the RecyclerView needs a new view
    public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_all_question_items, parent, false);
        return new QuestionViewHolder(view , listener , questionList);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionViewHolder holder, int position) {
        Question question = questionList.get(position);
        Log.i("TAG", "onBindViewHolder: " + new Gson().toJson(question));
        holder.textViewQuestion.setText(question.getQuestiontTitle());
    }

    //Return number of items in list
    @Override
    public int getItemCount() {
        return questionList.size();
    }

    //1 line of the list
    static class QuestionViewHolder extends RecyclerView.ViewHolder {
        TextView textViewQuestion;

        //constructor
        public QuestionViewHolder(@NonNull View itemView, final OnItemClickListener listener, final List<Question> questionList) {
            super(itemView);
            textViewQuestion = itemView.findViewById(R.id.textViewQuestion);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {

                            Question questionClicked = questionList.get(position);
                            List<Question> singleQuestionList = new ArrayList<>();
                            singleQuestionList.add(questionClicked);
                            listener.onItemClick(singleQuestionList,v);
                        }
                    }
                }
            });

        }
    }
}