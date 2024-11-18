package org.jee.entity;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.Collection;

@Entity
public class Inscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_inscription")
    private int idInscription;

    @Column(name = "date_inscription")
    private Date dateInscription;

    @Column(name = "etat")
    private int etat;

    @Column(name = "description_refus")
    private String descriptionRefus;

    @ManyToOne
    @JoinColumn(name = "id_etudiant", referencedColumnName = "id_personne")
    private Personne personneByIdEtudiant;

    @ManyToMany
    @JoinTable(
            name = "inscription_cours", // Nom de la table de jointure
            joinColumns = @JoinColumn(name = "id_inscription"), // Clé étrangère vers Inscription
            inverseJoinColumns = @JoinColumn(name = "id_cours") // Clé étrangère vers Cours
    )
    private Collection<Cours> cours;

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

    public Collection<Cours> getCours() {
        return cours;
    }

    public void setCours(Collection<Cours> cours) {
        this.cours = cours;
    }
}
