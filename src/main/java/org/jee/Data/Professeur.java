package org.jee.Data;


public class Professeur extends Personne {
    public Professeur(int id, String nom, String prenom, String dateNaissance, String contact, String mdp) {
        super(id, nom, prenom, dateNaissance, contact, mdp, 2);
    }


    @Override
    public String afficherDetails() {
        return "PROFESSEUR\n" + super.afficherDetails();
    }
}
