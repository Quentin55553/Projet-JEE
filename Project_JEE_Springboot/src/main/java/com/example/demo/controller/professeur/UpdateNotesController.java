package com.example.demo.controller.professeur;

import com.example.demo.entity.Cours;
import com.example.demo.entity.Personne;
import com.example.demo.entity.Resultat;
import com.example.demo.repository.CoursRepository;
import com.example.demo.repository.PersonneRepository;
import com.example.demo.repository.ResultatRepository;
import com.example.demo.service.EmailService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Enumeration;

@Controller
public class UpdateNotesController {

    @Autowired
    private ResultatRepository resultatRepository;

    @Autowired
    private PersonneRepository personneRepository;

    @Autowired
    private CoursRepository coursRepository;

    @Autowired
    private EmailService emailService;

    @PostMapping("/professeur/update-notes")
    public String updateNotes(HttpServletRequest request) {
        int idCours = Integer.parseInt(request.getParameter("idCours"));

        // Récupérer le cours
        Cours cours = coursRepository.findById(idCours).orElse(null);
        if (cours == null) {
            return "redirect:/professeur/notes?error=cours_invalide";
        }

        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            if (paramName.startsWith("note_")) {
                // Extraire l'identifiant de l'étudiant et la note
                String studentEmail = paramName.substring(5);
                double note = Double.parseDouble(request.getParameter(paramName));

                // Récupérer l'étudiant
                Personne etudiant = personneRepository.findById(studentEmail).orElse(null);

                if (etudiant != null) {
                    // Créer un nouveau résultat sans vérification
                    Resultat resultat = new Resultat();
                    resultat.setCoursByIdCours(cours);
                    resultat.setPersonneByIdEtudiant(etudiant);
                    resultat.setNote(note);
                    resultatRepository.save(resultat);

                    // Envoyer un email à l'étudiant
                    String subject = "Mise à jour de votre note pour le cours " + cours.getNomCours();
                    String body = "Bonjour " + etudiant.getPrenom() + " " + etudiant.getNom() + ",\n\n" +
                            "Votre note pour le cours \"" + cours.getNomCours() + "\" a été mise à jour.\n" +
                            "Nouvelle note : " + note + "\n\n" +
                            "Cordialement,\nL'équipe enseignante.";
                    emailService.sendEmail(etudiant.getContact(), subject, body);
                }
            }
        }

        return "redirect:/professeur/notes?success=notes_updated";
    }
}
