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

@WebServlet("/CreeCours")
public class CreationCoursServlet extends HttpServlet {

    private static final SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();

    @Override
    public void destroy() {
        factory.close();
    }

    /**
     * Créer ou modifie un objet Cours selon les paramètres envoyé par request. Notamment "action" qui définit si il faut
     * créer un cours ou en selectionner un pour le modifier ou le supprimer.
     * Redirige vers \webapp\Vue\Admin\gestionCours_Admin.jsp lorsque l'opération est terminée avec succés.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        // Récupération des données du formulaire
        String nom = request.getParameter("nom");
        String description = request.getParameter("description");
        String date_debut_str = request.getParameter("date_debut");
        String date_fin_str = request.getParameter("date_fin");
        String id_professeur = request.getParameter("id_professeur");
        String id_cours_str = request.getParameter("id_cours");


        if (!("suppression".equals(action)) && (nom == null || description == null || date_debut_str == null || date_fin_str == null || id_professeur == null)){
            response.setContentType("text/html");
            response.getWriter().println("Paramètres manquants.");
            return;
        }

        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();

        Cours cours;

        Integer id_cours = null;
        if (id_cours_str != null) {
            id_cours = Integer.parseInt(id_cours_str);
        }

        try {
            if("suppression".equals(action)){
                cours = ControleurCours.getCoursByID(id_cours);

                if (cours == null) {
                        response.setContentType("text/html");
                        response.getWriter().println("Le cours n'existe pas.");
                }
                else {
                        session.remove(cours);
                        tx.commit();
                        //Cours supprimé avec succés
                        response.sendRedirect("Vue/Admin/gestionCours_Admin.jsp");
                }
            } else {
                Date date_debut = Date.valueOf(date_debut_str);
                Date date_fin = Date.valueOf(date_fin_str);
                Personne professeur = ControleurPersonne.getPersonneByID(id_professeur);

                if (professeur == null) {
                    response.setContentType("text/html");
                    response.sendRedirect("Vue/Admin/erreurCours.jsp");
                    return;
                }
                if ("creation".equals(action)) {
                    cours = new Cours();
                } else if ("modification".equals(action)) {
                    cours = ControleurCours.getCoursByID(id_cours);

                    if (cours == null) {
                        response.setContentType("text/html");
                        response.getWriter().println("Le cours n'existe pas.");
                        return;
                    }
                } else {
                    response.setContentType("text/html");
                    response.getWriter().println("Pas d'action définie.");
                    return;
                }

                cours.setNomCours(nom);
                cours.setDescription(description);
                cours.setDateDebut(date_debut);
                cours.setDateFin(date_fin);
                cours.setPersonneByIdEnseignant(professeur);

                session.merge(cours);
                tx.commit();
                //cours modifié avec succés
                response.sendRedirect("Vue/Admin/gestionCours_Admin.jsp");

            }
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }

            response.setContentType("text/html");
            response.getWriter().println("<h1>Erreur à la modification</h1>" + e.getMessage() + "id_cours = " + id_cours);
            response.getWriter().println(nom);

        } finally {
            session.close();

        }
    }
}
