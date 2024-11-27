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
import org.jee.entity.Cours;
import org.jee.entity.Inscription;
import org.jee.entity.Personne;

import java.io.IOException;
import java.sql.Date;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.regex.Pattern;

@WebServlet("/InscriptionCoursServlet")
public class InscriptionCoursServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Récupération des données du formulaire
        String prenom = request.getParameter("prenom").toLowerCase();
        String nom = request.getParameter("nom").toLowerCase();
        String motDePasse = request.getParameter("motDePasse"); // Utilisé comme mot de passe
        String dateNaissanceStr = request.getParameter("dateNaissance"); // Assurez-vous qu'il est dans le bon format (yyyy-MM-dd)
        String[] coursIds = request.getParameterValues("coursIds[]");
        String contact = request.getParameter("contact");

        Date dateNaissance = Date.valueOf(dateNaissanceStr); // Convertir si nécessaire

        // Génération de l'email
        String emailBase = nom + "." + prenom + "@cy-tech.fr";

        // Configuration Hibernate
        SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();

            // Génération d'un email unique
            String email = generateUniqueEmail(session, emailBase);

            // Création de l'objet Personne
            Personne personne = new Personne();
            personne.setNom(nom);
            personne.setPrenom(prenom);
            personne.setDateNaissance(dateNaissance);
            personne.setPassword(motDePasse);
            personne.setRole(1); // Vous pouvez ajuster le rôle selon vos besoins
            personne.setIdPersonne(email); // Utilisation de l'email comme identifiant
            personne.setContact(contact);

            // Sauvegarde de la personne dans la base de données
            session.persist(personne);

            if (coursIds != null && coursIds.length > 0) {
                // Création d'une nouvelle inscription
                Inscription inscription = new Inscription();
                inscription.setPersonneByIdEtudiant(personne);
                inscription.setDateInscription(new Date(System.currentTimeMillis())); // Date actuelle
                inscription.setEtat(1); // Définissez un état approprié

                // Récupération et association des cours à l'inscription
                Collection<Cours> coursCollection = new ArrayList<>();
                for (String coursId : coursIds) {
                    // Récupération du cours depuis la base de données
                    Cours cours = session.get(Cours.class, Integer.parseInt(coursId));
                    if (cours != null) {
                        coursCollection.add(cours);
                    } else {
                        System.out.println("Cours introuvable pour l'ID : " + coursId);
                    }
                }

                // Ajout des cours à l'inscription
                //TODO: Modifier la ligne suivante :
                //inscription.setCours(coursCollection);
                // Ajout de l'ID de la personne pour la table de jointure
                inscription.setPersonneByIdEtudiant(personne); // Ceci associera la personne à chaque inscription-cours.

                // Sauvegarde de l'inscription dans la base de données
                session.persist(inscription);
            }



            // Validation de la transaction
            tx.commit();

            // Affichage du résultat
            response.setContentType("text/html");
            response.getWriter().println("<h1>Inscription réussie</h1>");
            response.getWriter().println("<h1> Cours choisies"+ Arrays.toString(coursIds) +"</h1>");
            response.getWriter().println("<p>Email généré : " + email + "</p>");
            response.getWriter().println("<p>Mot de passe : " + motDePasse + "</p>");

        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
            response.getWriter().println("<h1>Erreur lors de l'inscription</h1>");
        } finally {
            session.close();
            factory.close();
        }
    }

    // Méthode pour générer un email unique
    private String generateUniqueEmail(Session session, String baseEmail) {
        String email = normalizeEmail(baseEmail.toLowerCase());
        int counter = 1;

        // Vérification de l'unicité de l'email
        while (session.createQuery("SELECT 1 FROM Personne WHERE idPersonne = :email")
                .setParameter("email", email)
                .uniqueResult() != null) {
            email = normalizeEmail(baseEmail.replace("@cy-tech.fr", "").toLowerCase()) + counter + "@cy-tech.fr";
            counter++;
        }

        return email;
    }

    private String normalizeEmail(String email) {
        String normalized = Normalizer.normalize(email, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(normalized).replaceAll("");
    }
}
