package org.jee.Data;

import java.util.Objects;


public class Cours {
    private Professeur professeur;
    private String matiere;
    private String dateDebut;
    private String dateFin;


    public Cours(Professeur professeur, String matiere, String dateDebut, String dateFin) {
        this.professeur = professeur;
        this.matiere   = matiere;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

    @Override
    public String toString() {
        String resultat = "COURS\n";
        resultat += "Professeur : " + professeur + "\n";
        resultat += "Matière : " + matiere + "\n";
        resultat += "Date de début : " + dateDebut + "\n";
        resultat += "Date de fin : " + dateFin + "\n";
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
        return Objects.equals(professeur, cours.professeur) && Objects.equals(matiere, cours.matiere) && Objects.equals(dateDebut, cours.dateDebut) && Objects.equals(dateFin, cours.dateFin);
    }

    public Professeur getProfesseur() {
        return professeur;
    }

    public void setProfesseur(Professeur professeur) {
        this.professeur = professeur;
    }

    public String getMatiere() {
        return matiere;
    }

    public void setMatiere(String matiere) {
        this.matiere = matiere;
    }

    public String getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(String dateDebut) {
        this.dateDebut = dateDebut;
    }

    public String getDateFin() {
        return dateFin;
    }

    public void setDateFin(String dateFin) {
        this.dateFin = dateFin;
    }
}
