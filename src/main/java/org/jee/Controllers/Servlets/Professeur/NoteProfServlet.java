package org.jee.Controllers.Servlets.Professeur;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.jee.entity.Cours;
import org.jee.entity.Personne;
import org.jee.entity.Inscription;
import org.jee.Util.HibernateUtil;

import java.io.IOException;
import java.util.List;

@WebServlet("/NoteProfServlet")
public class NoteProfServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idCoursStr = request.getParameter("idCours");

        if (idCoursStr == null || idCoursStr.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID du cours manquant.");
            return;
        }

        int idCours = Integer.parseInt(idCoursStr);

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Begin a transaction (optional in some cases, depending on the operations)
            session.beginTransaction();

            // Fetch the course (Cours) object based on the provided ID
            Cours cours = session.get(Cours.class, idCours);
            if (cours == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Cours introuvable.");
                return;
            }

            // Query for all Inscription objects where the state is 1 (accepted students)
            Query<Inscription> inscriptionQuery = session.createQuery(
                    "FROM Inscription WHERE cours.idCours = :idCours AND etat = 1", Inscription.class);
            inscriptionQuery.setParameter("idCours", idCours);
            List<Inscription> inscriptions = inscriptionQuery.list();
            request.setAttribute("cours", cours);
            if (inscriptions.isEmpty()) {
                request.getRequestDispatcher("Vue/Professeur/traitementNotes.jsp").forward(request, response);
                return;
            }

            // Collect the list of students who are accepted for this course
            List<Personne> students = session.createQuery(
                            "SELECT i.personneByIdEtudiant FROM Inscription i WHERE i.cours.idCours = :idCours AND i.etat = 1", Personne.class)
                    .setParameter("idCours", idCours)
                    .list();

            // Log the details of students and course for debugging purposes
            System.out.println("Cours Name: " + cours.getNomCours());
            for (Personne student : students) {
                System.out.println("Student: " + student.getNom() + " " + student.getPrenom());
            }

            // Send the course and list of students as request attributes to the JSP page

            request.setAttribute("students", students);

            // Forward the request to the JSP for further processing
            request.getRequestDispatcher("Vue/Professeur/traitementNotes.jsp").forward(request, response);

            // Commit the transaction if necessary
            session.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erreur lors du traitement des inscriptions.");
        }
    }
}
