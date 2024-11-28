package org.jee.Controllers.Servlets.Etudiant;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.jee.entity.Cours;
import org.jee.entity.Inscription;
import org.jee.entity.Personne;
import org.jee.Util.HibernateUtil;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;

/**
 * Servlet utilisé lorsque les étudiants postulent via la page inscriptionsCours_Etudiants.jsp à un cours.
 */
@WebServlet("/InscriptionCoursServlet")
public class InscriptionCoursServlet extends HttpServlet {

    /**
     * Créer un nouvel objet Inscription.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession();
        Personne user = (Personne) httpSession.getAttribute("user");

        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String idCours_str = request.getParameter("idCours");
        if (idCours_str == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID nulle");
            return;
        }

        int idCours;
        try {
            idCours = Integer.parseInt(idCours_str);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Format de Id incorrect.");
            return;
        }

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            Cours cours = session.get(Cours.class, idCours);
            if (cours == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Le cours n'existe pas.");
                return;
            }

            Inscription inscription = new Inscription();
            inscription.setDateInscription(Date.valueOf(LocalDate.now()));
            inscription.setEtat(0);
            inscription.setPersonneByIdEtudiant(user);
            inscription.setCours(cours);

            session.persist(inscription);
            transaction.commit();

            response.sendRedirect(request.getContextPath()+"/DemandeInscriptionServlet");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erreur lors de l'inscription.");
        }
    }
}
