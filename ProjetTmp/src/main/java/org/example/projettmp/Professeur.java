package org.example.projettmp;


public class Professeur extends Personne {
    public Professeur(String nom, String prenom, String dateNaissance, String contact) {
        super(nom, prenom, dateNaissance, contact);
    }


    @Override
    public void afficherDetails() {
        // A redéfinir
    }
}
