package com.example.demo.controller.etudiant;

import com.example.demo.entity.Cours;
import com.example.demo.entity.Inscription;
import com.example.demo.entity.Personne;
import com.example.demo.repository.InscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MenuEtudiantController {

    @Autowired
    private InscriptionRepository inscriptionRepository;

    @GetMapping("/etudiant/MenuEtudiant")
    public String afficherMenuEtudiant(HttpSession session, Model model) {
        // Récupérer l'utilisateur connecté
        Personne user = (Personne) session.getAttribute("user");
        if (user == null || user.getRole() != 3) {
            return "redirect:/login";
        }

        // Récupérer toutes les inscriptions de l'étudiant
        List<Inscription> allInscriptions = inscriptionRepository.findByPersonneByIdEtudiantIdPersonne(user.getIdPersonne());

        // Séparer les inscriptions par leur état
        List<Inscription> inscriptionsEnAttenteOuRefusees = new ArrayList<>();
        List<Cours> coursAcceptes = new ArrayList<>();

        for (Inscription inscription : allInscriptions) {
            if (inscription.getEtat() == 0 || inscription.getEtat() == 2) {
                inscriptionsEnAttenteOuRefusees.add(inscription);
            } else if (inscription.getEtat() == 1) {
                coursAcceptes.add(inscription.getCours());
            }
        }

        // Ajouter les données déjà formatées au modèle
        model.addAttribute("inscriptions", inscriptionsEnAttenteOuRefusees);
        model.addAttribute("courses", coursAcceptes);

        return "Etudiant/menuEtudiant"; // Vue JSP
    }
}
