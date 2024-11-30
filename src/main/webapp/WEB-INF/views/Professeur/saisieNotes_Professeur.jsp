<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Saisie des Notes - Professeur</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<!-- Header avec menu de navigation -->
<div class="header header-professeur">
    <img src="${pageContext.request.contextPath}/Images/cytech.png" class="logo">
    <h2>Professeur</h2>
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

<h1 class="no-data">Saisie des Notes - Professeur</h1>

<form action="${pageContext.request.contextPath}/professeur/traiter-notes" method="post">
    <table class="notes-table">
        <thead>
        <tr>
            <th>Sélectionner</th>
            <th>Nom du Cours</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="cours" items="${coursList}">
            <tr>
                <td><input type="radio" name="idCours" value="${cours.idCours}" required></td>
                <td>${cours.nomCours}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <br>
    <button type="submit" class="submit-button">Choisir ce cours</button>
</form>
</body>
</html>
