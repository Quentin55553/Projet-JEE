<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Demandes d'inscription en attente</title>
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
<h1>Demandes d'inscription en attente</h1>

<c:if test="${not empty inscriptionDetails}">
    <table>
        <thead>
        <tr>
            <th>Nom de l'étudiant</th>
            <th>Email de l'étudiant</th>
            <th>Nom du cours</th>
            <th>Commentaire</th>
            <th>Action</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="details" items="${inscriptionDetails}">
            <tr>
                <td>${details.studentName}</td>
                <td>${details.studentEmail}</td>
                <td>${details.courseName}</td>
                <td>
                    <form method="post" action="${pageContext.request.contextPath}/professeur/manage-inscription">
                        <textarea name="commentaire" placeholder="Ajoutez un commentaire ici (optionnel)"></textarea>
                </td>
                <td>
                    <input type="hidden" name="studentEmail" value="${details.studentEmail}">
                    <input type="hidden" name="courseName" value="${details.courseName}">
                    <button type="submit" name="action" value="accept" class="accept-btn">Accepter</button>
                    <button type="submit" name="action" value="deny" class="deny-btn">Refuser</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</c:if>
<c:if test="${empty inscriptionDetails}">
    <p>Aucune demande d'inscription en attente.</p>
</c:if>
</body>
</html>
