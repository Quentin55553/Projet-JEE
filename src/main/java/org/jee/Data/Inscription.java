package org.jee.Data;

import java.util.List;
import java.util.HashMap;
import java.util.Map;


public class Inscription {
    //consultation des cours auxquels un étudiant est inscrit //
    private Map<Etudiant, List<Cours>> inscription; //association de chaque étudiant à un liste de cours//


    public Inscription() {
        this.inscription = new HashMap<>();
    }

    public void inscrire(Etudiant etudiant, Cours cours) {

    }
}