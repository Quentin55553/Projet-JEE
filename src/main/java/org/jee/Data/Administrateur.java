package org.jee.Data;


public class Administrateur extends Personne {
    public Administrateur(int id, String nom, String prenom, String dateNaissance, String contact, String mdp) {
        super(id, nom, prenom, dateNaissance, contact, mdp, 3);
    }


    @Override
    public String afficherDetails() {
        return "ADMINISTRATEUR\n" + super.afficherDetails();
    }
}
