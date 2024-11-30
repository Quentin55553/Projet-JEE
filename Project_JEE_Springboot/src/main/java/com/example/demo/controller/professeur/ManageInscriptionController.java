package com.example.demo.controller;

import com.example.demo.entity.Cours;
import com.example.demo.entity.Inscription;
import com.example.demo.entity.Personne;
import com.example.demo.repository.CoursRepository;
import com.example.demo.repository.InscriptionRepository;
import com.example.demo.repository.PersonneRepository;
import com.example.demo.service.EmailService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ManageInscriptionController {

    @Autowired
    private InscriptionRepository inscriptionRepository;

    @Autowired
    private PersonneRepository personneRepository;

    @Autowired
    private CoursRepository coursRepository;

    @Autowired
    private EmailService emailService;

    @PostMapping("/professeur/manage-inscription")
    public String manageInscription(
            @RequestParam String studentEmail,
            @RequestParam String courseName,
            @RequestParam String action,
            @RequestParam(required = false) String commentaire,
            HttpSession session) {

        // Récupérer l'étudiant et le cours
        Personne student = personneRepository.findById(studentEmail).orElse(null);
        Cours course = coursRepository.findByNomCours(courseName);

        if (student == null || course == null) {
            return "redirect:/professeur/inscription?error=not_found";
        }

        // Trouver l'inscription correspondante
        Inscription inscription = inscriptionRepository.findByPersonneByIdEtudiantAndCours(student, course);

        if (inscription == null) {
            return "redirect:/professeur/inscription?error=not_found";
        }

        // Mettre à jour l'état de l'inscription et envoyer un email
        String subject = "Mise à jour de votre inscription";
        String message;

        if ("accept".equals(action)) {
            inscription.setEtat(1); // Accepté
            message = "Bonjour " + student.getPrenom() + " " + student.getNom() + ",\n\n" +
                    "Votre inscription au cours \"" + course.getNomCours() + "\" a été acceptée.\n\n" +
                    "Cordialement,\nL'équipe.";
        } else if ("deny".equals(action)) {
            inscription.setEtat(2); // Refusé
            inscription.setDescriptionRefus(commentaire);
            message = "Bonjour " + student.getPrenom() + " " + student.getNom() + ",\n\n" +
                    "Votre inscription au cours \"" + course.getNomCours() + "\" a été refusée.\n" +
                    "Motif : " + commentaire + "\n\n" +
                    "Cordialement,\nL'équipe.";
        } else {
            return "redirect:/professeur/inscription?error=invalid_action";
        }

        inscriptionRepository.save(inscription); // Sauvegarder les modifications

        // Envoyer l'email au contact de l'étudiant
        emailService.sendEmail(student.getContact(), subject, message);

        return "redirect:/professeur/inscription?success";
    }
}
