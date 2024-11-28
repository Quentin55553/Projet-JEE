package org.jee.Controllers.Servlets.Etudiant;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.jee.Util.HibernateUtil;
import org.jee.entity.Cours;
import org.jee.entity.Resultat;
import org.jee.entity.Personne;
import org.jee.entity.Inscription;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Serlvet utilisé pour le menu étudiant, affiche les insriptions aux cours en attente ainsi que les moyennes pour les cours.
 */
@WebServlet("/MenuEtudiantServlet")
public class MenuEtudiantServlet extends HttpServlet {

    /**
     * Génère la liste des cours auquel l'étudiant n'est pas inscrit, l'etat des demandes d'inscriptions
     * et les moyennes sur les notes de l'étudiant par cours.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get the session attribute "user"
        HttpSession httpSession = request.getSession();
        Personne user = (Personne) httpSession.getAttribute("user");

        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Resultat> query = session.createQuery(
                    "FROM Resultat WHERE personneByIdEtudiant.idPersonne = :idPersonne",
                    Resultat.class
            );
            query.setParameter("idPersonne", user.getIdPersonne());

            List<Resultat> resultatList = query.list();

            Map<Integer, Double> courseAverages = new HashMap<>();
            Map<Integer, Integer> courseCounts = new HashMap<>();

            for (Resultat resultat : resultatList) {
                int courseId = resultat.getCoursByIdCours().getIdCours();
                double grade = resultat.getNote();

                courseAverages.put(courseId, courseAverages.getOrDefault(courseId, 0.0) + grade);
                courseCounts.put(courseId, courseCounts.getOrDefault(courseId, 0) + 1);
            }

            for (Map.Entry<Integer, Double> entry : courseAverages.entrySet()) {
                int courseId = entry.getKey();
                double total = entry.getValue();
                int count = courseCounts.get(courseId);

                courseAverages.put(courseId, total / count);
            }

            Query<Inscription> inscriptionQuery = session.createQuery(
                    "FROM Inscription WHERE personneByIdEtudiant.idPersonne = :idPersonne AND (etat = 0 OR etat = 2)",
                    Inscription.class
            );
            inscriptionQuery.setParameter("idPersonne", user.getIdPersonne());

            List<Inscription> inscriptions = inscriptionQuery.list();

            Map<Integer, Cours> courses = new HashMap<>();
            for (Integer courseId : courseAverages.keySet()) {
                Cours cours = session.get(Cours.class, courseId);
                if (cours != null) {
                    courses.put(courseId, cours);
                }
            }

            request.setAttribute("courseAverages", courseAverages);
            request.setAttribute("inscriptions", inscriptions);
            request.setAttribute("courses", courses);

            request.getRequestDispatcher("/Vue/Etudiant/menu_Etudiant.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while processing averages.");
        }
    }
}
