package org.jee.Controllers.Servlets.Etudiant;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.jee.entity.Personne;
import org.jee.entity.Resultat;
import org.jee.Util.HibernateUtil;

import java.io.IOException;
import java.util.List;

@WebServlet("/DebutReleveResultatServlet")
public class DebutReleveResultatServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession();
        Personne etudiant = (Personne) httpSession.getAttribute("user");

        // Check if the user is logged in
        if (etudiant == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        List<Resultat> resultats;

        // Use HibernateUtil to get the SessionFactory and open a session
        try (Session hibernateSession = HibernateUtil.getSessionFactory().openSession()) {
            Query<Resultat> query = hibernateSession.createQuery(
                    "FROM Resultat r WHERE r.personneByIdEtudiant.idPersonne = :idEtudiant",
                    Resultat.class
            );
            query.setParameter("idEtudiant", etudiant.getIdPersonne());
            resultats = query.list();
        }

        // Set the results as a request attribute and forward to the JSP
        request.setAttribute("resultats", resultats);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/Vue/Etudiant/notes_Etudiant.jsp");
        dispatcher.forward(request, response);
    }
}
