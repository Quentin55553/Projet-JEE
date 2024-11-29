<%@ page import="org.jee.entity.Cours" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
  <title>Saisie des Notes - Professeur</title>
  <link rel="stylesheet" href="<%= request.getContextPath() %>/Vue/style.css">
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
<h1 class="no-data">Saisie des Notes - Professeur</h1>
<form action="<%= request.getContextPath() %>/NoteProfServlet" method="post">
  <table>
    <thead>
    <tr>
      <th>Sélectionner</th>
      <th>Nom du Cours</th>
    </tr>
    </thead>
    <tbody >
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
    %>
    </tbody>
  </table>
  <div style="text-align: center;  margin-right:80px;">
    <button type="submit">Choisir ce cours</button>
  </div>
  <%}%>
</form>
</body>
</html>
