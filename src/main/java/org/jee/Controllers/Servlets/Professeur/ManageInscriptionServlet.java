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

/**
 * La servlet gére l'acceptation ou le refus d'un postulant au cours indiqué.
 */
@WebServlet("/ManageInscriptionServlet")
public class ManageInscriptionServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String studentEmail = request.getParameter("studentEmail");
        String courseName = request.getParameter("courseName");
        String action = request.getParameter("action");
        String commentaire = request.getParameter("commentaire");

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();

            Personne student = session.get(Personne.class, studentEmail);

            if (student == null) {
                response.getWriter().println("Erreur : L'étudiant avec cet email n'a pas été trouvé.");
                return;
            }

            Cours course = (Cours) session.createQuery("FROM Cours WHERE nomCours = :courseName",Cours.class)
                    .setParameter("courseName", courseName)
                    .uniqueResult();

            if (course == null) {
                response.getWriter().println("Erreur : Le cours n'a pas été trouvé.");
                return;
            }

            Collection<Inscription> inscriptions = student.getInscriptionsByIdPersonne();

            Inscription inscriptionToUpdate = null;
            for (Inscription inscription : inscriptions) {
                if (inscription.getCours().equals(course)) {
                    inscriptionToUpdate = inscription;
                    break;
                }
            }

            if (inscriptionToUpdate == null) {
                response.getWriter().println("Erreur : L'inscription à ce cours n'a pas été trouvée.");
                return;
            }


            if ("accept".equals(action)) {
                inscriptionToUpdate.setEtat(1);  // Accepter
            } else if ("deny".equals(action)) {
                inscriptionToUpdate.setEtat(2);  // Refuser
            }

            inscriptionToUpdate.setDescriptionRefus(commentaire);

            session.merge(inscriptionToUpdate);  // Merge the detached entity

            tx.commit();

            response.sendRedirect(request.getContextPath()+"/InscriptionProf");  // Adjust the path based on your project structure

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Erreur lors de la mise à jour de l'inscription.");
        }
    }
}
