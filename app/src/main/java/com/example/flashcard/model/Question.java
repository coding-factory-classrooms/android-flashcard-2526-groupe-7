package com.example.flashcard.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Question implements Parcelable {
    public String questionTitle;
    public AnswerOption[] answerOptions;
    public int answerCorrectIndex;
    public String questionImage;
    public String questionSong;
    public String difficult;
    public int questionTime;

    public Question(String questionText, AnswerOption[] options, int correctIndex, String questionImage, String difficult, int questionTime, String questionSong) {
        this.questionTitle = questionText;
        this.answerOptions = options;
        this.answerCorrectIndex = correctIndex;
        this.questionImage = questionImage;
        this.questionSong = questionSong;
        this.difficult = difficult;
        this.questionTime = questionTime;
    }

    protected Question(Parcel in) {
        questionTitle = in.readString();
        answerOptions = in.createTypedArray(AnswerOption.CREATOR);
        answerCorrectIndex = in.readInt();
        questionImage = in.readString();
        questionSong = in.readString();
        difficult = in.readString();
        questionTime = in.readInt();
    }

    public int getAnswerCorrectIndex() { return answerCorrectIndex; }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int flags) {
        parcel.writeString(questionTitle);
        parcel.writeTypedArray(answerOptions, flags);
        parcel.writeInt(answerCorrectIndex);
        parcel.writeString(questionImage);
        parcel.writeString(questionSong);
        parcel.writeString(difficult);
        parcel.writeInt(questionTime);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };
}
