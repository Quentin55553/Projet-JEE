package org.jee.Controllers.Servlets.Etudiant;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.jee.entity.Cours;
import org.jee.entity.Personne;
import org.jee.entity.Resultat;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Servlet déstinée à générer un relevé de notes pour un étudiant au format PDF
 */
@WebServlet("/ReleveResultatServlet")
public class ReleveResultatServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        Personne etudiant = (Personne) session.getAttribute("user");

        // Récupère les données JSON du formulaire
        String resultatsJson = request.getParameter("resultatsJson");
        String moyenneGlobale = request.getParameter("moyenneGlobale");

        // Désérialise la liste JSON en objets Java
        Gson gson = new Gson();
        List<Resultat> resultats = gson.fromJson(resultatsJson, new TypeToken<List<Resultat>>() {}.getType());

        // Prépare la Map des cours associés aux notes
        Map<Cours, List<Resultat>> coursNotesMap = prepareCoursNotesMap(resultats);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            generatePdf(etudiant, coursNotesMap, moyenneGlobale, baos);

        } catch (DocumentException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erreur lors de la génération du PDF.");
            return;
        }

        String nomFichier = "releve_notes_" + etudiant.getNom().replaceAll("\\s+", "_") + "_" + etudiant.getPrenom().replaceAll("\\s+", "_") + ".pdf";

        // Envoi du PDF en réponse
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + nomFichier + "\"");
        response.getOutputStream().write(baos.toByteArray());
    }


    /** 
     * Méthode pour générer le PDF
     * */
    private void generatePdf(Personne etudiant, Map<Cours, List<Resultat>> coursNotesMap, String moyenneGlobale, ByteArrayOutputStream baos) throws DocumentException, IOException {
        Document document = new Document();
        PdfWriter.getInstance(document, baos);
        document.open();

        String imagePath = getServletContext().getRealPath("/Images/cytech.png");
        Image image = Image.getInstance(imagePath);

        image.scaleToFit(100, 100);

        // Positionne l'image en haut à gauche
        float x = document.left() - image.getScaledWidth() + 90;
        float y = document.top() - image.getScaledHeight() + 20;
        image.setAbsolutePosition(x, y);
        document.add(image);

        Font headerFont = new Font(Font.FontFamily.HELVETICA, 20, Font.BOLD);
        Font ranksHeaderFont = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
        Font contentFont = new Font(Font.FontFamily.HELVETICA, 13, Font.NORMAL);

        Paragraph title = new Paragraph("Relevé de notes", headerFont);
        title.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(title);
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("Etudiant : " + etudiant.getPrenom() + " " + etudiant.getNom(), contentFont));
        document.add(new Paragraph("Moyenne des notes : " + moyenneGlobale + "/20", contentFont));
        document.add(new Paragraph("-------------------------------------------------------------"));
        document.add(new Paragraph("Notes par cours", ranksHeaderFont));
        document.add(new Paragraph("\n"));

        Iterator<Map.Entry<Cours, List<Resultat>>> iterator = coursNotesMap.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<Cours, List<Resultat>> entry = iterator.next();
            Cours cours = entry.getKey();
            List<Resultat> resultats = entry.getValue();

            // Affichage unique du nom du cours et du professeur
            document.add(new Paragraph("Cours : " + cours.getNomCours(), contentFont));
            document.add(new Paragraph("Professeur : " + cours.getPersonneByIdEnseignant().getPrenom() + " " + cours.getPersonneByIdEnseignant().getNom(), contentFont));

            // Calcul de la moyenne du cours et affichage
            double sommeNotesCours = 0;

            // On ajoute les notes sous forme de liste "Note 1, Note 2, ..." et on calcul la moyenne en même temps
            StringBuilder notesList = new StringBuilder("Notes :\n");
            for (int i = 0; i < resultats.size(); i++) {
                sommeNotesCours += resultats.get(i).getNote();
                notesList.append("          Note " + (i + 1) + " : " + resultats.get(i).getNote());
                if (i < resultats.size() - 1) {
                    notesList.append("\n");
                }
            }

            double moyenneCours = sommeNotesCours / resultats.size();
            document.add(new Paragraph("Moyenne : " + String.format("%.2f", moyenneCours) + "/20", contentFont));

            document.add(new Paragraph(notesList.toString(), contentFont));

            // Si ce n'est pas le dernier élément, on ajoute le séparateur
            if (iterator.hasNext()) {
                document.add(new Paragraph("------"));
            }
        }

        document.close();
    }


    /** 
     * Méthode pour convertir la liste de résultats en Map des cours avec les résultats associés
     */
    private Map<Cours, List<Resultat>> prepareCoursNotesMap(List<Resultat> resultats) {
        Map<Cours, List<Resultat>> coursNotesMap = new java.util.HashMap<>();

        for (Resultat resultat : resultats) {
            Cours cours = resultat.getCoursByIdCours();
            coursNotesMap.computeIfAbsent(cours, k -> new java.util.ArrayList<>()).add(resultat);
        }

        return coursNotesMap;
    }
}
