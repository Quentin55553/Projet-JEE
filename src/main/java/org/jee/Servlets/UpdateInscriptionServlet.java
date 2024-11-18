package org.jee.Servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.jee.entity.Inscription;

import java.io.IOException;

@WebServlet("/admin/UpdateInscriptionServlet")
public class UpdateInscriptionServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Récupération de l'ID de l'inscription depuis le formulaire
        String idInscriptionStr = request.getParameter("idInscription");
        if (idInscriptionStr == null || idInscriptionStr.isEmpty()) {
            response.sendRedirect("manageInscription.jsp");
            return;
        }

        int idInscription = Integer.parseInt(idInscriptionStr);

        // Configuration Hibernate
        SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();

            // Récupération de l'inscription
            Inscription inscription = session.get(Inscription.class, idInscription);
            if (inscription != null) {
                // Mise à jour de l'état
                inscription.setEtat(2);
                session.update(inscription);
            }

            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
            factory.close();
        }

        // Redirection vers la page de gestion des inscriptions
        response.sendRedirect("manageInscription.jsp");
    }
}
