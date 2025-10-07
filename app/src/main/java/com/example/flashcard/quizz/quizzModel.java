package com.example.flashcard.quizz;

public class quizzModel {
    private String name;
    private String link;

    private Integer picture;



    // Constructeur
    public quizzModel(String name, String link,Integer picture ) {
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
