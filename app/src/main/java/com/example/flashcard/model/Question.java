package com.example.flashcard.model;

public class Question{
    public String questionTitle;
    public AnswerOption[] answerOptions;
    public int answerCorrectIndex;
    public String questionImage;
    public String questionSong;
    public String difficult;
    int questionTime;

    public Question(String questionText, AnswerOption[] options, int correctIndex, String questionImage, String difficult, int questionTime, String questionSong) {
        this.questionTitle = questionText;
        this.answerOptions = options;
        this.answerCorrectIndex = correctIndex;
        this.questionImage = questionImage;
        this.questionSong = questionSong;
        this.difficult = difficult;
        this.questionTime = questionTime;

    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public AnswerOption[] getAnswerOptions() {
        return answerOptions;
    }

    public int getAnswerCorrectIndex() {
        return answerCorrectIndex;
    }

    public String getQuestionImage() {
        return questionImage;
    }

    public String getQuestionSong() {
        return questionSong;
    }

    public String getDifficult() {
        return difficult;
    }

    public int getQuestionTime() {
        return questionTime;
    }
}
