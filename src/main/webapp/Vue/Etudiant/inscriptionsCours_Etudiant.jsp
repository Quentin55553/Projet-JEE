<%@ page import="org.jee.entity.Cours" %>
<%@ page import="org.jee.entity.Personne" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    // Vérification de l'utilisateur connecté et de son rôle
    Personne user = (Personne) session.getAttribute("user");
    if (user == null || user.getRole() != 3) {
        response.sendRedirect(request.getContextPath() + "/Vue/login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cours disponibles pour inscription</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/Vue/style.css">
</head>
<body>
<div class="header header-etudiant">
    <img src="<%= request.getContextPath() %>/Images/cytech.png" class="logo">
    <h3>Étudiant</h3>
    <nav>
        <ul>
            <li><a href="<%= request.getContextPath() %>/MenuEtudiantServlet">Accueil</a></li>
            <li><a href="<%= request.getContextPath() %>/DemandeInscriptionServlet">Inscrire à un cours</a></li>
            <li><a href="<%= request.getContextPath() %>/DebutReleveResultatServlet">Générer le relevé de notes</a></li>
        </ul>
    </nav>
    <form action="<%= request.getContextPath() %>/logout" method="Get" style="display: inline;">
        <button type="submit">Déconnexion</button>
    </form>
</div>
<h3>Cours disponibles pour inscription</h3>

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
        <td><%= cours.getPersonneByIdEnseignant().getPrenom() %> <%= cours.getPersonneByIdEnseignant().getNom() %></td>
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
