package com.example.demo.controller.etudiant;

import com.example.demo.entity.Cours;
import com.example.demo.entity.Personne;
import com.example.demo.entity.Resultat;
import com.example.demo.repository.ResultatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class NotesController {

    @Autowired
    private ResultatRepository resultatRepository;

    @GetMapping("/etudiant/notes")
    public String afficherNotesEtudiant(HttpSession session, Model model) {
        // Récupérer l'étudiant connecté
        Personne etudiant = (Personne) session.getAttribute("user");
        if (etudiant == null || etudiant.getRole() != 3) {
            return "redirect:/login";
        }

        // Récupérer les résultats de l'étudiant
        List<Resultat> resultats = resultatRepository.findByPersonneByIdEtudiantIdPersonne(etudiant.getIdPersonne());
        if (resultats == null || resultats.isEmpty()) {
            model.addAttribute("message", "Pas de notes disponibles.");
            return "Etudiant/notesEtudiant";
        }

        // Calcul des moyennes
        Map<Cours, List<Resultat>> coursNotesMap = new HashMap<>();
        double sommeNotes = 0;
        for (Resultat resultat : resultats) {
            Cours cours = resultat.getCoursByIdCours();
            coursNotesMap.computeIfAbsent(cours, k -> new java.util.ArrayList<>()).add(resultat);
            sommeNotes += resultat.getNote();
        }

        double moyenneGlobale = sommeNotes / resultats.size();
        model.addAttribute("moyenneGlobale", String.format("%.2f", moyenneGlobale));
        model.addAttribute("coursNotesMap", coursNotesMap);
        session.setAttribute("resultats", resultats);

        return "Etudiant/notesEtudiant";
    }
}
