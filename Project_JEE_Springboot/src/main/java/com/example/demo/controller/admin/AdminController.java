package com.example.demo.controller.admin;

import com.example.demo.entity.Personne;
import com.example.demo.repository.PersonneRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/menu")
    public String showAdminMenu(HttpSession session, HttpServletResponse response) throws IOException {
        // Vérifier si l'utilisateur est connecté et est administrateur
        Personne user = (Personne) session.getAttribute("user");
        if (user == null || user.getRole() != 1) {
            // Redirection vers la page de connexion si l'utilisateur n'est pas connecté ou n'est pas administrateur
            response.sendRedirect("/login");
            return null;
        }
        return "Admin/menu_admin"; // Vue JSP (WEB-INF/views/Admin/menu_admin.jsp)
    }
    @GetMapping("/gestionCours")
    public String gestionCours(Model model) {
        // Ajoutez ici toute donnée nécessaire pour la vue, si besoin
        return "Admin/gestionCours_Admin"; // Correspond à WEB-INF/views/Admin/gestionCours_Admin.jsp
    }
}
