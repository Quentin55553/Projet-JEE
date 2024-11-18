package org.jee.Controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jee.Data.Cours;

import java.io.IOException;
import java.util.ArrayList;


public class ControleurCours {
    private ArrayList<Cours> coursList = new ArrayList<>();


    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("list".equals(action)) {
            request.setAttribute("coursList", coursList);
            request.getRequestDispatcher("/cours.jsp").forward(request, response);

        } else if ("delete".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            coursList.removeIf(cours -> cours.getId() == id);
            response.sendRedirect("cours?action=list");
        }
    }


    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");
        String nomCours = request.getParameter("nomCours");
        String description = request.getParameter("description");
        int idEnseignant = Integer.parseInt(request.getParameter("idEnseignant"));

        if (idParam == null || idParam.isEmpty()) { // Ajouter
            Cours cours = new Cours(coursList.size() + 1, idEnseignant, nomCours, description);
            coursList.add(cours);

        } else { // Modifier
            int id = Integer.parseInt(idParam);
            Cours cours = coursList.stream().filter(c -> c.getId() == id).findFirst().orElse(null);

            if (cours != null) {
                cours.setNom(nomCours);
                cours.setDescription(description);
                cours.setIdProfesseur(idEnseignant);
            }
        }

        response.sendRedirect("cours?action=list");
    }












    /*
    private List<Cours> listeCours;


    public void ajouterCours(Cours cours) {
        if (listeCours == null) {
            listeCours = new ArrayList<Cours>();
        }

        listeCours.add(cours);
    }


    public void supprimerCours(Cours cours) {
        if (listeCours != null) {
            // Sûrement la méthode equals() à redéfinir dans Cours
            listeCours.remove(cours);
        }
    }


    public void afficherCours() {
        if (listeCours != null) {
            for (Cours cours : listeCours) {
                System.out.println(cours);
            }
        }
    }
     */
}
