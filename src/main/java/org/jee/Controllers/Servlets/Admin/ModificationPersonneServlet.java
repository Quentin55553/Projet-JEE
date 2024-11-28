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
import org.jee.entity.Personne;
import org.jee.entity.Cours;

import java.io.IOException;
import java.sql.Date;

/**
 * La servlet associée à la création/modification/suprression des cours par l'administrateur.
 * Voire \webapp\Vue\Admin\creationCours_Admin.jsp
 */

@WebServlet("/ModifPersonne")
public class ModificationPersonneServlet extends HttpServlet {

    private static final SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();

    @Override
    public void destroy() {
        factory.close();
    }

    /**
     * Créer ou modifie un objet Personne selon les paramètres envoyé par request. Notamment "action" qui définit si il faut
     * le modifier ou le supprimer.
     * Redirige vers \webapp\Vue\Admin\ModificationPersonne_Admin.jsp lorsque l'opération est terminée avec succés.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        // Récupération des données du formulaire
        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String date = request.getParameter("date");
        String idPersonne = request.getParameter("idPersonne");


        if (!("suppression".equals(action)) && (nom == null || prenom == null || date == null || idPersonne == null)){
            response.setContentType("text/html");
            response.getWriter().println("Paramètres manquants.");
            return;
        }

        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();

        Personne personne;

        try {
            if("suppression".equals(action)){
                personne = ControleurPersonne.getPersonneByID(idPersonne);

                if (personne == null) {
                    response.setContentType("text/html");
                    response.getWriter().println("La personne n'existe pas.");
                }
                else {
                    session.remove(personne);
                    tx.commit();
                    //Cours supprimé avec succés
                    response.sendRedirect("Vue/Admin/menu_admin.jsp");
                }
            } else {
                Date dateA = Date.valueOf(date);
                personne = ControleurPersonne.getPersonneByID(idPersonne);

                if (personne == null) {
                    response.setContentType("text/html");
                    response.getWriter().println("<h1>Erreur : Le professeur spécifié est introuvable.</h1>");
                    return;
                }
               if ("modification".equals(action)) {
                   personne = ControleurPersonne.getPersonneByID(idPersonne);

                    if (personne == null) {
                        response.setContentType("text/html");
                        response.getWriter().println("La personne n'existe pas.");
                        return;
                    }
                } else {
                    response.setContentType("text/html");
                    response.getWriter().println("Pas d'action définie.");
                    return;
                }

                personne.setNom(nom);
                personne.setPrenom(prenom);
                personne.setDateNaissance(dateA);

                session.merge(personne);
                tx.commit();
                //cours modifié avec succés
                response.sendRedirect("Vue/Admin/menu_admin.jsp");

            }
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }

            response.setContentType("text/html");
            response.getWriter().println("<h1>Erreur à la modification</h1>" + e.getMessage() + "idPersonne = " + idPersonne);
            response.getWriter().println(nom);

        } finally {
            session.close();

        }
    }
}
