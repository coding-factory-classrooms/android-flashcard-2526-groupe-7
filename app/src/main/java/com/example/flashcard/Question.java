package com.example.flashcard;

public class Question {
    private String question;

    public Question(String questionText) {
        this.question = questionText;
    }

    public String getQuestionText() {
        return question;
    }
}