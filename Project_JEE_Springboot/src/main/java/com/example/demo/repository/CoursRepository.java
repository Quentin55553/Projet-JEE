package com.example.demo.repository;

import com.example.demo.entity.Cours;
import com.example.demo.entity.Personne;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface CoursRepository extends JpaRepository<Cours, Integer> {

    // Rechercher tous les cours d'un enseignant donné
    List<Cours> findByPersonneByIdEnseignantIdPersonne(String idPersonne);

    // Rechercher les cours par nom (exact ou contenant une chaîne)
    List<Cours> findByNomCoursContainingIgnoreCase(String nomCours);

    // Rechercher les cours par date de début ou après une date donnée
    List<Cours> findByDateDebutAfter(Date date);
    // Trouver les cours enseignés par un professeur
    List<Cours> findByPersonneByIdEnseignant(Personne enseignant);

    // Trouver un cours par nom
    Cours findByNomCours(String nomCours);
}
