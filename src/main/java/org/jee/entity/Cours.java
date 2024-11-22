package org.jee.entity;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.Collection;

@Entity
public class Cours {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cours")
    private int idCours;

    @Column(name = "nom_cours")
    private String nomCours;

    @Column(name = "description")
    private String description;

    @Column(name = "date_debut")
    private Date dateDebut;

    @Column(name = "date_fin")
    private Date dateFin;

    @ManyToOne
    @JoinColumn(name = "id_enseignant", referencedColumnName = "id_personne")
    private Personne personneByIdEnseignant;

    @ManyToMany(mappedBy = "cours")
    private Collection<Inscription> inscriptions;

    // Getters et setters
    public int getIdCours() {
        return idCours;
    }

    public void setIdCours(int idCours) {
        this.idCours = idCours;
    }

    public String getNomCours() {
        return nomCours;
    }

    public void setNomCours(String nomCours) {
        this.nomCours = nomCours;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public Personne getPersonneByIdEnseignant() {
        return personneByIdEnseignant;
    }

    public void setPersonneByIdEnseignant(Personne personneByIdEnseignant) {
        this.personneByIdEnseignant = personneByIdEnseignant;
    }

    public Collection<Inscription> getInscriptions() {
        return inscriptions;
    }

    public void setInscriptions(Collection<Inscription> inscriptions) {
        this.inscriptions = inscriptions;
    }
}
