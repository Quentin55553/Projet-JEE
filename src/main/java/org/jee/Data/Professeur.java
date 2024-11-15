package org.jee.Data;


public class Professeur extends Personne {
    public Professeur(String nom, String prenom, String dateNaissance, String contact) {
        super(nom, prenom, dateNaissance, contact);
    }


    @Override
    public String afficherDetails() {
        return "PROFESSEUR\n" + super.afficherDetails();
    }
}
