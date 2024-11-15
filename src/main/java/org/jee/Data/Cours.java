package org.jee.Data;


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
