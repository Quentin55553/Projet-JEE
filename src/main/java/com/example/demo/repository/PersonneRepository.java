package com.example.demo.repository;

import com.example.demo.entity.Personne;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonneRepository extends JpaRepository<Personne, String> {
    Personne findByIdPersonneAndPassword(String email, String password);



    List<Personne> findByRole(int role);


    List<Personne> findByRoleAndIdPersonneContainingAndNomContainingAndPrenomContainingAndContactContaining(
            int role, String id, String nom, String prenom, String contact);


}


