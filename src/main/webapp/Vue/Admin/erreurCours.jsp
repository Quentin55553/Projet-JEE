<%@ page import="org.jee.entity.Personne" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    // Vérifiez si l'utilisateur est connecté et est administrateur
    Personne user = (Personne) session.getAttribute("user");
    if (user == null || user.getRole() != 1) {
        // Redirigez vers la page de connexion si l'utilisateur n'est pas connecté ou n'est pas administrateur
        response.sendRedirect(request.getContextPath()+"/Vue/login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Tableau des Cours</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/Vue/style.css">
</head>

<body>
<!-- Header avec menu de navigation -->
<div class="header header-admin">
    <img src="<%= request.getContextPath() %>/Images/cytech.png" class="logo">
    <h2>Administrateur</h2>
    <nav>
        <ul>
            <li><a href="<%= request.getContextPath() %>/Vue/Admin/menu_admin.jsp">Accueil</a></li>
            <li><a href="<%= request.getContextPath() %>/Vue/Admin/inscriptionsEtudiants_Admin.jsp">Créer profil étudiant</a></li>
            <li><a href="<%= request.getContextPath() %>/Vue/Admin/inscriptionsProfesseurs_Admin.jsp">Créer profil professeur</a></li>
            <li><a href="<%= request.getContextPath() %>/Vue/Admin/gestionCours_Admin.jsp">Gestion de cours</a></li>
            <li><a href="<%= request.getContextPath() %>/Vue/Admin/AffichagePersonne_Admin.jsp">Affichage de professeur et étudiant</a></li>
        </ul>
    </nav>
    <form action="<%= request.getContextPath() %>/logout" method="Get" style="display: inline;">
        <button type="submit">Déconnexion</button>
    </form>
</div>

<br><br>
<div class="main-content">
    <h1 class="no-data">Le Professeur n'existe pas</h1>
</div>
</body>
</html>