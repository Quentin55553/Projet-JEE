package org.jee.Data;

import java.util.Objects;


public class Cours {
    private int id;
    private int idProfesseur;
    private String nom;
    private String description;


    public Cours(int id, int idProfesseur, String nom, String description) {
        this.id = id;
        this.idProfesseur = idProfesseur;
        this.nom = nom;
        this.description = description;
    }


    @Override
    public String toString() {
        String resultat = "COURS\n";
        resultat += "Professeur : " + idProfesseur + "\n";
        resultat += "Matière : " + nom + "\n";
        return resultat;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Cours cours = (Cours) obj;
        return Objects.equals(idProfesseur, cours.idProfesseur) && Objects.equals(nom, cours.nom);
    }


    // Getters et setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public int getIdProfesseur() {
        return idProfesseur;
    }

    public void setIdProfesseur(int idProfesseur) {
        this.idProfesseur = idProfesseur;
    }


    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
