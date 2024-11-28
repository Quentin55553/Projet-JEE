<%@ page import="org.jee.Controllers.Servlets.Admin.ControleurPersonne" %>
<%@ page import="org.jee.entity.Personne" %>
<%@ page import="java.util.List" %>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    String actionAdmin = request.getParameter("action");
    String idPersonne = null;
    if (request.getParameter("idPersonne")!= null) {idPersonne = String.valueOf(request.getParameter("idPersonne"));}
%>
<%
    // Vérifiez si l'utilisateur est connecté et est administrateur
    Personne user = (Personne) session.getAttribute("user");
    if (user == null || user.getRole() != 1) {
        // Redirigez vers la page de connexion si l'utilisateur n'est pas connecté ou n'est pas administrateur
        response.sendRedirect(request.getContextPath()+"/Vue/login.jsp");
        return;
    }
    /**
     * Afficher le tableau des cours
     * */
%>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Amdinistrateur - Formulaire de modification de personne</title>
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

<div class="form-container">
    <% Personne p = ControleurPersonne.getPersonneByID(idPersonne);
        if ("modification".equals(actionAdmin) && idPersonne!=null) {
    %>
    <h2>Formulaire de modification de personne</h2>
    <form action="<%= request.getContextPath() %>/ModifPersonne" method="POST">
        <input type="hidden" name="action" value="modification">
        <input type="hidden" name="idPersonne" value="<%= p.getIdPersonne()%>">

        <label>Nom</label>
        <input type="text" name="nom" value="<%= p.getNom() %>" required>

        <label>Prénom</label>
        <input type="text" name="prenom" value="<%= p.getPrenom() %>" required>

        <label>Date de naissance</label>
        <input type="text" name="date" value="<%= p.getDateNaissance()%>">

        <button type="submit">Modifier cours</button>
    </form>
    <% } else if ("suppression".equals(actionAdmin)) {%>
    <h2>Suppression de Personne</h2>
    <form action="<%= request.getContextPath() %>/ModifPersonne" method="POST">
        <input type="hidden" name="action" value="suppression">
        <input type="hidden" name="idPersonne" value="<%= p.getIdPersonne()%>">
        <label>Voulez-vous supprimer cette personne ?</label>
        <button type="submit" class="deleteButton">Supprimer</button>
    </form>
    <% } else {}%>
</div>
</body>
</html>