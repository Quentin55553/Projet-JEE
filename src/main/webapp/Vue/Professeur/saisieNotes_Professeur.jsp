<%@ page import="org.jee.entity.Cours" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
  <title>Saisie des Notes - Professeur</title>
  <style>
    table {
      width: 100%;
      border-collapse: collapse;
    }
    th, td {
      border: 1px solid black;
      padding: 8px;
      text-align: left;
    }
    th {
      background-color: #f2f2f2;
    }
  </style>
</head>
<body>
<h1>Saisie des Notes - Professeur</h1>
<form action="<%= request.getContextPath() %>/NoteProfServlet" method="post">
  <table>
    <thead>
    <tr>
      <th>Sélectionner</th>
      <th>Nom du Cours</th>
    </tr>
    </thead>
    <tbody>
    <%
      // Cast the coursList attribute to List<Cours>
      List<Cours> coursList = (List<Cours>) request.getAttribute("coursList");

      // Check if the coursList is null or empty
      if (coursList == null || coursList.isEmpty()) {
    %>
    <tr><td colspan="2">Pas de cours disponible</td></tr>
    <%
    } else {
      for (Cours cours : coursList) {
    %>
    <tr>
      <td><input type="radio" name="idCours" value="<%= cours.getIdCours() %>" required></td>
      <td><%= cours.getNomCours() %></td>
    </tr>
    <%
        }
      }
    %>
    </tbody>
  </table>
  <br>
  <button type="submit">Choisir ce cours</button>
</form>
</body>
</html>
