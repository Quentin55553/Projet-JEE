<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Tableau des Cours</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>

<body>
<!-- Header avec menu de navigation -->
<div class="header header-professeur">
    <img src="${pageContext.request.contextPath}/Images/cytech.png" class="logo">
    <h2 class="no-data">Professeur</h2>
    <nav>
        <ul>
            <li><a href="${pageContext.request.contextPath}/professeur/home">Accueil</a></li>
            <li><a href="${pageContext.request.contextPath}/professeur/inscription">Inscription Professeur</a></li>
            <li><a href="${pageContext.request.contextPath}/professeur/notes">Saisie de Notes</a></li>
        </ul>
    </nav>
    <form action="${pageContext.request.contextPath}/logout" method="GET" style="display: inline;">
        <button type="submit">Déconnexion</button>
    </form>
</div>

<!-- Contenu principal -->
<div class="main-content">
    <h1 class="no-data">Bienvenue, ${user.prenom} ${user.nom}!</h1>

    <!-- Boutons d'action -->
    <div  class="no-data">
        <button onclick="window.location.href='${pageContext.request.contextPath}/professeur/inscription'">Inscription Professeur</button>
        <button onclick="window.location.href='${pageContext.request.contextPath}/professeur/notes'">Saisie de Notes</button>
    </div>

    <!-- Section dynamique selon le choix -->
    <div id="dynamic-content">
        <p class="no-data">Sélectionnez une action dans le menu ou cliquez sur un des boutons ci-dessus pour commencer.</p>
    </div>
</div>
</body>
</html>
