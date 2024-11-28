<%@ page import="org.jee.entity.Cours" %>
<%@ page import="org.jee.entity.Resultat" %>
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
<%
    // Retrieve the course and results from the request attributes
    Cours cours = (Cours) request.getAttribute("cours");
    List<Resultat> resultats = (List<Resultat>) request.getAttribute("resultats");

    // Check if the course exists
    if (cours == null) {
        response.sendRedirect(request.getContextPath() + "/MenuEtudiantServlet");
        return;
    }
%>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Notes du Cours - <%= cours.getNomCours() %></title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/Vue/style.css">
</head>

<body>
<div class="header header-etudiant">
    <img src="<%= request.getContextPath() %>/Images/cytech.png" class="logo">
    <h2>Étudiant</h2>
    <nav>
        <ul>
            <li><a href="<%= request.getContextPath() %>/MenuEtudiantServlet">Accueil</a></li>
            <li><a href="<%= request.getContextPath() %>/DemandeInscriptionServlet">Inscrire à un cours</a></li>
        </ul>
    </nav>
    <form action="<%= request.getContextPath() %>/logout" method="Get" style="display: inline;">
        <button type="submit">Déconnexion</button>
    </form>
</div>

<!-- Course Information -->
<div>
    <h3>Informations sur le Cours</h3>
    <p><strong>Nom du Cours:</strong> <%= cours.getNomCours() %></p>
    <p><strong>Professeur:</strong> <%= cours.getPersonneByIdEnseignant().getPrenom() %> <%= cours.getPersonneByIdEnseignant().getNom() %></p>
</div>

<!-- Results Table -->
<div>
    <h3>Résultats des Étudiants</h3>
    <table>
        <thead>
        <tr>
            <th>Note Numéro</th>
            <th>Note</th>
        </tr>
        </thead>
        <tbody>
        <%
            if (resultats != null && !resultats.isEmpty()) {
                int counter = 1;  // Counter for the result number
                for (Resultat resultat : resultats) {
        %>
        <tr>
            <td>Note numéro <%= counter++ %></td>
            <td><%= resultat.getNote() != null ? String.format("%.2f", resultat.getNote()) : "N/A" %></td>
        </tr>
        <%
            }
        } else {
        %>
        <tr>
            <td colspan="2">Aucun résultat disponible pour ce cours.</td>
        </tr>
        <%
            }
        %>
        </tbody>
    </table>
</div>

</body>
</html>
