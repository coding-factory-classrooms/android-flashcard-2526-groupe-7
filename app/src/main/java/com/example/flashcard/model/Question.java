package com.example.flashcard.model;

public class Question {
    public String questionTitle;
    public String[] answerOptions;
    public int answerCorrectIndex;
    String questionImage;
    String questionSong;
    String difficult;
    int questionTime;

    public Question(String questionText, String[] options, int correctIndex, String questionImage, String difficult, int questionTime, String questionSong) {
        this.questionTitle = questionText;
        this.answerOptions = options;
        this.answerCorrectIndex = correctIndex;
        this.questionImage = questionImage;
        this.questionSong = questionSong;
        this.difficult = difficult;
        this.questionTime = questionTime;

    }
}
