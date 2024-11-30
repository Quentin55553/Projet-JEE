package com.example.demo.service;

import com.example.demo.entity.Personne;
import com.example.demo.repository.PersonneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonneService {

    @Autowired
    private PersonneRepository personneRepository;

    public List<Personne> getPersonnesByFilter(int role, String id, String nom, String prenom, String contact) {
        // Les paramètres null ou vides doivent être remplacés par une chaîne vide
        return personneRepository.findByRoleAndIdPersonneContainingAndNomContainingAndPrenomContainingAndContactContaining(
                role,
                id != null ? id : "",
                nom != null ? nom : "",
                prenom != null ? prenom : "",
                contact != null ? contact : ""
        );
    }

    public List<Personne> getPersonnesByRole(int role) {
        return personneRepository.findByRole(role);
    }
    public Personne getPersonneById(String id) {
        return personneRepository.findById(id).orElse(null);
    }

    public void updatePersonne(Personne personne) {
        personneRepository.save(personne);
    }

    public void deletePersonne(Personne personne) {
        personneRepository.delete(personne);
    }
}

