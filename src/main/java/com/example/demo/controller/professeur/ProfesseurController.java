package com.example.demo.controller;

import com.example.demo.entity.Personne;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/professeur")
public class ProfesseurController {

    /**
     * Affiche le menu principal pour les professeurs.
     */
    @GetMapping("/home")
    public String professeurHome(HttpSession session, Model model) {
        Personne user = (Personne) session.getAttribute("user");

        // Vérifiez si l'utilisateur est connecté et est un professeur
        if (user == null || user.getRole() != 2) {
            return "redirect:/login"; // Redirige vers la page de connexion
        }

        // Passe les informations de l'utilisateur à la vue
        model.addAttribute("user", user);

        return "Professeur/menu_Professeur"; // Correspond à WEB-INF/jsp/Professeur/menu_Professeur.jsp
    }
}
