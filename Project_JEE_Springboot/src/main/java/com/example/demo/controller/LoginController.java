package com.example.demo.controller;

import com.example.demo.entity.Personne;
import com.example.demo.repository.PersonneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private PersonneRepository personneRepository;

    @GetMapping("/login")
    public String showLoginForm(@RequestParam(required = false) String error, Model model) {
        model.addAttribute("error", error);
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String mdp,
                        HttpSession session) {

        Personne personne = personneRepository.findByIdPersonneAndPassword(email, mdp);

        if (personne != null) {
            session.setAttribute("user", personne);
            switch (personne.getRole()) {
                case 1:
                    return "redirect:/admin/menu";
                case 2:
                    return "redirect:/professeur/home";
                case 3:
                    return "redirect:/etudiant/MenuEtudiant";
                default:
                    return "redirect:/login?error=unknown_role";
            }
        } else {
            return "redirect:/login?error=invalid_credentials";
        }
    }
}
