<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Demandes d'inscription en attente</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/Vue/style.css">
    <style>
        textarea {
            width: 100%;
            height: 50px;
            margin-top: 5px;
        }
        .deny-btn {
            background-color: #f44336;
            color: white;
            border: none;
        }
    </style>
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
<h1 class="no-data">Demandes d'inscription en attente</h1>

<%
    // Retrieve the list of inscriptions passed from the servlet
    List<Map<String, String>> inscriptionDetails = (List<Map<String, String>>) request.getAttribute("inscriptionDetails");

    if (inscriptionDetails != null && !inscriptionDetails.isEmpty()) {
%>
<table>
    <thead>
    <tr>
        <th>Nom de l'étudiant</th>
        <th>Email de l'étudiant</th>
        <th>Nom du cours</th>
        <th>Commentaire</th>
        <th>Action</th>
    </tr>
    </thead>
    <tbody>
    <%
        // Loop through each record and display it in a table row
        for (Map<String, String> details : inscriptionDetails) {
            String studentName = details.get("studentName");
            String studentEmail = details.get("studentEmail");
            String courseName = details.get("courseName");
    %>
    <tr>
        <td><%= studentName %></td>
        <td><%= studentEmail %></td>
        <td><%= courseName %></td>
        <td>
            <form method="post" action="<%= request.getContextPath() %>/ManageInscriptionServlet">
                <label>
                    <textarea name="commentaire" placeholder="Ajoutez un commentaire ici (optionnel)"></textarea>
                </label>
        </td>
        <td>
            <input type="hidden" name="studentEmail" value="<%= studentEmail %>">
            <input type="hidden" name="courseName" value="<%= courseName %>">
            <button type="submit" name="action" value="accept" class="accept-btn">Accepter</button>
            <button type="submit" name="action" value="deny" class="deny-btn">Refuser</button>
            </form>
        </td>
    </tr>
    <% } %>
    </tbody>
</table>
<%
} else {
%>
<p class="no-data">Aucune demande d'inscription en attente.</p>
<%
    }
%>
</body>
</html>
