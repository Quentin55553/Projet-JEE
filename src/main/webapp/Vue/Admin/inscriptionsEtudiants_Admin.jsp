<%@ page import="org.jee.entity.Personne" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
  // Vérifiez si l'utilisateur est connecté et est administrateur
  Personne user = (Personne) session.getAttribute("user");
  if (user == null || user.getRole() != 1) {
    // Redirigez vers la page de connexion si l'utilisateur n'est pas connecté ou n'est pas administrateur
    response.sendRedirect(request.getContextPath()+"/Vue/login.jsp");
    return;
  }
%>

<!DOCTYPE html>
<html lang="fr">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Administrateur - Formulaire d'inscription étudiant</title>
  <link rel="stylesheet" href="../style.css">
</head>
<body>

<!-- Header with "Déconnexion" button -->
<div class="header header-admin">
  <img src="<%= request.getContextPath() %>/Images/cytech.png" class="logo">
  <h2>Administrateur</h2>
  <nav>
    <ul>
      <li><a href="<%= request.getContextPath() %>/Vue/Admin/menu_admin.jsp">Accueil</a></li>
      <li><a href="<%= request.getContextPath() %>/Vue/Admin/inscriptionsEtudiants_Admin.jsp">Cr&#233;er profil &#233;tudiant</a></li>
      <li><a href="<%= request.getContextPath() %>/Vue/Admin/inscriptionsProfesseurs_Admin.jsp">Créer profil professeur</a></li>
      <li><a href="<%= request.getContextPath() %>/Vue/Admin/gestionCours_Admin.jsp">Gestion de cours</a></li>
      <li><a href="<%= request.getContextPath() %>/Vue/Admin/AffichagePersonne_Admin.jsp">Affichage de professeur et étudiant</a></li>
    </ul>
  </nav>
  <form action="<%= request.getContextPath() %>/logout" method="Get" style="display: inline;">
    <button type="submit">Déconnexion</button>
  </form>
</div>
<br><br><br><br><br>
<!-- Form container for student registration -->
<div class="form-container">
  <h3>Inscription Etudiant</h3>
  <form action="<%= request.getContextPath() %>/CreeEP" method="POST">
    <label for="nom">Nom</label>
    <input type="text" id="nom" name="nom" required>

    <label for="prenom">Prénom</label>
    <input type="text" id="prenom" name="prenom" required>

    <label for="date_naissance">Date de naissance</label>
    <input type="date" id="date_naissance" name="date_naissance" required>

    <label for="contact">Email personnel</label>
    <input type="email" id="contact" name="contact" required>

    <label for="password">Mot de passe</label>
    <input type="password" id="password" name="password" required>

    <input type="hidden" id="role" name="role" value="3">

    <button type="submit">Créer profil</button>
  </form>

  <!-- Back to Admin Menu Link -->
  <div class="back-link">
    <a href="<%= request.getContextPath() %>/Vue/Admin/menu_admin.jsp">Retour au menu administrateur</a>
  </div>
</div>

</body>
</html>
