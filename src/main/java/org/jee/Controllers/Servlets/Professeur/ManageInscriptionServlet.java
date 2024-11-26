package org.jee.Controllers.Servlets.Professeur;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.jee.entity.Cours;
import org.jee.entity.Inscription;
import org.jee.Util.HibernateUtil;
import org.jee.entity.Personne;

import java.io.IOException;
import java.util.Collection;

@WebServlet("/ManageInscriptionServlet")
public class ManageInscriptionServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String studentEmail = request.getParameter("studentEmail");
        String courseName = request.getParameter("courseName");
        String action = request.getParameter("action");
        String commentaire = request.getParameter("commentaire");

        if (studentEmail == null || courseName == null || action == null) {
            response.getWriter().println("Erreur : Informations manquantes.");
            return;
        }

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();

            // Retrieve the student (Personne) entity using the email
            Personne student = session.get(Personne.class, studentEmail);

            if (student == null) {
                response.getWriter().println("Erreur : L'étudiant avec cet email n'a pas été trouvé.");
                return;
            }

            // Retrieve the course (Cours) entity using the course name
            Cours course = (Cours) session.createQuery("FROM Cours WHERE nomCours = :courseName")
                    .setParameter("courseName", courseName)
                    .uniqueResult();

            if (course == null) {
                response.getWriter().println("Erreur : Le cours n'a pas été trouvé.");
                return;
            }

            // Retrieve the inscriptions for the student
            Collection<Inscription> inscriptions = student.getInscriptionsByIdPersonne();

            // Find the corresponding inscription for the course
            Inscription inscriptionToUpdate = null;
            for (Inscription inscription : inscriptions) {
                if (inscription.getCoursByIdCours().equals(course)) {
                    inscriptionToUpdate = inscription;
                    break;
                }
            }

            if (inscriptionToUpdate == null) {
                response.getWriter().println("Erreur : L'inscription à ce cours n'a pas été trouvée.");
                return;
            }

            // Set the new state (Etat) based on the action
            if ("accept".equals(action)) {
                inscriptionToUpdate.setEtat(1);  // Accept
            } else if ("deny".equals(action)) {
                inscriptionToUpdate.setEtat(2);  // Deny
            }

            // Set the comment if provided
            inscriptionToUpdate.setNoteProf(commentaire);

            // Merge the updated inscription to ensure the changes are tracked by Hibernate
            session.merge(inscriptionToUpdate);  // Merge the detached entity

            tx.commit();  // Commit the transaction

            // Redirect to inscription_prof.jsp after successful update
            response.sendRedirect(request.getContextPath()+"/InscriptionProf");  // Adjust the path based on your project structure

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Erreur lors de la mise à jour de l'inscription.");
        }
    }
}
