package org.jee.Controllers.Servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.jee.entity.Personne;
import org.jee.entity.Cours;
import org.jee.entity.Inscription;
import org.jee.Util.HibernateUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/InscriptionProf")
public class InscriptionProfServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve the session ID (assuming it represents the professor)
        request.getSession().setAttribute("id", "2.1@cy-tech.fr");
        String sessionId = (String) request.getSession().getAttribute("id");

        if (sessionId == null) {
            response.getWriter().println("Error: User session not found.");
            return;
        }

        // Open Hibernate session
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();

            // Fetch the professor entity by ID
            Personne professor = session.get(Personne.class, sessionId);

            if (professor == null) {
                response.getWriter().println("Error: Professor not found.");
                return;
            }

            // Get the courses taught by this professor
            List<Cours> courses = new ArrayList<>(professor.getCoursByIdPersonne());

            // List to hold inscription details
            List<Map<String, String>> inscriptionDetails = new ArrayList<>();

            // Iterate over each course and retrieve inscriptions with `etat = 0`
            for (Cours course : courses) {
                for (Inscription inscription : course.getInscriptions()) {
                    if (inscription.getEtat() == 0) { // Check the state
                        // Fetch student and course details
                        Personne student = inscription.getPersonneByIdEtudiant();
                        Map<String, String> details = new HashMap<>();
                        details.put("studentName", student.getNom() + " " + student.getPrenom());
                        details.put("studentEmail", student.getIdPersonne());
                        details.put("courseName", course.getNomCours());
                        inscriptionDetails.add(details);
                    }
                }
            }

            request.setAttribute("inscriptionDetails", inscriptionDetails);
            request.getRequestDispatcher("/Vue/Professeur/inscription_prof.jsp").forward(request, response);

            tx.commit(); // Commit transaction
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error occurred while processing inscriptions.");
        }
    }
}
