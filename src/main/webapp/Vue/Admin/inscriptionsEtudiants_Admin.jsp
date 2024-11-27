<%@ page import="org.jee.entity.Personne" %><%
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
  <style>
    /* Global styles */
    body {
      font-family: Arial, sans-serif;
      background-color: #f4f4f9;
      margin: 0;
      padding: 0;
    }

    .header button {
      background-color: #dc3545;
      color: white;
      border: none;
      padding: 10px 20px;
      cursor: pointer;
      font-size: 16px;
      float: right;
      margin-top: -30px;
    }

    .header button:hover {
      background-color: #c82333;
    }

    /* Form container styles */
    .form-container {
      margin-top: 80px;
      background-color: white;
      max-width: 500px;
      margin: 30px auto;
      padding: 20px;
      border-radius: 8px;
      box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    }

    .form-container h2 {
      text-align: center;
      margin-bottom: 20px;
    }

    label {
      display: block;
      font-size: 14px;
      margin-bottom: 6px;
      font-weight: bold;
    }

    input[type="text"], input[type="email"], input[type="password"], input[type="date"] {
      width: 100%;
      padding: 10px;
      margin-bottom: 15px;
      border: 1px solid #ccc;
      border-radius: 4px;
      box-sizing: border-box;
    }

    button[type="submit"] {
      width: 100%;
      padding: 12px;
      background-color: #007BFF;
      color: white;
      border: none;
      font-size: 16px;
      cursor: pointer;
      border-radius: 4px;
      transition: background-color 0.3s ease;
    }

    button[type="submit"]:hover {
      background-color: #0056b3;
    }

    .back-link {
      display: block;
      text-align: center;
      margin-top: 20px;
    }

    .back-link a {
      color: #007BFF;
      text-decoration: none;
      font-size: 16px;
    }

    .back-link a:hover {
      text-decoration: underline;
    }
  </style>
</head>
<body>

<!-- Header with "Déconnexion" button -->
<div class="header header-admin">
  <img src="<%= request.getContextPath() %>/Images/cytech.png" class="logo">
  <h2>Administrateur</h2>
  <nav>
    <ul>
      <li><a href="menu_admin.jsp">Accueil</a></li>
      <li><a href="inscriptionsEtudiants_Admin.jsp">Créer profil étudiant</a></li>
      <li><a href="inscriptionsProfesseurs_Admin.jsp">Créer profil professeur</a></li>
      <li><a href="gestionCours_Admin.jsp">Gestion de cours</a></li>
      <li><a href="manageInscription.jsp">Gestion des inscriptions</a></li>
      <li><a href="creationCours_Admin.jsp">Créer profil professeur</a></li>

    </ul>
  </nav>
  <form action="../../logout" method="Get" style="display: inline;">
    <button type="submit">Déconnexion</button>
  </form>
</div>
<br><br><br><br><br>
<!-- Form container for student registration -->
<div class="form-container">
  <h2>Inscription Etudiant</h2>
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
    <a href="menu_admin.html">Retour au menu administrateur</a>
  </div>
</div>

</body>
</html>
