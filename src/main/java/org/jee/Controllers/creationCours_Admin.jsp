<%@ page import="org.jee.Controllers.ControleurCours" %>
<%@ page import="org.jee.entity.Cours" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    String actionAdmin = request.getParameter("action");
    Integer id_cours = null;
    if (request.getParameter("id_cours") != null) {
        try {
            id_cours = Integer.valueOf(request.getParameter("id_cours"));
        } catch (NumberFormatException e) {
            id_cours = null;
        }
    }
    Cours cours = null;
    if (id_cours != null && "modification".equals(actionAdmin)) {
        cours = ControleurCours.getCoursByID(id_cours);
    }
%>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Amdinistrateur - Formulaire de création de cours</title>
    <link rel="stylesheet" href="../style.css">
</head>
<body>
<div class="header">
    <h2>Administrateur</h2>
    <button>Déconnexion</button>
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
    <% } else if ("modification".equals(actionAdmin) && cours!=null) {
    %>
    <h2>Modification de Cours</h2>
    <form action="../../CreeCours" method="POST">
        <input type="hidden" name="action" value="modification">
        <input type="hidden" name="id_cours" value="<%= id_cours %>">

        <label>Nom du cours</label>
        <input type="text" name="nom" value="<%= cours.getNomCours() %>" required>

        <label>Description du cours</label>
        <input type="text" name="description" value="<%= cours.getDescription() %>">

        <label>Date de début</label>
        <input type="date" name="date_debut" value="<%= cours.getDateDebut() %>" required>

        <label>Date de fin</label>
        <input type="date" name="date_fin" value="<%= cours.getDateFin() %>" required>

        <label>Id du professeur</label>
        <input type="text" name="id_professeur" value="<%= cours.getPersonneByIdEnseignant().getIdPersonne() %>">

        <button type="submit">Modifier cours</button>
    </form>
    <% } else if ("suppression".equals(actionAdmin)) {%>
    <h2>Suppression de Cours</h2>
    <form action="../../CreeCours" method="POST">
        <input type="hidden" name="action" value="suppression">
        <input type="hidden" name="id_cours" value="<%= id_cours %>">
        <label>Voulez-vous supprimer ce cours ?</label>
        <button type="submit" class="deleteButton">Supprimer</button>
    </form>
    <% } else {}%>
</div>
</body>
</html>