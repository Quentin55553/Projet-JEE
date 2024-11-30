package org.jee.Controllers.Servlets.Etudiant;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.jee.entity.Cours;
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

        if (etudiant == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        List<Resultat> resultats;

        try (Session hibernateSession = HibernateUtil.getSessionFactory().openSession()) {
            Query<Resultat> query = hibernateSession.createQuery(
                    "FROM Resultat r WHERE r.personneByIdEtudiant.idPersonne = :idEtudiant",
                    Resultat.class
            );
            query.setParameter("idEtudiant", etudiant.getIdPersonne());
            resultats = query.list();
        }

        HttpSession session = request.getSession();
        session.setAttribute("resultats", resultats);
        response.sendRedirect(request.getContextPath() + "/Vue/Etudiant/notes_Etudiant.jsp");
    }
}
