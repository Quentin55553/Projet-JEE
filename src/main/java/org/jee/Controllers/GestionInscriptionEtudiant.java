package org.jee.Controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.jee.Data.Etudiant;

import java.io.IOException;
import java.util.ArrayList;


@WebServlet(name = "gestionInscriptionEtudiant", value = "/gestion-inscription-etudiant")
public class GestionInscriptionEtudiant extends HttpServlet {
    private ArrayList<Etudiant> etudiants = new ArrayList<>();


    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String action = request.getParameter("action");

        if ("list".equals(action)) {
            request.setAttribute("etudiants", etudiants);
            request.getRequestDispatcher("/etudiants.jsp").forward(request, response);

        } else if ("delete".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            etudiants.removeIf(etudiant -> etudiant.getId() == id);
            response.sendRedirect("etudiant?action=list");

        } else if ("details".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            Etudiant etudiant = etudiants.stream().filter(e -> e.getId() == id).findFirst().orElse(null);
            request.setAttribute("etudiant", etudiant);
            request.getRequestDispatcher("/etudiantDetails.jsp").forward(request, response);
        }
    }


    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        // Code pour doPost
    }
}
