package com.example.demo.controller.admin;

import com.example.demo.entity.Personne;
import com.example.demo.repository.PersonneRepository;
import com.example.demo.service.EmailService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Date;

@Controller
@RequestMapping("/admin/inscription")
public class InscriptionController {

    @Autowired
    private PersonneRepository personneRepository;

    @Autowired
    private EmailService emailService;

    /**
     * Affiche le formulaire d'inscription pour un étudiant.
     */
    @GetMapping("/etudiant")
    public String inscriptionEtudiantForm(Model model,HttpSession session, HttpServletResponse response) {
        model.addAttribute("role", 3); // Rôle 3 pour étudiant
        return "Admin/inscriptionEtudiant_Admin"; // Correspond à WEB-INF/jsp/inscriptionEtudiant_Admin.jsp
    }

    /**
     * Affiche le formulaire d'inscription pour un professeur.
     */
    @GetMapping("/professeur")
    public String inscriptionProfesseurForm(Model model,HttpSession session, HttpServletResponse response) {
        model.addAttribute("role", 2); // Rôle 2 pour professeur
        return "Admin/inscriptionProfesseur_Admin"; // Correspond à WEB-INF/jsp/inscriptionProfesseur_Admin.jsp
    }

    /**
     * Traite le formulaire d'inscription pour un étudiant ou un professeur.
     */
    @PostMapping("/save")
    public String saveInscription(
            @RequestParam String nom,
            @RequestParam String prenom,
            @RequestParam String date_naissance,
            @RequestParam String contact,
            @RequestParam String password,
            @RequestParam int role,
            RedirectAttributes redirectAttributes,HttpSession session, HttpServletResponse response
    ) throws IOException {
        // Vérifier si l'utilisateur est connecté et est administrateur
        Personne user = (Personne) session.getAttribute("user");
        if (user == null || user.getRole() != 1) {
            // Redirection vers la page de connexion si l'utilisateur n'est pas connecté ou n'est pas administrateur
            response.sendRedirect("/login");
            return null;
        }
        try {
            String idPersonne = nom.replaceAll(" ", "_") + "." + prenom.replaceAll(" ", "_") + "@cy-tech.fr";

            // Vérifie si une personne avec cet identifiant existe déjà
            if (personneRepository.existsById(idPersonne)) {
                redirectAttributes.addFlashAttribute("error", "L'identifiant " + idPersonne + " existe déjà.");
                return "redirect:/admin/inscription/" + (role == 3 ? "etudiant" : "professeur");
            }

            // Crée une nouvelle personne
            Personne personne = new Personne();
            personne.setNom(nom.toLowerCase());
            personne.setPrenom(prenom.toLowerCase());
            personne.setDateNaissance(Date.valueOf(date_naissance));
            personne.setContact(contact);
            personne.setPassword(password);
            personne.setRole(role);
            personne.setIdPersonne(idPersonne);

            // Sauvegarde dans la base de données
            personneRepository.save(personne);

            // Envoyer un email de confirmation
            String subject = "Confirmation d'inscription";
            String body = "Bonjour " + prenom + " " + nom + ",\n\n" +
                    "Votre inscription à CY Tech a été réussie. Voici vos identifiants :\n" +
                    "Identifiant : " + idPersonne + "\n" +
                    "Mot de passe : " + password + "\n\n" +
                    "Veuillez conserver ces informations en lieu sûr.\n\n" +
                    "Cordialement,\nL'équipe administrative.";
            emailService.sendEmail(contact, subject, body);

            redirectAttributes.addFlashAttribute("success", "Inscription réussie pour : " + idPersonne);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors de l'inscription : " + e.getMessage());
        }
        return "redirect:/admin/menu";
    }
}
