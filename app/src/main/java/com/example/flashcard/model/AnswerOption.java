package com.example.flashcard.model;

import java.io.Serializable;

public class AnswerOption implements Serializable {

    public int id;
    public String reponse;
    public AnswerOption(int id, String reponse) {
        this.id = id;
        this.reponse = reponse;
    }
}