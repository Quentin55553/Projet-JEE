package org.jee.Data;


public class Etudiant extends Personne {
    public Etudiant(String nom, String prenom, String dateNaissance, String contact) {
        super(nom, prenom, dateNaissance, contact);
    }


    @Override
    public void afficherDetails() {
        // A redéfinir
    }
}
