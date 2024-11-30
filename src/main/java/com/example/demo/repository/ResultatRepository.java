package com.example.demo.repository;

import com.example.demo.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResultatRepository extends JpaRepository<Resultat, Integer> {

    // Rechercher tous les résultats pour un étudiant donné
    List<Resultat> findByPersonneByIdEtudiantIdPersonne(String idPersonne);

    // Rechercher tous les résultats pour un cours donné
    List<Resultat> findByCoursByIdCoursIdCours(int idCours);

    // Rechercher un résultat spécifique pour un étudiant dans un cours
    // Recherche des résultats d'un étudiant spécifique pour un cours
    List<Resultat> findByPersonneByIdEtudiantIdPersonneAndCoursByIdCoursIdCours(String idPersonne, int idCours);




}
