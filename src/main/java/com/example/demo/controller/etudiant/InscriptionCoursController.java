package com.example.demo.controller.etudiant;

import com.example.demo.entity.Cours;
import com.example.demo.entity.Inscription;
import com.example.demo.entity.Personne;
import com.example.demo.repository.CoursRepository;
import com.example.demo.repository.InscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class InscriptionCoursController {

    @Autowired
    private CoursRepository coursRepository;

    @Autowired
    private InscriptionRepository inscriptionRepository;

    @GetMapping("/etudiant/inscriptionCours")
    public String afficherCoursDisponibles(HttpSession session, Model model) {
        // Récupérer l'utilisateur connecté
        Personne user = (Personne) session.getAttribute("user");
        if (user == null || user.getRole() != 3) {
            return "redirect:/login";
        }

        // Récupérer les inscriptions de l'étudiant
        List<Inscription> inscriptionsEtudiant = inscriptionRepository.findByPersonneByIdEtudiantIdPersonne(user.getIdPersonne());

        // Récupérer les cours auxquels l'étudiant est déjà inscrit
        List<Cours> coursInscrits = inscriptionsEtudiant.stream()
                .map(Inscription::getCours)
                .collect(Collectors.toList());

        // Récupérer tous les cours non-inscrits
        List<Cours> coursNonInscrits = coursRepository.findAll().stream()
                .filter(cours -> !coursInscrits.contains(cours))
                .collect(Collectors.toList());

        model.addAttribute("coursNonInscrits", coursNonInscrits);
        return "Etudiant/inscriptionCours_etudiant";
    }

    @PostMapping("/etudiant/inscrireCours")
    public String inscrireEtudiantAuCours(@RequestParam("idCours") int idCours, HttpSession session) {
        // Récupérer l'utilisateur connecté
        Personne user = (Personne) session.getAttribute("user");
        if (user == null || user.getRole() != 3) {
            return "redirect:/login";
        }

        // Enregistrer l'inscription
        Inscription nouvelleInscription = new Inscription();
        nouvelleInscription.setPersonneByIdEtudiant(user);
        nouvelleInscription.setCours(coursRepository.findById(idCours).orElse(null));
        nouvelleInscription.setEtat(0);
        nouvelleInscription.setDateInscription(new java.sql.Date(System.currentTimeMillis()));
        inscriptionRepository.save(nouvelleInscription);

        return "redirect:/etudiant/inscriptionCours";
    }
}
