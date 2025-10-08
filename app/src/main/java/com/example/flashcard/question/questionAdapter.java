package com.example.flashcard.question;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flashcard.R;
import com.example.flashcard.quizz.quizzAdapter; // Note: This import seems unused based on the code provided
import java.util.List;

public class questionAdapter extends RecyclerView.Adapter<questionAdapter.QuestionViewHolder>{

    private List<questionModel> questions;
    private int currentIndex = 0;
    private RecyclerView recyclerView;
    private int goodAnswer = 0;
    private TextView scoreText;
    private TextView questionTitle;

    public questionAdapter(List<questionModel> questions, RecyclerView recyclerView,TextView scoreText, TextView questionTitle) {
        this.questions = questions;
        this.recyclerView = recyclerView;
        this.scoreText = scoreText;
        this.questionTitle = questionTitle;
    }

    @NonNull
    @Override
    public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.question_item, parent, false);
        return new QuestionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionViewHolder holder, int position) {
        questionModel q = questions.get(position);
        holder.bind(q);
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    class QuestionViewHolder extends RecyclerView.ViewHolder {
        RadioGroup optionsGroup;
        RadioButton option1, option2, option3;
        Button validateButton;

        public QuestionViewHolder(View itemView) {
            super(itemView);
            optionsGroup = itemView.findViewById(R.id.optionsRadioGroup);
            option1 = itemView.findViewById(R.id.opt1RadioButton);
            option2 = itemView.findViewById(R.id.opt2RadioButton);
            option3 = itemView.findViewById(R.id.opt3RadioButton);
            validateButton = itemView.findViewById(R.id.validateButton);
        }

        void bind(questionModel question) {
            questionTitle.setText(question.questionTitle);
            option1.setText(question.answerOptions[0]);
            option2.setText(question.answerOptions[1]);
            option3.setText(question.answerOptions[2]);
            optionsGroup.clearCheck();

            validateButton.setOnClickListener(v -> {
                int checkedId = optionsGroup.getCheckedRadioButtonId();
                int selectedIndex = -1;

                if (checkedId == R.id.opt1RadioButton) {
                    selectedIndex = 0;
                } else if (checkedId == R.id.opt2RadioButton) {
                    selectedIndex = 1;
                } else if (checkedId == R.id.opt3RadioButton) {
                    selectedIndex = 2;
                }

                if (selectedIndex == -1){
                    return;
                }

                if (selectedIndex == question.answerCorrectIndex){
                    goodAnswer = goodAnswer+1;
                    scoreText.setText(String.valueOf(goodAnswer));
                    v.post(() -> System.out.println("Bonne réponse" ));
                } else{
                    v.post(() -> System.out.println("Mauvaise réponse"));
                }

                int next = getBindingAdapterPosition() + 1;
                if (next < questions.size()){
                    recyclerView.smoothScrollToPosition(next);
                }
            });
        }
    }
}