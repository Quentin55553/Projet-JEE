package org.jee.Controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.query.Query;

import org.jee.Data.Personne;
import org.jee.Util.HibernateUtil;

import java.io.IOException;


@WebServlet("/login_")
public class Authentification extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idPersonne = request.getParameter("idPersonne");
        String password = request.getParameter("password");

        // Vérifier les informations d'identification via Hibernate
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query query = session.createQuery("FROM Personne WHERE idPersonne = :idPersonne AND contact = :password");
        query.setParameter("idPersonne", idPersonne);
        query.setParameter("password", password);

        Personne personne = (Personne) query.uniqueResult();
        System.out.println(personne);
        session.close();

        if (personne != null) {
            // Authentification réussie
            HttpSession httpSession = request.getSession();
            httpSession.setAttribute("user", personne);

            // Redirige vers la page d'accueil
            response.sendRedirect("home.jsp");

        } else {
            // Authentification échouée
            response.sendRedirect("login.jsp?error=true");
        }
    }
}
