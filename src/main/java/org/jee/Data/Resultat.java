package org.jee.Data;


public class Resultat {
    private int id;
    private int idEtudiant;
    private int idCours;
    private float note;


    public Resultat(int id, int idEtudiant, int idCours, float note) {
        this.id = id;
        this.idEtudiant = idEtudiant;
        this.idCours = idCours;
        this.note = note;
    }


    // Getters et setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public int getIdEtudiant() {
        return idEtudiant;
    }

    public void setIdEtudiant(int idEtudiant) {
        this.idEtudiant = idEtudiant;
    }


    public int getIdCours() {
        return idCours;
    }

    public void setIdCours(int idCours) {
        this.idCours = idCours;
    }


    public float getNote() {
        return note;
    }

    public void setNote(float note) {
        this.note = note;
    }
}
