<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Saisie des Notes</title>
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

<h2 class="no-data">Cours: ${cours.nomCours}</h2>

<c:choose>
    <c:when test="${not empty students}">
        <form action="${pageContext.request.contextPath}/professeur/update-notes" method="post">
            <input type="hidden" name="idCours" value="${cours.idCours}">
            <table class="notes-table">
                <thead>
                <tr>
                    <th>Nom</th>
                    <th>Prénom</th>
                    <th>Email</th>
                    <th>Note (0-20)</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="student" items="${students}">
                    <tr>
                        <td>${student.nom}</td>
                        <td>${student.prenom}</td>
                        <td>${student.idPersonne}</td>
                        <td>
                            <input type="number" name="note_${student.idPersonne}" min="0" max="20" required>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <button type="submit" class="submit-button">Soumettre les notes</button>
        </form>
    </c:when>
    <c:otherwise>
        <p class="no-data">Aucun étudiant à évaluer.</p>
    </c:otherwise>
</c:choose>
</body>
</html>
