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

    public void inscrire(Etudiant etudiant, Cours cours) {// inscription des étudiants à un cours//
        inscriptions.puIfAbsent(etudiant, new Arraylist<>()); // Initialise la liste si l'étudiant n'ets pas encore inscrit le putIfabsent est une méthode integré à class hashmap de java 
        List<Cours> coursInscrits = inscriptions.get(etudiant); // avoir accès à la liste des cours de l'étudiant 

        if (coursInscrits.contains(cours)) { // permet de vérifier si l'étudiant est déjà inscrit à ce cours// 
            System.out.println("L'étudiant " + etudiant.getNom() + " est déjà inscrit au cours " + cours.getMatiere());
        } else { // Ajoute le cours à la liste des inscriptions de l'étudiant//
            coursInscrits.add(cours);
            System.out.println("Inscription réussie pour " + etudiant.getNom() + " au cours " + cours.getMatiere());
        }
    }
      // 2. Consulter les cours auxquels un étudiant est inscrit
    public List<Cours> consulterCours(Etudiant etudiant) {
        return inscriptions.getOrDefault(etudiant, new ArrayList<>());
    }

    // 3. Afficher tous les cours inscrits pour chaque étudiant
    public void afficherInscriptions() {
        for (Map.Entry<Etudiant, List<Cours>> entry : inscriptions.entrySet()) {
            Etudiant etudiant = entry.getKey();
            List<Cours> cours = entry.getValue();

            System.out.println("Étudiant : " + etudiant.getNom() + " " + etudiant.getPrenom());
            if (cours.isEmpty()) {
                System.out.println("  Aucun cours inscrit.");
            } else {
                for (Cours c : cours) {
                    System.out.println("  - " + c.getMatiere());
                }
            }
        }
    }
}

    }
