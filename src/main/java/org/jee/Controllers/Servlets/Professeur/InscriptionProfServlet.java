package org.jee.Controllers.Servlets.Professeur;

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

/**
 * La servlet utilisée pour gérer les inscriptions des étudiants, du point de vue du professeur.
 * Voire page inscription_prof.jsp.
 */
@WebServlet("/InscriptionProf")
public class InscriptionProfServlet extends HttpServlet {

    /**
     * Renvoie la liste des étudiants ayant postuler aux cours de ce professeur.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Personne user = (Personne) request.getSession().getAttribute("user");

        if (user == null) {
            response.getWriter().println("Error: User session not found.");
            return;
        }

        String sessionId = user.getIdPersonne();

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();

            Personne professor = session.get(Personne.class, sessionId);

            if (professor == null) {
                response.getWriter().println("Error: Professor not found.");
                return;
            }

            List<Cours> courses = new ArrayList<>(professor.getCoursByIdPersonne());

            List<Map<String, String>> inscriptionDetails = new ArrayList<>();

            for (Cours course : courses) {
                for (Inscription inscription : course.getInscriptions()) {
                    if (inscription.getEtat() == 0) {
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

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error occurred while processing inscriptions.");
        }
    }
}
