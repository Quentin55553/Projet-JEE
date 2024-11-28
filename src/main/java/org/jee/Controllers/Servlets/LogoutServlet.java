package org.jee.Controllers.Servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Servlet responsable de la déconnexion des utilisateurs.
 */
@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    /**
     * Déconnecte l'utilisateur et le redirige vers la page de connexion.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate(); // Déconnecte l'utilisateur
        }
        response.sendRedirect(request.getContextPath()+"/Vue/login.jsp");
    }
}
