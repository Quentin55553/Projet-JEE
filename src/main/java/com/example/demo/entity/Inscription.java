package com.example.demo.entity;

import jakarta.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "inscription")
public class Inscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_inscription", nullable = false, unique = true)
    private int idInscription;

    @Column(name = "date_inscription", nullable = false)
    private Date dateInscription;

    @Column(name = "etat", nullable = false)
    private int etat;

    @Column(name = "description_refus")
    private String descriptionRefus;

    @ManyToOne
    @JoinColumn(name = "id_etudiant", referencedColumnName = "id_personne", nullable = false)
    private Personne personneByIdEtudiant;

    @ManyToOne
    @JoinColumn(name = "id_cours", referencedColumnName = "id_cours", nullable = false)
    private Cours cours;

    // Getters et setters
    public int getIdInscription() {
        return idInscription;
    }

    public void setIdInscription(int idInscription) {
        this.idInscription = idInscription;
    }

    public Date getDateInscription() {
        return dateInscription;
    }

    public void setDateInscription(Date dateInscription) {
        this.dateInscription = dateInscription;
    }

    public int getEtat() {
        return etat;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }

    public String getDescriptionRefus() {
        return descriptionRefus;
    }

    public void setDescriptionRefus(String descriptionRefus) {
        this.descriptionRefus = descriptionRefus;
    }

    public Personne getPersonneByIdEtudiant() {
        return personneByIdEtudiant;
    }

    public void setPersonneByIdEtudiant(Personne personneByIdEtudiant) {
        this.personneByIdEtudiant = personneByIdEtudiant;
    }

    public Cours getCours() {
        return cours;
    }

    public void setCours(Cours cours) {
        this.cours = cours;
    }
}
