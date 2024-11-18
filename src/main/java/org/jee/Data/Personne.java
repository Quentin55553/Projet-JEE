package org.jee.Data;


public abstract class Personne {
    private int id;
    private String nom;
    private String prenom;
    private String dateNaissance;
    private String mail;
    private String mdp;
    private int role; // 1 : Étudiant, 2 : Professeur, 3 : Administrateur


    public Personne(int id, String nom, String prenom, String dateNaissance, String contact, String mdp, int role) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.mail = contact;
        this.mdp = mdp; // VOIR POUR CHIFFREMENT
        this.role = role;
    }


    public String afficherDetails() {
        String resultat = "";
        resultat += "Prénom : " + this.prenom + "\n";
        resultat += "Nom : " + this.nom + "\n";
        resultat += "Date de naissance : " + this.dateNaissance + "\n";
        resultat += "Email : " + this.mail + "\n";
        return resultat;
    }


    // Getters et setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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


    public String getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(String dateNaissance) {
        this.dateNaissance = dateNaissance;
    }


    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }


    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }


    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}
