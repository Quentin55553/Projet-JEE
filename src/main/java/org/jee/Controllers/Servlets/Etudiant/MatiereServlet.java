package org.jee.Controllers.Servlets.Etudiant;

import org.jee.entity.Cours;
import org.jee.entity.Personne;
import org.jee.entity.Resultat;
import org.jee.Util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

/**
 * Serlvet utilisé pour afficher les détails des notes d'une matière par l'étudiant depuis la page menu_Etudiant.jsp
 */
@WebServlet("/MatiereServlet")
public class MatiereServlet extends HttpServlet {

    /**
     * Etablie la liste des resultats d'un étudiant sur le Cours spécifié et redirige l'étudiant vers les détails des
     * notes du cours.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int courseId = Integer.parseInt(request.getParameter("idCours"));

        Personne user = (Personne) request.getSession().getAttribute("user");

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();

            Cours cours = session.get(Cours.class, courseId);

            if (cours == null) {
                response.sendRedirect(request.getContextPath() + "/MenuEtudiantServlet");
                return;
            }

            String hql = "FROM Resultat r WHERE r.coursByIdCours.idCours = :courseId AND r.personneByIdEtudiant.idPersonne = :studentId";
            Query<Resultat> query = session.createQuery(hql, Resultat.class);
            query.setParameter("courseId", courseId);
            query.setParameter("studentId", user.getIdPersonne());
            List<Resultat> resultats = query.list();

            request.setAttribute("cours", cours);
            request.setAttribute("resultats", resultats);

            tx.commit();

            request.getRequestDispatcher("/Vue/Etudiant/note_Matiere.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while fetching course data.");
        }
    }
}
