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

@WebServlet("/MatiereServlet")
public class MatiereServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get the course ID from the request parameter
        int courseId = Integer.parseInt(request.getParameter("idCours"));

        // Retrieve the user (student) from the session
        Personne user = (Personne) request.getSession().getAttribute("user");

        // Open Hibernate session
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();

            // Retrieve the course with the given ID
            Cours cours = session.get(Cours.class, courseId);

            // If the course is not found, redirect to the student menu
            if (cours == null) {
                response.sendRedirect(request.getContextPath() + "/MenuEtudiantServlet");
                return;
            }

            // Query for the results (notes) of the current student for this course
            String hql = "FROM Resultat r WHERE r.coursByIdCours.idCours = :courseId AND r.personneByIdEtudiant.idPersonne = :studentId";
            Query<Resultat> query = session.createQuery(hql, Resultat.class);
            query.setParameter("courseId", courseId);
            query.setParameter("studentId", user.getIdPersonne());
            List<Resultat> resultats = query.list();

            // Set the course and results as request attributes
            request.setAttribute("cours", cours);
            request.setAttribute("resultats", resultats);

            // Commit transaction
            tx.commit();

            // Forward the request to the JSP page
            request.getRequestDispatcher("/Vue/Etudiant/note_Matiere.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while fetching course data.");
        }
    }
}
