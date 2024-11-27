package org.jee.Controllers.Servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.exception.ConstraintViolationException;
import org.jee.Controllers.ControleurCours;
import org.jee.entity.Personne;
import org.jee.entity.Cours;

import javax.naming.ldap.Control;
import java.io.IOException;
import java.sql.Date;

@WebServlet("/CreeCours")
public class CreationCoursServlet extends HttpServlet {

    Personne profTest = new Personne();


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("TEST CREATION");

        String action = request.getParameter("action");

        Cours cours;

        // Récupération des données du formulaire
        String nom = request.getParameter("nom").toLowerCase();
        String description = request.getParameter("description").toLowerCase();
        Date date_debut = Date.valueOf(request.getParameter("date_debut"));
        Date date_fin = Date.valueOf(request.getParameter("date_fin"));
        //professeur = Personne.getPersonneById(id_professeur);

        // Configuration Hibernate
        SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();

            if ("creation".equals(action)) {
                cours = new Cours();
            }
            else if ("modification".equals(action) || "suppression".equals(action)) {
                cours = ControleurCours.getCoursByID(Integer.valueOf(request.getParameter("idCours")));
            }
            else {
                cours = null;
                session.close();
                factory.close();
                response.setContentType("text/html");
                response.getWriter().println("<h1>Erreur à la création, modification ou suppression de cours</h1>");
            }

            cours.setNomCours(nom);
            cours.setDescription(description);
            cours.setDateDebut(date_debut);
            cours.setDateFin(date_fin);
            cours.setPersonneByIdEnseignant(profTest);

            // Sauvegarde de la personne dans la base de données
            session.persist(cours);

            // Validation de la transaction
            tx.commit();

            // Affichage du résultat
            response.setContentType("text/html");
            response.getWriter().println("<h1>Modification réussie</h1>");


        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
            response.setContentType("text/html");
            response.getWriter().println("<h1>Erreur à la modification</h1>" + e.getMessage());
            response.getWriter().println(nom);

        } finally {
            session.close();
            factory.close();

        }
    }
}