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

/**
 * Servlet destinée à l'attribution des notes par les Professeurs pour les étudiants.
 */
@WebServlet("/NoteProfServlet")
public class NoteProfServlet extends HttpServlet {

    /**
     * Répond par la liste des étudiants inscrit à la matière indiquée.
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idCoursStr = request.getParameter("idCours");

        if (idCoursStr == null || idCoursStr.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID du cours manquant.");
            return;
        }

        int idCours = Integer.parseInt(idCoursStr);

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();

            Cours cours = session.get(Cours.class, idCours);
            if (cours == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Cours introuvable.");
                return;
            }
            
            ////////////////////////////////////////////////////////////////////////////////////////////////////////////
            // A quoi sert cette partie ?
            ////////////////////////////////////////////////////////////////////////////////////////////////////////////
            Query<Inscription> inscriptionQuery = session.createQuery(
                    "FROM Inscription WHERE cours.idCours = :idCours AND etat = 1", Inscription.class);
            inscriptionQuery.setParameter("idCours", idCours);
            List<Inscription> inscriptions = inscriptionQuery.list();
            request.setAttribute("cours", cours);
            if (inscriptions.isEmpty()) {
                request.getRequestDispatcher("Vue/Professeur/traitementNotes.jsp").forward(request, response);
                return;
            }
            ////////////////////////////////////////////////////////////////////////////////////////////////////////////
            
            List<Personne> students = session.createQuery(
                            "SELECT i.personneByIdEtudiant FROM Inscription i WHERE i.cours.idCours = :idCours AND i.etat = 1", Personne.class)
                    .setParameter("idCours", idCours)
                    .list();

            System.out.println("nom Cours: " + cours.getNomCours());
            for (Personne student : students) {
                System.out.println("Student: " + student.getNom() + " " + student.getPrenom());
            }

            request.setAttribute("students", students);

            request.getRequestDispatcher("Vue/Professeur/traitementNotes.jsp").forward(request, response);

            session.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erreur lors du traitement des inscriptions.");
        }
    }
}
