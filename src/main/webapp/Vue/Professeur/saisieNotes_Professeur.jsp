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
  <link rel="stylesheet" href="../style.css">
</head>
<body>
<!-- Header avec menu de navigation -->
<div class="header header-professeur">
  <img src="<%= request.getContextPath() %>/Images/cytech.png" class="logo">
  <h2>Professeur</h2>
  <nav>
    <ul>
      <li><a href="menu_Professeur.jsp">Accueil</a></li>
      <li><a href="inscription_prof.jsp">Inscription Professeur</a></li>
      <li><a href="saisieNotes_Professeur.jsp">Saisie de Notes</a></li>
      <li><a href="traitementNotes.jsp">Traitement des Notes</a></li>
    </ul>
  </nav>
  <form action="../../logout" method="Get" style="display: inline;">
    <button type="submit">Déconnexion</button>
  </form>
</div>
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
