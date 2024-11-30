package com.example.demo.repository;

import com.example.demo.entity.Cours;
import com.example.demo.entity.Inscription;
import com.example.demo.entity.Personne;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InscriptionRepository extends JpaRepository<Inscription, Integer> {

    // Rechercher toutes les inscriptions d'un étudiant
    List<Inscription> findByPersonneByIdEtudiantIdPersonne(String idPersonne);

    // Rechercher toutes les inscriptions pour un cours
    List<Inscription> findByCoursIdCours(int idCours);

    // Rechercher toutes les inscriptions par état
    List<Inscription> findByEtat(int etat);
    // Trouver une inscription par étudiant et cours
    Inscription findByPersonneByIdEtudiantAndCours(Personne personne, Cours cours);
    List<Inscription> findByEtatAndCours(int etat, Cours cours);

}
