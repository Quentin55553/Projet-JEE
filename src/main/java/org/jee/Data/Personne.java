package org.jee.Data;


public abstract class Personne {
    private String nom;
    private String prenom;
    private String dateNaissance;
    private String contact;


    public Personne(String nom, String prenom, String dateNaissance, String contact) {
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.contact = contact;
    }

    public abstract void afficherDetails();
}
