package org.jee.Data;


public abstract class Personne {
    private String mail;
    private String password;
    private String nom;
    private String prenom;
    private String dateNaissance;
    private int role; // 1-étudiant, 2-Proffeseur, 3-admin


    public Personne(String nom, String prenom, String dateNaissance, String contact) {
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
    }


    public String afficherDetails() {
        String resultat = "";
        resultat += "Prénom : " + this.prenom + "\n";
        resultat += "Nom : " + this.nom + "\n";
        resultat += "Date de naissance : " + this.dateNaissance + "\n";
        resultat += "Email : " + this.mail + "\n";
        return resultat;
    };

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(String dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}
