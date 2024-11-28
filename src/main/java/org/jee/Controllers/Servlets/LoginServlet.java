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

/**
 * La servlet responsable de la connexion et de la redirection des utilisateurs selon leurs rôles.
 */

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    /**
     * Verifie que l'utilisateur existe dans la base et le redirige selon son rôle vers les menu Etudiant, Professeur ou 
     * Administrateur.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idPersonne = request.getParameter("idPersonne");
        String password = request.getParameter("password");
        System.out.println(idPersonne);
        System.out.println(password);


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
                    response.sendRedirect("Vue/Admin/menu_admin.jsp");
                    break;
                case 2: // Rôle enseignant
                    System.out.println(personne.getIdPersonne());
                    response.sendRedirect("Vue/Professeur/menu_Professeur.jsp");
                    break;
                case 3: // Rôle administrateur
                    response.sendRedirect(request.getContextPath()+"/MenuEtudiantServlet");
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
