package org.jee.Controllers.Servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.query.Query;

import org.jee.entity.Personne;
import org.jee.Util.HibernateUtil;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idPersonne = request.getParameter("idPersonne");
        String password = request.getParameter("password");

        // Vérifier les informations d'identification via Hibernate
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query query = session.createQuery("FROM Personne WHERE idPersonne = :idPersonne AND password = :password");
        query.setParameter("idPersonne", idPersonne);
        query.setParameter("password", password);

        Personne personne = (Personne) query.uniqueResult();
        session.close();

        if (personne != null) {
            // Authentification réussie
            HttpSession httpSession = request.getSession();
            httpSession.setAttribute("user", personne);

            // Redirige en fonction du rôle
            switch (personne.getRole()) {
                case 1: // Rôle étudiant
                    response.sendRedirect("admin/home.jsp");
                    break;
                case 2: // Rôle enseignant
                    response.sendRedirect("teacher/home.jsp");
                    break;
                case 3: // Rôle administrateur
                    response.sendRedirect("student/home.jsp");
                    break;
                default:
                    // Rôle inconnu
                    response.sendRedirect("Vue/login.jsp?error=true");
                    break;
            }
        } else {
            // Authentification échouée
            response.sendRedirect("Vue/login.jsp?error=true");
        }
    }
}
