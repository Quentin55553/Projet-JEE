package org.jee.Servlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;


@WebServlet(name = "uneServler", value = "/une-servlet")
public class UneServletANommer {
    public void init() {
        // Code d'initialisation de la servlet
    }


    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        // Code pour doGet
    }


    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        // Code pour doPost
    }


    public void destroy() {
    }
}
