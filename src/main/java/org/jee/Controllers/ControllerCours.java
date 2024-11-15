package org.jee.Controllers;

import org.jee.Data.Cours;

import java.util.ArrayList;
import java.util.List;


public class ControllerCours {
    private List<Cours> listeCours;


    public void ajouterCours(Cours cours) {
        if (listeCours == null) {
            listeCours = new ArrayList<Cours>();
        }

        listeCours.add(cours);
    }


    public void supprimerCours(Cours cours) {
        if (listeCours != null) {
            // Sûrement la méthode equals() à redéfinir dans Cours
            listeCours.remove(cours);
        }
    }


    public void afficherCours() {
        if (listeCours != null) {
            for (Cours cours : listeCours) {
                System.out.println(cours);
            }
        }
    }
}
