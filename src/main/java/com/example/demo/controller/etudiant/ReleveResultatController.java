package com.example.demo.controller.etudiant;

import com.example.demo.entity.Cours;
import com.example.demo.entity.Personne;
import com.example.demo.entity.Resultat;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
public class ReleveResultatController {

    @PostMapping("/etudiant/releve")
    public void genererReleve(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException, DocumentException {


        // Récupération des données
        List<Resultat> resultats = (List<Resultat>) session.getAttribute("resultats");
        String moyenneGlobale = request.getParameter("moyenneGlobale");
        Personne etudiant = (Personne) session.getAttribute("user");
        System.out.println("Vérification des paramètres :");
        System.out.println("Session - Resultats : " + session.getAttribute("resultats"));
        System.out.println("Session - User : " + session.getAttribute("user"));
        System.out.println("Request - Moyenne Globale : " + request.getParameter("moyenneGlobale"));
        if (resultats == null || etudiant == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Aucune donnée disponible pour générer le relevé.");
            return;
        }

        Map<Cours, List<Resultat>> coursNotesMap = prepareCoursNotesMap(resultats);

        // Générer le PDF
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        generatePdf(etudiant, coursNotesMap, moyenneGlobale, baos);

        String nomFichier = "releve_notes_" + etudiant.getNom().replaceAll("\\s+", "_") + "_" + etudiant.getPrenom().replaceAll("\\s+", "_") + ".pdf";
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + nomFichier + "\"");
        response.getOutputStream().write(baos.toByteArray());
    }


    private Map<Cours, List<Resultat>> prepareCoursNotesMap(List<Resultat> resultats) {
        Map<Cours, List<Resultat>> coursNotesMap = new java.util.HashMap<>();
        for (Resultat resultat : resultats) {
            Cours cours = resultat.getCoursByIdCours();
            coursNotesMap.computeIfAbsent(cours, k -> new java.util.ArrayList<>()).add(resultat);
        }
        return coursNotesMap;
    }

    /**
     * Génère le PDF avec les informations du relevé de notes
     */
    private void generatePdf(Personne etudiant, Map<Cours, List<Resultat>> coursNotesMap, String moyenneGlobale, ByteArrayOutputStream baos) throws DocumentException, IOException {
        Document document = new Document();
        PdfWriter.getInstance(document, baos);
        document.open();

        // Ajout du logo
        String imagePath = "src/main/resources/static/Images/cytech.png";
        Image image = Image.getInstance(imagePath);
        image.scaleToFit(100, 100);
        image.setAlignment(Element.ALIGN_CENTER);
        document.add(image);

        // Ajout d'un espace après le logo
        document.add(new Paragraph("\n\n"));

        // Police pour le contenu
        Font headerFont = new Font(Font.FontFamily.HELVETICA, 20, Font.BOLD);
        Font subHeaderFont = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
        Font contentFont = new Font(Font.FontFamily.HELVETICA, 13, Font.NORMAL);

        // Titre principal centré
        Paragraph title = new Paragraph("Relevé de notes", headerFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        // Informations générales sur l'étudiant
        document.add(new Paragraph("\n"));
        Paragraph studentInfo = new Paragraph("Étudiant : " + etudiant.getPrenom() + " " + etudiant.getNom(), contentFont);
        studentInfo.setAlignment(Element.ALIGN_CENTER);
        document.add(studentInfo);

        Paragraph globalAverage = new Paragraph("Moyenne Globale : " + moyenneGlobale + "/20", contentFont);
        globalAverage.setAlignment(Element.ALIGN_CENTER);
        document.add(globalAverage);

        document.add(new Paragraph("\n\n"));

        // Section des notes par cours
        Paragraph sectionTitle = new Paragraph("Notes par cours :", subHeaderFont);
        sectionTitle.setAlignment(Element.ALIGN_CENTER);
        document.add(sectionTitle);

        document.add(new Paragraph("\n"));

        // Détails par cours
        for (Map.Entry<Cours, List<Resultat>> entry : coursNotesMap.entrySet()) {
            Cours cours = entry.getKey();
            List<Resultat> resultats = entry.getValue();

            // Nom du cours
            Paragraph courseTitle = new Paragraph("Cours : " + cours.getNomCours(), subHeaderFont);
            courseTitle.setAlignment(Element.ALIGN_LEFT);
            document.add(courseTitle);

            // Informations sur le professeur
            Paragraph professorInfo = new Paragraph(
                    "Professeur : " + cours.getPersonneByIdEnseignant().getPrenom() + " " + cours.getPersonneByIdEnseignant().getNom(),
                    contentFont
            );
            professorInfo.setAlignment(Element.ALIGN_LEFT);
            document.add(professorInfo);

            document.add(new Paragraph("\n"));

            // Notes et moyenne du cours
            double sommeNotes = 0;
            StringBuilder notesDetails = new StringBuilder();
            for (int i = 0; i < resultats.size(); i++) {
                sommeNotes += resultats.get(i).getNote();
                notesDetails.append("- Note ").append(i + 1).append(" : ").append(resultats.get(i).getNote()).append("\n");
            }

            double moyenneCours = sommeNotes / resultats.size();
            Paragraph courseAverage = new Paragraph("Moyenne : " + String.format("%.2f", moyenneCours) + "/20", contentFont);
            courseAverage.setAlignment(Element.ALIGN_LEFT);
            document.add(courseAverage);

            Paragraph notesParagraph = new Paragraph(notesDetails.toString(), contentFont);
            notesParagraph.setAlignment(Element.ALIGN_LEFT);
            document.add(notesParagraph);

        }

        // Fermeture du document
        document.close();
    }

}
