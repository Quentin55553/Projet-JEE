<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Formulaire de connexion</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/Vue/style.css">
</head>

<body>
<div class="background-shape1"></div>
<div class="background-shape2"></div>
<div class="background-shape"></div>

<img src="<%= request.getContextPath() %>/Images/cytech.png" class="loginLogo">

<div class="form-container">
    <h2>Connexion</h2>

    <form action="<%= request.getContextPath() %>/login" method="POST">
        <label for="idPersonne">Email</label>
        <input type="email" id="idPersonne" name="idPersonne" required>

        <label for="password">Mot de passe</label>
        <input type="password" id="password" name="password" required>

        <button type="submit">Se connecter</button>
    </form>

    <% if (request.getParameter("error") != null) { %>
    <p class="error-message">Identifiants invalides. Veuillez réessayer.</p>
    <% } %>

    <a href="Vue/inscription.jsp" class="nouvel-eleve">Nouvel élève</a>
</div>
</body>
</html>
