package org.jee.Controllers.Servlets.Etudiant;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.jee.entity.Cours;
import org.jee.entity.Inscription;
import org.jee.entity.Personne;
import org.jee.Util.HibernateUtil;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;

@WebServlet("/InscriptionCoursServlet")
public class InscriptionCoursServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession();
        Personne user = (Personne) httpSession.getAttribute("user");

        if (user == null) {
            response.sendRedirect("login.jsp"); // Redirect to login if not authenticated
            return;
        }

        // Get the course ID from the request
        String idCoursParam = request.getParameter("idCours");
        if (idCoursParam == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid course ID.");
            return;
        }

        int idCours;
        try {
            idCours = Integer.parseInt(idCoursParam);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Course ID must be a number.");
            return;
        }

        // Perform database operations
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            // Fetch the course by ID
            Cours cours = session.get(Cours.class, idCours);
            if (cours == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Course not found.");
                return;
            }

            // Create a new inscription
            Inscription inscription = new Inscription();
            inscription.setDateInscription(Date.valueOf(LocalDate.now())); // Set current date
            inscription.setEtat(0); // Etat is set to 0
            inscription.setPersonneByIdEtudiant(user); // Set the student
            inscription.setCours(cours); // Set the course

            // Save the new inscription to the database
            session.persist(inscription);
            transaction.commit();

            // Redirect to the success page or refresh the course list
            response.sendRedirect(request.getContextPath()+"/DemandeInscriptionServlet");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while processing the inscription.");
        }
    }
}
