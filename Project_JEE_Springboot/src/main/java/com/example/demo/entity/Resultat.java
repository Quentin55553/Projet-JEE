package com.example.demo.entity;

import jakarta.persistence.*;
@Entity
@Table(name = "resultat")
public class Resultat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_resultat", nullable = false, unique = true)
    private int idResultat;

    @Column(name = "note")
    private Double note;

    @ManyToOne
    @JoinColumn(name = "id_etudiant", referencedColumnName = "id_personne", nullable = false)
    private Personne personneByIdEtudiant;

    @ManyToOne
    @JoinColumn(name = "id_cours", referencedColumnName = "id_cours", nullable = false)
    private Cours coursByIdCours;

    // Getters et setters
    public int getIdResultat() {
        return idResultat;
    }

    public void setIdResultat(int idResultat) {
        this.idResultat = idResultat;
    }

    public Double getNote() {
        return note;
    }

    public void setNote(Double note) {
        this.note = note;
    }

    public Personne getPersonneByIdEtudiant() {
        return personneByIdEtudiant;
    }

    public void setPersonneByIdEtudiant(Personne personneByIdEtudiant) {
        this.personneByIdEtudiant = personneByIdEtudiant;
    }

    public Cours getCoursByIdCours() {
        return coursByIdCours;
    }

    public void setCoursByIdCours(Cours coursByIdCours) {
        this.coursByIdCours = coursByIdCours;
    }
}
