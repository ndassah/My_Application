package com.example.myapplication;

public class HelperClass {
    String password;
    String nom;
    String mail;
    String id;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    String prenom;


    public HelperClass(String nom, String prenom, String mail, String password,String id) {
        this.nom = nom;
        this.prenom = prenom;
        this.mail = mail;
        this.password = password;
        this.id = id;
    }


    public HelperClass() {
    }
}
