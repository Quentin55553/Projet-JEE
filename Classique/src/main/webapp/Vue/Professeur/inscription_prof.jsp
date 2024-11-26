<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Demandes d'inscription en attente</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
            padding: 0;
        }
        h1 {
            color: #333;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        table th, table td {
            border: 1px solid #ddd;
            padding: 10px;
            text-align: left;
        }
        table th {
            background-color: #f2f2f2;
        }
        table tr:hover {
            background-color: #f9f9f9;
        }
        .no-data {
            font-size: 18px;
            color: #666;
            margin-top: 20px;
        }
        textarea {
            width: 100%;
            height: 50px;
            margin-top: 5px;
        }
        button {
            margin-top: 10px;
            padding: 5px 10px;
            cursor: pointer;
        }
        .accept-btn {
            background-color: #4CAF50;
            color: white;
            border: none;
        }
        .deny-btn {
            background-color: #f44336;
            color: white;
            border: none;
        }
    </style>
</head>
<body>
<h1>Demandes d'inscription en attente</h1>

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
                <textarea name="commentaire" placeholder="Ajoutez un commentaire ici (optionnel)"></textarea>
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
