package org.jee.Controllers.Servlets.Admin;

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
import org.jee.entity.Personne;

import java.io.IOException;
import java.sql.Date;


/**
 * La servlet dédiée à la création d'un objet Personne.
 */
@WebServlet("/CreeEP")
public class CreeEtuProfServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Récupération des données du formulaire
        String prenom = request.getParameter("prenom").toLowerCase();
        String nom = request.getParameter("nom").toLowerCase();
        String motDePasse = request.getParameter("password");
        String dateNaissance = request.getParameter("date_naissance"); // Format yyyy-MM-dd
        String contact = request.getParameter("contact");
        String role = request.getParameter("role");
        String id_personne = nom.replaceAll(" ", "_") + "." + prenom.replaceAll(" ", "_") + "@cy-tech.fr";

        // Configuration Hibernate
        SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();

            // Vérification si une personne avec le même id_personne existe
            if (session.get(Personne.class, id_personne) != null) {
                throw new Exception("L'identifiant " + id_personne + " existe déjà.");
            }

            // Création de l'objet Personne
            Personne personne = new Personne();
            personne.setNom(nom);
            personne.setPrenom(prenom);
            personne.setDateNaissance(Date.valueOf(dateNaissance));
            personne.setPassword(motDePasse);
            personne.setRole(Integer.parseInt(role)); 
            personne.setIdPersonne(id_personne);
            personne.setContact(contact);

            // Sauvegarde de la personne dans la base de données
            session.persist(personne);

            // Validation de la transaction
            tx.commit();

            response.setContentType("text/html");
            response.sendRedirect("Vue/Admin/menu_admin.jsp");

        } catch (ConstraintViolationException e) {
            if (tx != null) {
                tx.rollback();
            }
            response.sendRedirect("Vue/Admin/adminErreur.jsp");
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
            response.sendRedirect("Vue/Admin/adminErreur.jsp");
        } finally {
            session.close();
            factory.close();
        }
    }
}
