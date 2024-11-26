<%@ page import="org.jee.Controllers.ControleurCours" %>
<%@ page import="org.jee.entity.Cours" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    String actionAdmin = request.getParameter("action");
    Integer idCours = null;
    if (request.getParameter("id_cours")!= null) {idCours = Integer.valueOf(request.getParameter("id_cours"));}
%>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Amdinistrateur - Formulaire de création de cours</title>
    <link rel="stylesheet" href="../style.css">
</head>
<body>
<!-- Header avec menu de navigation -->
<div class="header">
    <img src="<%= request.getContextPath() %>/Images/cytech.png" class="logo">
    <h2>Menu administrateur</h2>
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

<div class="form-container">

    <% if ("creation".equals(actionAdmin)) {%>
    <h2>Création de Cours</h2>
    <form action="../../CreeCours" method="POST">
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
    <% if ("modification".equals(actionAdmin) && idCours!=null) {
    Cours cours = ControleurCours.getCoursByID(idCours);
    %>
    <h2>Modification de Cours</h2>
    <form action="../../CreeCours" method="POST">
        <input type="hidden" name="action" value="modification">
        <input type="hidden" name="idcours" value="<%= idCours%>">

        <label>Nom du cours</label>
        <input type="text" name="nom" value="<%= cours.getNomCours() %>" required>

        <label>Description du cours</label>
        <input type="text" name="description" value="<%= cours.getDescription()%>">

        <label>Date de début</label>
        <input type="date" name="date_debut" value="<%= cours.getDateDebut() %>" required>

        <label>Date de fin</label>
        <input type="date" name="date_fin" value="<%= cours.getDateFin()%>" required>

        <label>Id du professeur</label>
        <input type="text" name="id_professeur" value="<%= cours.getPersonneByIdEnseignant()%>">

        <button type="submit">Modifier cours</button>
    </form>
    <% } else if ("suppression".equals(actionAdmin)) {%>
    <h2>Suppression de Cours</h2>
    <form action="../../CreeCours" method="POST">
        <input type="hidden" name="action" value="suppression">
        <label>Voulez-vous supprimer ce cours ?</label>
        <button type="submit" class="deleteButton">Supprimer</button>
    </form>
    <% } else {}%>
</div>
</body>
</html>