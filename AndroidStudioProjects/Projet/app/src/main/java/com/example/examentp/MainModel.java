package com.example.examentp;

public class MainModel {
    String Nom,Image,Date,Cinema;


    MainModel()
    {

    }

    public MainModel(String nom, String image, String date, String cinema) {
        Nom = nom;
        Image = image;
        Date = date;
        Cinema = cinema;
    }

    public String getNom() {
        return Nom;
    }

    public void setNom(String nom) {
        Nom = nom;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getCinema() {
        return Cinema;
    }

    public void setCinema(String cinema) {
        Cinema = cinema;
    }
}
