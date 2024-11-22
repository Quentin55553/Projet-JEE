<!DOCTYPE html>
<html lang="fr">
<head>
  <meta charset="UTF-8">

  <title>Amdinistrateur - Formulaire d'inscription étudiant</title>
  <link rel="stylesheet" href="style.css">
</head>
<body>
<div class="header">
  <h2>Administrateur</h2>
  <button>Déconnexion</button>
</div>

<div class="form-container">
  <h2>Inscription professeur</h2>
  <form action="../../CreeEP" method="POST">
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
</div>
</body>
</html>
