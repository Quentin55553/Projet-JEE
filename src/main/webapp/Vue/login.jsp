<!DOCTYPE html>
<html lang="fr">
    <head>
        <meta charset="UTF-8">
        <title>Formulaire de connexion</title>
        <link rel="stylesheet" href="Vue/style.css">
    </head>

    <body>
        <div class="form-container">
            <h2>Connexion</h2>

            <form>
                <label for="email">Email</label>
                <input type="email" id="email" name="email" required>

                <label for="mdp">Mot de passe</label>
                <input type="password" id="mdp" name="mdp" required>

                <button type="submit">Se connecter</button>
            </form>

            <% if (request.getParameter("error") != null) { %>
                <p class="error-message">Identifiants invalides. Veuillez réessayer.</p>
            <% } %>

            <a href="Vue/inscription.jsp" class="nouvel-eleve">Nouvel élève</a>
        </div>
    </body>
</html>
