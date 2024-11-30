<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<link rel="icon" href="${pageContext.request.contextPath}/Images/cytech.png" type="image/png">
<head>
    <meta charset="UTF-8">
    <title>Formulaire de connexion</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>

<body>
<div class="background-shape1"></div>
<div class="background-shape2"></div>
<div class="background-shape"></div>

<img src="${pageContext.request.contextPath}/Images/cytech.png" class="loginLogo">

<div class="form-container">
    <h2>Connexion</h2>

    <form action="${pageContext.request.contextPath}/login" method="POST">
        <label for="email">Email</label>
        <input type="email" id="email" name="email" required>

        <label for="mdp">Mot de passe</label>
        <input type="password" id="mdp" name="mdp" required>

        <button type="submit">Se connecter</button>
    </form>

    <c:if test="${not empty error}">
        <p class="error">
            <c:choose>
                <c:when test="${error == 'invalid_credentials'}">Identifiants invalides.</c:when>
                <c:when test="${error == 'unknown_role'}">Rôle utilisateur inconnu. Contactez l'administrateur.</c:when>
                <c:otherwise>Erreur inconnue. Veuillez réessayer.</c:otherwise>
            </c:choose>
        </p>
    </c:if>
</div>
</body>
</html>
