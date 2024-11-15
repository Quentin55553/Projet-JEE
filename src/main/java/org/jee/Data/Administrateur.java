package org.jee.Data;


public class Administrateur extends Personne {
    public Administrateur(String nom, String prenom, String dateNaissance, String contact) {
        super(nom, prenom, dateNaissance, contact);
    }


    @Override
    public String afficherDetails() {
        return "ADMINISTRATEUR\n" + super.afficherDetails();
    }
}
