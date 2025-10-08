package com.example.flashcard.model;

public class Quizz {
    private String name;
    private String link;
    private Integer picture;



    // Constructeur
    public Quizz(String name, String link, Integer picture ) {
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

    public Integer getPicture(){
        return picture;
    }
}
