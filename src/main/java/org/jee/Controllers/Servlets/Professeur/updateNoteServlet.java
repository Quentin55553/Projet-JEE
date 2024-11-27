package org.jee.Controllers.Servlets.Professeur;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.jee.entity.Resultat;
import org.jee.entity.Cours;
import org.jee.entity.Personne;
import org.jee.Util.HibernateUtil;

import java.io.IOException;
import java.util.Enumeration;

@WebServlet("/UpdateNotesServlet")
public class updateNoteServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            // Get course ID from the form
            int idCours = Integer.parseInt(request.getParameter("idCours"));

            // Iterate through the submitted notes
            Enumeration<String> parameterNames = request.getParameterNames();
            while (parameterNames.hasMoreElements()) {
                String paramName = parameterNames.nextElement();
                if (paramName.startsWith("note_")) {
                    // Extract student email and note value
                    String studentEmail = paramName.substring(5); // Extract email
                    String noteStr = request.getParameter(paramName); // Get note value

                    double note = Double.parseDouble(noteStr); // Parse to double

                    System.out.println("Student Email: " + studentEmail + ", Note: " + note);

                    // Create a new Resultat object
                    Resultat resultat = new Resultat();

                    // Retrieve the student (Personne) and course (Cours) entities
                    Personne etudiant = session.get(Personne.class, studentEmail);
                    Cours cours = session.get(Cours.class, idCours);

                    if (etudiant != null && cours != null) {
                        resultat.setPersonneByIdEtudiant(etudiant);
                        resultat.setCoursByIdCours(cours);
                        resultat.setNote(note);

                        // Save the Resultat entity
                        session.persist(resultat);
                    }
                }
            }

            // Commit the transaction
            transaction.commit();
            response.sendRedirect("Vue/Professeur/confirmation.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erreur lors de la mise à jour des notes.");
        }
    }
}
