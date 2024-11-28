package org.jee.entity;

import jakarta.persistence.*;
import java.sql.Date;
import java.util.List;
import java.util.Objects;


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

    // Relation OneToMany avec Inscription
    @OneToMany(mappedBy = "cours", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Inscription> inscriptions;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Cours cours = (Cours) o;

        return idCours == cours.idCours;
    }


    @Override
    public int hashCode() {
        return Objects.hash(idCours);
    }


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


    public List<Inscription> getInscriptions() {
        return inscriptions;
    }

    public void setInscriptions(List<Inscription> inscriptions) {
        this.inscriptions = inscriptions;
    }
}