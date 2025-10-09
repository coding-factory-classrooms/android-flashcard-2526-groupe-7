package com.example.flashcard.model;

public class QuizModel {
    private String name;
    private String link;
    private String picture;

    // Constructeur
    public QuizModel(String name, String link, String picture ) {
        this.name = name;
        this.link = link;
        this.picture = picture;
    }

    // Getters (méthodes pour récupérer les données)
    public String getName() {
        return name;
    }

    public String getLink() {
        return link;
    }

    public String getPicture(){
        return picture;
    }
}
