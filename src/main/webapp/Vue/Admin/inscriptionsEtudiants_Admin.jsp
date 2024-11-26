<!DOCTYPE html>
<html lang="fr">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Administrateur - Formulaire d'inscription étudiant</title>
  <link rel="stylesheet" href="style.css">
  <style>
    /* Global styles */
    body {
      font-family: Arial, sans-serif;
      background-color: #f4f4f9;
      margin: 0;
      padding: 0;
    }

    .header {
      background-color: #007BFF;
      color: white;
      padding: 20px;
      text-align: center;
      position: fixed;
      top: 0;
      width: 100%;
      z-index: 1000;
    }

    .header h2 {
      margin: 0;
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
<div class="header">
  <h2>Administrateur</h2>
  <button onclick="window.location.href='logout.html'">Déconnexion</button>
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
