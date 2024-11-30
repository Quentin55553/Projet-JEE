package com.example.demo.controller.etudiant;

import com.example.demo.entity.Cours;
import com.example.demo.entity.Personne;
import com.example.demo.entity.Resultat;
import com.example.demo.repository.CoursRepository;
import com.example.demo.repository.ResultatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MatiereController {

    @Autowired
    private CoursRepository coursRepository;

    @Autowired
    private ResultatRepository resultatRepository;

    @GetMapping("/Matiere")

    public String afficherDetailsMatiere(@RequestParam("idCours") int courseId, HttpSession session, Model model) {
        // Récupération de l'utilisateur connecté
        Personne user = (Personne) session.getAttribute("user");
        if (user == null || user.getRole() != 3) {
            return "redirect:/login";
        }

        // Récupération du cours
        Cours cours = coursRepository.findById(courseId).orElse(null);
        if (cours == null) {
            return "redirect:/etudiant/MenuEtudiant";
        }

        // Récupération des résultats pour ce cours et cet étudiant
        List<Resultat> resultats = resultatRepository.findByPersonneByIdEtudiantIdPersonneAndCoursByIdCoursIdCours(
                user.getIdPersonne(),
                courseId
        );

        // Ajout des données au modèle
        model.addAttribute("cours", cours);
        model.addAttribute("resultats", resultats);

        return "Etudiant/note_Matiere"; // Correspond au fichier JSP
    }

}
