package org.jee.entity;

import jakarta.persistence.*;


@Entity
public class Resultat {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_resultat")
    private int idResultat;

    @Basic
    @Column(name = "note")
    private Double note;

    @ManyToOne
    @JoinColumn(name = "id_etudiant", referencedColumnName = "id_personne")
    private Personne personneByIdEtudiant;

    @ManyToOne
    @JoinColumn(name = "id_cours", referencedColumnName = "id_cours")
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
