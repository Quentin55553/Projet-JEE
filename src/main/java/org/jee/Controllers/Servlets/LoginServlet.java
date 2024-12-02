package org.jee.Controllers.Servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import org.jee.entity.Personne;
import org.jee.Util.HibernateUtil;

import java.io.IOException;

/**
 * La servlet responsable de la connexion et de la redirection des utilisateurs selon leurs rôles.
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idPersonne = request.getParameter("idPersonne");
        String password = request.getParameter("password");

        // Vérifier ou créer un administrateur par défaut
        createDefaultAdmin();

        // Vérifier les informations d'identification via Hibernate
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Personne> query = session.createQuery(
                    "FROM Personne WHERE idPersonne = :idPersonne AND password = :password", Personne.class);
            query.setParameter("idPersonne", idPersonne);
            query.setParameter("password", password);

            Personne personne = query.uniqueResult();

            if (personne != null) {
                // Authentification réussie
                HttpSession httpSession = request.getSession();
                httpSession.setAttribute("user", personne);

                // Redirige en fonction du rôle
                switch (personne.getRole()) {
                    case 1: // Administrateur
                        response.sendRedirect("Vue/Admin/menu_admin.jsp");
                        break;
                    case 2: // Enseignant
                        response.sendRedirect("Vue/Professeur/menu_professeur.jsp");
                        break;
                    case 3: // Étudiant
                        response.sendRedirect(request.getContextPath() + "/MenuEtudiantServlet");
                        break;
                    default:
                        response.sendRedirect("Vue/login.jsp?error=true");
                        break;
                }
            } else {
                // Authentification échouée
                response.sendRedirect("Vue/login.jsp?error=true");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("Vue/login.jsp?error=true");
        }
    }

    /**
     * Vérifie si un administrateur existe. Si non, en crée un avec des informations par défaut.
     */
    private void createDefaultAdmin() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();


            Query<Personne> query = session.createQuery("FROM Personne WHERE role = 1", Personne.class);
            Personne admin = query.uniqueResult();

            if (admin == null) {

                admin = new Personne();
                admin.setIdPersonne("admin.admin@cy-tech.fr");
                admin.setNom("Admin");
                admin.setPrenom("Admin");
                admin.setDateNaissance(java.sql.Date.valueOf("2000-01-01"));
                admin.setContact("admin.admin@cy-tech.fr");
                admin.setRole(1);
                admin.setPassword("admin");

                session.persist(admin);
                transaction.commit();
            } else {
                transaction.rollback();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
