package com.example.demo.controller;

import com.example.demo.entity.Cours;
import com.example.demo.entity.Inscription;
import com.example.demo.entity.Personne;
import com.example.demo.repository.CoursRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ProfesseurInscriptionController {

    @Autowired
    private CoursRepository coursRepository;

    /**
     * Affiche les inscriptions en attente.
     */
    @GetMapping("/professeur/inscription")
    public String listInscriptions(HttpSession session, Model model) {
        Personne user = (Personne) session.getAttribute("user");

        if (user == null || user.getRole() != 2) {
            return "redirect:/login";
        }

        List<Cours> courses = coursRepository.findByPersonneByIdEnseignant(user);

        List<Map<String, String>> inscriptionDetails = new ArrayList<>();
        for (Cours course : courses) {
            for (Inscription inscription : course.getInscriptions()) {
                if (inscription.getEtat() == 0) { // État 0 = En attente
                    Map<String, String> details = new HashMap<>();
                    details.put("studentName", inscription.getPersonneByIdEtudiant().getNom() + " " + inscription.getPersonneByIdEtudiant().getPrenom());
                    details.put("studentEmail", inscription.getPersonneByIdEtudiant().getIdPersonne());
                    details.put("courseName", course.getNomCours());
                    inscriptionDetails.add(details);
                }
            }
        }

        model.addAttribute("inscriptionDetails", inscriptionDetails);
        return "Professeur/inscription_prof"; // Vue JSP
    }
}
