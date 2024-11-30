package com.example.demo.controller;

import com.example.demo.entity.Cours;
import com.example.demo.entity.Inscription;
import com.example.demo.entity.Personne;
import com.example.demo.repository.CoursRepository;
import com.example.demo.repository.InscriptionRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class TraitementNotesController {

    @Autowired
    private CoursRepository coursRepository;

    @Autowired
    private InscriptionRepository inscriptionRepository;

    @PostMapping("/professeur/traiter-notes")
    public String getEtudiants(@RequestParam int idCours, HttpSession session, Model model) {
        Personne user = (Personne) session.getAttribute("user");

        if (user == null || user.getRole() != 2) {
            return "redirect:/login";
        }

        Cours cours = coursRepository.findById(idCours).orElse(null);

        if (cours == null) {
            return "redirect:/professeur/notes?error=cours_invalide";
        }

        // Utilise le repository pour trouver les inscriptions acceptées pour ce cours
        List<Inscription> inscriptions = inscriptionRepository.findByEtatAndCours(1, cours);

        // Collecte les étudiants liés à ces inscriptions
        List<Personne> students = inscriptions.stream()
                .map(Inscription::getPersonneByIdEtudiant)
                .collect(Collectors.toList());

        model.addAttribute("cours", cours);
        model.addAttribute("students", students);

        return "Professeur/traitementNotes"; // Vue JSP pour la saisie des notes
    }
}
