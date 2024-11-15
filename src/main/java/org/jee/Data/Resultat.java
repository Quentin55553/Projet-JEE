package org.jee.Data;


public class Resultat {
    private Etudiant etudiant;
    private Cours cours;
    private float note;


    public Resultat(Etudiant etudiant, Cours cours, float note) {
        this.etudiant = etudiant;
        this.cours = cours;
        this.note = note;
    }

    public Etudiant getEtudiant() {
        return etudiant;
    }

    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
    }

    public Cours getCours() {
        return cours;
    }

    public void setCours(Cours cours) {
        this.cours = cours;
    }

    public float getNote() {
        return note;
    }

    public void setNote(float note) {
        this.note = note;
    }
}
