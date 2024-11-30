package com.example.demo.controller.professeur;

import com.example.demo.entity.Cours;
import com.example.demo.entity.Personne;
import com.example.demo.repository.CoursRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class SaisieNotesController {

    @Autowired
    private CoursRepository coursRepository;

    @GetMapping("/professeur/notes")
    public String getCours(HttpSession session, Model model) {
        Personne user = (Personne) session.getAttribute("user");

        if (user == null || user.getRole() != 2) {
            return "redirect:/login";
        }

        List<Cours> coursList = coursRepository.findByPersonneByIdEnseignant(user);
        model.addAttribute("coursList", coursList);
        return "Professeur/saisieNotes_Professeur";
    }
}
