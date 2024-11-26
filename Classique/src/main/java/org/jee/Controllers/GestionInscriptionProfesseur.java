package org.jee.Controllers;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;


@WebServlet(name = "gestionInscriptionProfesseur", value = "/gestion-inscription-professeur")
public class GestionInscriptionProfesseur extends HttpServlet {
    public void init() {

    }


    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        // Code pour doPost
    }
}
