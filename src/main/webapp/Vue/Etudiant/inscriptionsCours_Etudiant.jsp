<%@ page import="org.jee.entity.Cours" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cours disponibles pour inscription</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        table, th, td {
            border: 1px solid #ddd;
        }
        th, td {
            padding: 8px;
            text-align: left;
        }
        th {
            background-color: #f4f4f4;
        }
        tr:hover {
            background-color: #f1f1f1;
        }
        .button {
            background-color: #4CAF50;
            color: white;
            padding: 10px 20px;
            text-decoration: none;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }
        .button:hover {
            background-color: #45a049;
        }
    </style>
</head>
<body>
<h1>Cours disponibles pour inscription</h1>

<%
    // Récupérer la liste des cours disponibles depuis la requête
    List<Cours> coursNonInscrits = (List<Cours>) request.getAttribute("coursNonInscrits");

    if (coursNonInscrits == null || coursNonInscrits.isEmpty()) {
%>
<p>Aucun cours disponible pour inscription.</p>
<%
} else {
%>
<table>
    <thead>
    <tr>
        <th>Nom du cours</th>
        <th>Description</th>
        <th>Date de début</th>
        <th>Date de fin</th>
        <th>Professeur</th>
        <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <%
        for (Cours cours : coursNonInscrits) {
    %>
    <tr>
        <td><%= cours.getNomCours() %></td>
        <td><%= cours.getDescription() %></td>
        <td><%= cours.getDateDebut() %></td>
        <td><%= cours.getDateFin() %></td>
        <td><%= cours.getPersonneByIdEnseignant().getNom() %> <%= cours.getPersonneByIdEnseignant().getPrenom() %></td>
        <td>
            <form action="InscriptionCoursServlet" method="post">
                <input type="hidden" name="idCours" value="<%= cours.getIdCours() %>">
                <button type="submit" class="button">S'inscrire</button>
            </form>
        </td>
    </tr>
    <%
        }
    %>
    </tbody>
</table>
<%
    }
%>
</body>
</html>
