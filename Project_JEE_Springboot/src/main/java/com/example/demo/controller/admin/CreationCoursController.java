package com.example.demo.controller.admin;

import com.example.demo.entity.Cours;
import com.example.demo.entity.Personne;
import com.example.demo.repository.CoursRepository;
import com.example.demo.repository.PersonneRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Date;
import java.util.Optional;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin/cours")
public class CreationCoursController {

    @Autowired
    private CoursRepository coursRepository;

    @Autowired
    private PersonneRepository personneRepository;

    /**
     * Affiche la page de gestion des cours
     */
    @GetMapping("/gestion")
    public String gestionCours(Model model,HttpSession session, HttpServletResponse response) throws IOException {
        // Vérifier si l'utilisateur est connecté et est administrateur
        Personne user = (Personne) session.getAttribute("user");
        if (user == null || user.getRole() != 1) {
            // Redirection vers la page de connexion si l'utilisateur n'est pas connecté ou n'est pas administrateur
            response.sendRedirect("/login");
            return null;
        }
        model.addAttribute("coursList", coursRepository.findAll());
        return "Admin/gestionCours_Admin"; // Correspond à WEB-INF/jsp/gestionCours_Admin.jsp
    }

    /**
     * Affiche le formulaire de création d'un cours
     */
    @GetMapping("/creation")
    public String creationForm(Model model) throws IOException{
        model.addAttribute("action", "creation");
        model.addAttribute("cours", new Cours());
        return "Admin/creationCours_Admin";
    }

    /**
     * Affiche le formulaire de modification d'un cours existant
     */
    @GetMapping("/modification/{id}")
    public String modificationForm(
            @PathVariable int id,
            Model model,
            RedirectAttributes redirectAttributes
    ) throws IOException{
        Optional<Cours> coursOptional = coursRepository.findById(id);
        if (coursOptional.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Cours introuvable.");
            return "redirect:/admin/cours/gestion";
        }
        model.addAttribute("action", "modification");
        model.addAttribute("cours", coursOptional.get());
        return "Admin/creationCours_Admin"; // Réutilise le même JSP pour la modification
    }

    /**
     * Sauvegarde un cours (création ou modification)
     */
    @PostMapping("/save")
    public String saveCours(
            @RequestParam(required = false) Integer id_cours, // Optionnel pour création
            @RequestParam String nom_cours,
            @RequestParam(required = false) String description,
            @RequestParam String date_debut,
            @RequestParam String date_fin,
            @RequestParam String id_professeur, // ID du professeur
            @RequestParam String action,
            RedirectAttributes redirectAttributes
    ) throws IOException{
        try {
            // Conversion des dates
            Date dateDebut = Date.valueOf(date_debut);
            Date dateFin = Date.valueOf(date_fin);

            // Vérifie si le professeur existe
            Optional<Personne> professeurOptional = personneRepository.findById(id_professeur);
            if (professeurOptional.isEmpty()) {
                redirectAttributes.addFlashAttribute("error", "Professeur introuvable.");
                return "redirect:/admin/cours/gestion";
            }

            Personne professeur = professeurOptional.get();

            Cours cours;
            if ("modification".equals(action) && id_cours != null) {
                // Modification d'un cours existant
                Optional<Cours> coursOptional = coursRepository.findById(id_cours);
                if (coursOptional.isEmpty()) {
                    redirectAttributes.addFlashAttribute("error", "Cours introuvable.");
                    return "redirect:/admin/cours/gestion";
                }
                cours = coursOptional.get();
            } else {
                // Création d'un nouveau cours
                cours = new Cours();
            }

            // Met à jour les champs du cours
            cours.setNomCours(nom_cours);
            cours.setDescription(description);
            cours.setDateDebut(dateDebut);
            cours.setDateFin(dateFin);
            cours.setPersonneByIdEnseignant(professeur);

            // Sauvegarde le cours
            coursRepository.save(cours);

            redirectAttributes.addFlashAttribute("success", "Cours enregistré avec succès.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors de l'enregistrement : " + e.getMessage());
        }
        return "redirect:/admin/cours/gestion";
    }

    /**
     * Supprime un cours
     */
    @PostMapping("/delete/{id}")
    public String deleteCours(
            @PathVariable int id,
            RedirectAttributes redirectAttributes
    ) {
        try {
            coursRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Cours supprimé avec succès.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors de la suppression : " + e.getMessage());
        }
        return "redirect:/admin/cours/gestion";
    }
}
