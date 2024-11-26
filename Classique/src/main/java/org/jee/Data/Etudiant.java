package org.jee.Data;


public class Etudiant extends Personne {
    public Etudiant(int id, String nom, String prenom, String dateNaissance, String contact, String mdp) {
        super(id, nom, prenom, dateNaissance, contact, mdp, 1);
    }


    @Override
    public String afficherDetails() {
        return "ÉTUDIANT\n" + super.afficherDetails();
    }
}
