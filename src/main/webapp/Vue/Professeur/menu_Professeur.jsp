<%@ page import="org.jee.entity.Personne" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    // Vérifiez si l'utilisateur est connecté et est un prof
    Personne user = (Personne) session.getAttribute("user");
    if (user == null || user.getRole() != 2) {
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
<div class="header header-professeur">
    <img src="<%= request.getContextPath() %>/Images/cytech.png" class="logo">
    <h2>Professeur</h2>
    <nav>
        <ul>
            <li><a href="<%= request.getContextPath() %>/Vue/Professeur/menu_Professeur.jsp">Accueil</a></li>
            <li><a href="<%= request.getContextPath() %>/InscriptionProf">Inscription Professeur</a></li>
            <li><a href="<%= request.getContextPath() %>/Servlet_Debut_Note_Prof">Saisie de Notes</a></li>
        </ul>
    </nav>
    <form action="<%= request.getContextPath() %>/logout" method="Get" style="display: inline;">
        <button type="submit">Déconnexion</button>
    </form>
</div>

<!-- Contenu principal -->
<div class="main-content">
    <h1 class="no-data">Bienvenue, <%= user.getPrenom() %> <%= user.getNom() %>!</h1>

    <!-- Boutons d'action -->
    <div class="actions no-data">
        <button onclick="window.location.href='<%= request.getContextPath() %>/InscriptionProf'">Inscription Professeur</button>
        <button onclick="window.location.href='<%= request.getContextPath() %>/Servlet_Debut_Note_Prof'" class="margin-left">Saisie de Notes</button>
    </div>

    <!-- Section dynamique selon le choix -->
    <div id="dynamic-content">
        <p class="no-data">Sélectionnez une action dans le menu ou cliquez sur un des boutons ci-dessus pour commencer.</p>
    </div>
</div>
</body>
</html>
