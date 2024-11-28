package org.jee.Controllers.Servlets.Admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.jee.entity.Personne;

import java.io.IOException;
import java.util.List;

@WebServlet("/AffichagePersonne")
public class AffichagePersonneServlet extends HttpServlet {
    private static final SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();

    @Override
    public void destroy() {
        factory.close();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String typePersonne = request.getParameter("typePersonne");
        String form_filtre = request.getParameter("form_filtre");

        String id = request.getParameter("filtre_id");
        String nom = request.getParameter("filtre_nom");
        String prenom = request.getParameter("filtre_prenom");
        String contact = request.getParameter("filtre_contact");

        try {
            if(form_filtre==null) {
                if ("etudiant".equals(typePersonne)) {
                    List<Personne> liste = ControleurPersonne.getPersonnesListByRole(3);
                    request.setAttribute("liste", liste);
                } else if ("professeur".equals(typePersonne)) {
                    List<Personne> liste = ControleurPersonne.getPersonnesListByRole(2);
                    request.setAttribute("liste", liste);
                }
            } else if ("true".equals(form_filtre)) {

                int role = 0;
                if("etudiant".equals(typePersonne)) {
                    role = 3;
                }
                else if("professeur".equals(typePersonne)) {
                    role = 2;
                }
                else {throw new ServletException("role inconnu" + typePersonne);}

                List<Personne> liste = ControleurPersonne.getPersonnesListByFilter(role, id, nom, prenom, contact);

                request.setAttribute("liste", liste);
            }
            request.setAttribute("typePersonne", typePersonne);
            request.getRequestDispatcher("/Vue/Admin/AffichagePersonne_Admin.jsp").forward(request, response);
        }catch (Exception e) {
            response.setContentType("text/html");
            response.getWriter().println(e.getMessage());
        }

    }
}
