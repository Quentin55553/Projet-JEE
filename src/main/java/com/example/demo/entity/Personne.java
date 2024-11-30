package com.example.demo.entity;

import jakarta.persistence.*;
import java.sql.Date;
import java.util.Collection;

@Entity
@Table(name = "personne")
public class Personne {

    @Id
    @Column(name = "id_personne", nullable = false, unique = true)
    private String idPersonne;

    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "prenom", nullable = false)
    private String prenom;

    @Column(name = "date_naissance", nullable = false)
    private Date dateNaissance;

    @Column(name = "contact", nullable = false)
    private String contact;

    @Column(name = "role", nullable = false)
    private int role;

    @Column(name = "password", nullable = false)
    private String password;

    @OneToMany(mappedBy = "personneByIdEnseignant", cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<Cours> coursByIdPersonne;

    @OneToMany(mappedBy = "personneByIdEtudiant", cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<Inscription> inscriptionsByIdPersonne;

    @OneToMany(mappedBy = "personneByIdEtudiant", cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<Resultat> resultatsByIdPersonne;

    // Getters et setters
    public String getIdPersonne() {
        return idPersonne;
    }

    public void setIdPersonne(String idPersonne) {
        this.idPersonne = idPersonne;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Date getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Collection<Cours> getCoursByIdPersonne() {
        return coursByIdPersonne;
    }

    public void setCoursByIdPersonne(Collection<Cours> coursByIdPersonne) {
        this.coursByIdPersonne = coursByIdPersonne;
    }

    public Collection<Inscription> getInscriptionsByIdPersonne() {
        return inscriptionsByIdPersonne;
    }

    public void setInscriptionsByIdPersonne(Collection<Inscription> inscriptionsByIdPersonne) {
        this.inscriptionsByIdPersonne = inscriptionsByIdPersonne;
    }

    public Collection<Resultat> getResultatsByIdPersonne() {
        return resultatsByIdPersonne;
    }

    public void setResultatsByIdPersonne(Collection<Resultat> resultatsByIdPersonne) {
        this.resultatsByIdPersonne = resultatsByIdPersonne;}}