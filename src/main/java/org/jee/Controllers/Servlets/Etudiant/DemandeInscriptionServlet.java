package org.jee.Controllers.Servlets.Etudiant;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.jee.Util.HibernateUtil;
import org.jee.entity.Cours;
import org.jee.entity.Inscription;
import org.jee.entity.Personne;

import java.io.IOException;
import java.util.List;

/**
 * Servlet permet aux étudiant de postuler aux cours auquels ils ne sont pas inscrits.
 */
@WebServlet("/DemandeInscriptionServlet")
public class DemandeInscriptionServlet extends HttpServlet {
    /**
     * Permet de génerer la liste des cours auquel un étudiant n'est pas inscrit.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Récupérer l'utilisateur (Personne) connecté depuis la session
        HttpSession sessionHttp = request.getSession();
        Personne user = (Personne) sessionHttp.getAttribute("user");

        if (user == null) {
            response.sendRedirect("login.jsp"); // Redirige si pas connecté
            return;
        }

        try (Session hibernateSession = HibernateUtil.getSessionFactory().openSession()) {
            // Récupérer toutes les inscriptions de l'étudiant connecté
            Query<Inscription> inscriptionQuery = hibernateSession.createQuery(
                    "FROM Inscription WHERE personneByIdEtudiant.idPersonne = :idEtudiant",
                    Inscription.class
            );
            inscriptionQuery.setParameter("idEtudiant", user.getIdPersonne());

            List<Inscription> inscriptionsEtudiant = inscriptionQuery.list();

            // Extraire les cours des inscriptions pour créer la première collection
            List<Cours> coursInscrits = inscriptionsEtudiant.stream()
                    .map(Inscription::getCours)
                    .toList();

            // Récupérer tous les cours
            Query<Cours> coursQuery = hibernateSession.createQuery("FROM Cours", Cours.class);
            List<Cours> tousLesCours = coursQuery.list();

            // Créer une deuxième collection des cours auxquels l'étudiant n'est pas inscrit
            List<Cours> coursNonInscrits = tousLesCours.stream()
                    .filter(cours -> !coursInscrits.contains(cours))
                    .toList();

            // Ajouter la liste des cours non inscrits à la requête
            request.setAttribute("coursNonInscrits", coursNonInscrits);

            // Forward vers la JSP
            request.getRequestDispatcher("Vue/Etudiant/inscriptionsCours_Etudiant.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erreur lors du traitement des inscriptions.");
        }
    }
}

