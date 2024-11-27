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
    <link rel="stylesheet" href="../style.css">
</head>

<body>
<!-- Header avec menu de navigation -->
<div class="header header-admin">
    <img src="<%= request.getContextPath() %>/Images/cytech.png" class="logo">
    <h2>Administrateur</h2>
    <nav>
        <ul>
            <li><a href="menu_admin.jsp">Accueil</a></li>
            <li><a href="inscriptionsEtudiants_Admin.jsp">Créer profil étudiant</a></li>
            <li><a href="inscriptionsProfesseurs_Admin.jsp">Créer profil professeur</a></li>
            <li><a href="gestionCours_Admin.jsp">Gestion de cours</a></li>
            <li><a href="manageInscription.jsp">Gestion des inscriptions</a></li>
            <li><a href="creationCours_Admin.jsp">Créer profil professeur</a></li>

        </ul>
    </nav>
    <form action="../../logout" method="Get" style="display: inline;">
        <button type="submit">Déconnexion</button>
    </form>
</div>

<!-- Contenu principal -->
<div class="main-content">
    <h1>Bienvenue, <%= user.getPrenom() %> <%= user.getNom() %>!</h1>

    <!-- Boutons d'action -->
    <div class="actions">
        <button onclick="window.location.href='inscriptionsEtudiants_Admin.jsp'">Créer profil étudiant</button>
        <button onclick="window.location.href='inscriptionsProfesseurs_Admin.jsp'">Créer profil professeur</button>
        <button onclick="window.location.href='gestionCours_Admin.jsp'">Accéder à la gestion de cours</button>
        <button onclick="window.location.href='manageInscription.jsp'">Gestion des inscriptions</button>
        <button onclick="window.location.href='creationCours_Admin.jsp'">Créer profil professeur</button>
    </div>

    <!-- Section dynamique selon le choix -->
    <div id="dynamic-content">
        <p>Sélectionnez une action dans le menu ou cliquez sur un des boutons ci-dessus pour commencer.</p>
    </div>
</div>
</body>
</html>
