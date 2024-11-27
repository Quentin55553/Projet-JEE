package org.jee.Controllers.Servlets.Professeur;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.jee.entity.Cours;
import org.jee.Util.HibernateUtil;
import org.jee.entity.Personne;

import java.io.IOException;
import java.util.List;

@WebServlet("/Servlet_Debut_Note_Prof")
public class Servlet_Debut_Note_Prof extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Récupérer l'ID de l'utilisateur connecté depuis la session
        HttpSession httpSession = request.getSession();
        httpSession.setAttribute("id", "2.1@cy-tech.fr");
        String idEnseignant = (String) httpSession.getAttribute("id");

        if (idEnseignant == null) {
            response.sendRedirect("login.jsp"); // Redirection si pas connecté
            return;
        }

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Démarrer une transaction pour une gestion sûre
            session.beginTransaction();

            // Requête pour récupérer l'enseignant (Personne)
            Query<Personne> query2 = session.createQuery("FROM Personne WHERE idPersonne = :idEnseignant", Personne.class);
            query2.setParameter("idEnseignant", idEnseignant);

            Personne enseignant = query2.uniqueResult();
            if (enseignant == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Enseignant non trouvé.");
                return;
            }

            // Requête pour récupérer les cours enseignés par cet enseignant
            Query<Cours> query = session.createQuery("FROM Cours WHERE personneByIdEnseignant = :Enseignant", Cours.class);
            query.setParameter("Enseignant", enseignant);

            List<Cours> coursList = query.list();

            // Transférer la liste des cours à la JSP
            request.setAttribute("coursList", coursList);
            request.getRequestDispatcher("Vue/Professeur/saisieNotes_Professeur.jsp").forward(request, response);

            // Commit transaction
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erreur lors du traitement des cours.");
        }
    }
}

