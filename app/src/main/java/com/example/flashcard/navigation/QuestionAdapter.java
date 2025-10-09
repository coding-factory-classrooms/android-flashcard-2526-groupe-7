package com.example.flashcard;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder> {

    private List<Question> questionList;

    // Constructor: initializes the adapter with the list of questions
    public QuestionAdapter(List<Question> questionList) {
        this.questionList = questionList;
    }

    @NonNull
    @Override
    // Method called to create a new ViewHolder when the RecyclerView needs a new view
    public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_all_question_items, parent, false);
        return new QuestionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionViewHolder holder, int position) {
        Question question = questionList.get(position);
        holder.textViewQuestion.setText(question.getQuestionText());
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
        public QuestionViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewQuestion = itemView.findViewById(R.id.textViewQuestion);

        }
    }
}