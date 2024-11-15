package org.jee.Data;


public class Administrateur extends Personne {
    public Administrateur(String nom, String prenom, String dateNaissance, String contact) {
        super(nom, prenom, dateNaissance, contact);
    }


    @Override
    public void afficherDetails() {
        System.out.println("Administrateur");
        System.out.println("Prénom : " + this.getPrenom());
        System.out.println("Nom : " + this.getNom());
        System.out.println("Date de naissance : " + this.getDateNaissance());
        System.out.println("Email : " + this.getMail());
    }
}
