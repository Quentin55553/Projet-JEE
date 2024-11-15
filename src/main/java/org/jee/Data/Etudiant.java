package org.jee.Data;


public class Etudiant extends Personne {
    public Etudiant(String nom, String prenom, String dateNaissance, String contact) {
        super(nom, prenom, dateNaissance, contact);
    }

    public void updatePrenom(String prenom) {
        this.setNom(prenom);
    }

    public void updateNom(String nom) {
        this.setNom(nom);
    }

    public void updateDateDeNaissance(String dateNaissance) {
        this.setDateNaissance(dateNaissance);
    }

    public void updateEmail(String email) {
        this.setMail(email);
    }

    @Override
    public void afficherDetails() {
        System.out.println("Étudiant");
        System.out.println("Prénom : " + this.getPrenom());
        System.out.println("Nom : " + this.getNom());
        System.out.println("Date de naissance : " + this.getDateNaissance());
        System.out.println("Email : " + this.getMail());
    }
}
