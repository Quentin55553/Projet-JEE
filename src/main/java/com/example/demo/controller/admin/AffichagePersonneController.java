package com.example.demo.controller.admin;

import com.example.demo.entity.Personne;
import com.example.demo.service.PersonneService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/admin/listePersonne")
public class AffichagePersonneController {

    @Autowired
    private PersonneService personneService;

    // Afficher la liste des personnes
    @RequestMapping("/affichagePersonne")
    public String afficherPersonnes(@RequestParam(required = false) String typePersonne,
                                    @RequestParam(required = false) String form_filtre,
                                    @RequestParam(required = false) String filtre_id,
                                    @RequestParam(required = false) String filtre_nom,
                                    @RequestParam(required = false) String filtre_prenom,
                                    @RequestParam(required = false) String filtre_contact,
                                    @RequestParam(value = "error", required = false) String error,
                                    @RequestParam(value = "success", required = false) String success,
                                    Model model,
                                    HttpSession session, HttpServletResponse response) throws IOException {

        // Vérifier si l'utilisateur est connecté et est administrateur
        Personne user = (Personne) session.getAttribute("user");
        if (user == null || user.getRole() != 1) {
            // Redirection vers la page de connexion si l'utilisateur n'est pas connecté ou n'est pas administrateur
            response.sendRedirect("/login");
            return null;
        }
        List<Personne> liste;
        if (typePersonne == null) {
            typePersonne = "etudiant";
        }
        System.out.println("Filtre ID : " + filtre_id);
        System.out.println("Filtre Nom : " + filtre_nom);
        System.out.println("Filtre Prénom : " + filtre_prenom);
        System.out.println("Filtre Contact : " + filtre_contact);
        System.out.println("Filtre ghfgdfdsv : " + form_filtre);

        if (form_filtre == null) {
            int role = resolveRole(typePersonne);
            liste = personneService.getPersonnesByRole(role);
        } else {
            int role = resolveRole(typePersonne);
            liste = personneService.getPersonnesByFilter(role, filtre_id, filtre_nom, filtre_prenom, filtre_contact);
        }

        model.addAttribute("liste", liste);
        model.addAttribute("typePersonne", typePersonne);

        if (error != null) {
            model.addAttribute("error", error);
        }
        if (success != null) {
            model.addAttribute("success", success);
        }

        return "Admin/affichagePersonne";
    }

    // Formulaire de modification
    @GetMapping("/modifier")
    public String afficherFormulaireModification(@RequestParam("idPersonne") String idPersonne,
                                                 @RequestParam(value = "error", required = false) String error,
                                                 Model model) {

        Personne personne = personneService.getPersonneById(idPersonne);

        model.addAttribute("personne", personne);
        if (error != null) {
            model.addAttribute("error", error);
        }

        return "Admin/ModificationPersonne_Admin";
    }

    // Gestion de la modification
    @PostMapping("/modifier")
    public String modifierPersonne(@RequestParam("idPersonne") String idPersonne,
                                   @RequestParam("nom") String nom,
                                   @RequestParam("prenom") String prenom,
                                   @RequestParam("date") String date,
                                   Model model) {
        try {
            Personne personne = personneService.getPersonneById(idPersonne);

            if (personne == null) {
                return "redirect:/admin/listePersonne/affichagePersonne?error=Personne introuvable.";
            }

            personne.setNom(nom);
            personne.setPrenom(prenom);
            personne.setDateNaissance(java.sql.Date.valueOf(date));

            personneService.updatePersonne(personne);
            return "redirect:/admin/listePersonne/affichagePersonne?success=Modification reussie.";

        } catch (Exception e) {
            return "redirect:/admin/listePersonne/modifier?idPersonne=" + idPersonne + "&error=Erreur lors de la modification.";
        }
    }

    // Gestion de la suppression
    @PostMapping("/supprimer")
    public String supprimerPersonne(@RequestParam("idPersonne") String idPersonne) {
        try {
            Personne personne = personneService.getPersonneById(idPersonne);

            if (personne == null) {
                return "redirect:/admin/listePersonne/affichagePersonne?error=Personne introuvable.";
            }

            personneService.deletePersonne(personne);
            return "redirect:/admin/listePersonne/affichagePersonne?success=Personne supprimee avec succes.";
        } catch (Exception e) {
            return "redirect:/admin/listePersonne/affichagePersonne?error=Erreur lors de la suppression.";
        }
    }
    @GetMapping("/details")
    public String afficherDetails(@RequestParam("id") String id, Model model) {
        // Récupérer les détails de la personne en fonction de l'ID
        Personne personne = personneService.getPersonneById(id);
        model.addAttribute("personne", personne);
        return "Admin/detailsPersonne";
    }

    // Méthode pour résoudre le rôle
    private int resolveRole(String typePersonne) {
        if ("etudiant".equalsIgnoreCase(typePersonne)) {
            return 3; // Étudiant
        } else if ("professeur".equalsIgnoreCase(typePersonne)) {
            return 2; // Professeur
        } else {
            return 0; // Autre
        }
    }
}
