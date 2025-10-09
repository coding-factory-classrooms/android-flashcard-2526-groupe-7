package com.example.flashcard.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class AnswerOption implements Parcelable {

    public int id;
    public String reponse;

    public AnswerOption(int id, String reponse) {
        this.id = id;
        this.reponse = reponse;
    }

    protected AnswerOption(Parcel in) {
        id = in.readInt();
        reponse = in.readString();
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int flags) {
        parcel.writeInt(id);
        parcel.writeString(reponse);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AnswerOption> CREATOR = new Creator<AnswerOption>() {
        @Override
        public AnswerOption createFromParcel(Parcel in) {
            return new AnswerOption(in);
        }

        @Override
        public AnswerOption[] newArray(int size) {
            return new AnswerOption[size];
        }
    };
}
