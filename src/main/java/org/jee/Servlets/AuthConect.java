import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/auth")
public class AuthConect extends HttpServlet {

    // Affiche la page de connexion
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("logout".equals(action)) {
            // Déconnexion de l'utilisateur
            request.getSession().invalidate();
            response.sendRedirect("login.jsp");
        } else {
            // Affiche la page de connexion
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }

    // Traite la connexion de l'utilisateur
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        Personne user = null;
        user = user.Register(email, password);
        int temp = user.ExistWhich();
        if (temp == 1) {
            // redirige un etudiant
            request.getSession().setAttribute("user", user);
            response.sendRedirect("PageEtudiant.jsp");
        } else if (temp == 2) {
            // redirige un Proffeseur
            request.getSession().setAttribute("user", user);
            response.sendRedirect("PageProffesseur.jsp");
        } else if (temp == 3) {
            // redirige un admin
            request.getSession().setAttribute("user", user);
            response.sendRedirect("PageAdmin.jsp");
        } else  {
            request.setAttribute("errorMessage", "Email ou mot de passe incorrect");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}
