<%@ page import="org.jee.Controllers.Servlets.Admin.ControleurCours" %>
<%@ page import="org.jee.entity.Cours" %>
<%@ page import="org.jee.entity.Personne" %>
<%@ page import="java.util.List" %>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    String actionAdmin = request.getParameter("action");
    Integer id_cours = null;
    if (request.getParameter("id_cours")!= null) {id_cours = Integer.valueOf(request.getParameter("id_cours"));}
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
    List<Cours> coursList = ControleurCours.getCoursList();
%>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Amdinistrateur - Formulaire de création de cours</title>
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

    <% if ("creation".equals(actionAdmin)) {%>
    <h3>Création de Cours</h3>
    <form action="<%= request.getContextPath() %>/CreeCours" method="POST">
        <input type="hidden" name="action" value="creation">

        <label>Nom du cours</label>
        <input type="text" name="nom" required>

        <label>Description du cours</label>
        <input type="text" name="description">

        <label>Date de début</label>
        <input type="date" name="date_debut" required>

        <label>Date de fin</label>
        <input type="date" name="date_fin" required>

        <label>Id du professeur</label>
        <input type="text" name="id_professeur" required>

        <button type="submit">Créer cours</button>
    </form>
    <% } %>
    <% if ("modification".equals(actionAdmin) && id_cours!=null) {
    Cours cours = ControleurCours.getCoursByID(id_cours);
    %>
    <h2>Modification de Cours</h2>
    <form action="<%= request.getContextPath() %>/CreeCours" method="POST">
        <input type="hidden" name="action" value="modification">
        <input type="hidden" name="id_cours" value="<%= id_cours%>">

        <label>Nom du cours</label>
        <input type="text" name="nom" value="<%= cours.getNomCours() %>" required>

        <label>Description du cours</label>
        <input type="text" name="description" value="<%= cours.getDescription()%>">

        <label>Date de début</label>
        <input type="date" name="date_debut" value="<%= cours.getDateDebut() %>" required>

        <label>Date de fin</label>
        <input type="date" name="date_fin" value="<%= cours.getDateFin()%>" required>

        <label>Id du professeur</label>
        <input type="text" name="id_professeur" value="<%= cours.getPersonneByIdEnseignant().getIdPersonne()%>">

        <button type="submit">Modifier cours</button>
        <%if (request.getParameter("error") != null) {%><p class="error-message">Erreur : Le professeur spécifié est introuvable.</p><%}%>
    </form>
    <% } else if ("suppression".equals(actionAdmin)) {%>
    <h2>Suppression de Cours</h2>
    <form action="<%= request.getContextPath() %>/CreeCours" method="POST">
        <input type="hidden" name="action" value="suppression">
        <input type="hidden" name="id_cours" value="<%= id_cours%>">
        <label>Voulez-vous supprimer ce cours ?</label>
        <button type="submit" class="deleteButton">Supprimer</button>
    </form>
    <% } else {}%>
</div>
</body>
</html>