<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Cours disponibles pour inscription</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="header header-etudiant">
    <img src="${pageContext.request.contextPath}/Images/cytech.png" class="logo">
    <h2>Étudiant</h2>
    <nav>
        <ul>
            <li><a href="${pageContext.request.contextPath}/etudiant/MenuEtudiant">Accueil</a></li>
            <li><a href="${pageContext.request.contextPath}/etudiant/inscriptionCours">Inscription cours</a></li>
            <li><a href="${pageContext.request.contextPath}/etudiant/notes">Notes</a></li>
        </ul>
    </nav>
    <form action="${pageContext.request.contextPath}/logout" method="GET" style="display: inline;">
        <button type="submit">Déconnexion</button>
    </form>
</div>
<h3>Cours disponibles pour inscription</h3>

<c:if test="${empty coursNonInscrits}">
    <p class="no-data">Aucun cours disponible pour inscription.</p>
</c:if>

<c:if test="${not empty coursNonInscrits}">
    <table>
        <thead>
        <tr>
            <th>Nom du cours</th>
            <th>Description</th>
            <th>Date de début</th>
            <th>Date de fin</th>
            <th>Professeur</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="cours" items="${coursNonInscrits}">
            <tr>
                <td>${cours.nomCours}</td>
                <td>${cours.description}</td>
                <td>${cours.dateDebut}</td>
                <td>${cours.dateFin}</td>
                <td>${cours.personneByIdEnseignant.prenom} ${cours.personneByIdEnseignant.nom}</td>
                <td>
                    <form action="${pageContext.request.contextPath}/etudiant/inscrireCours" method="POST">
                        <input type="hidden" name="idCours" value="${cours.idCours}">
                        <button type="submit" class="button">S'inscrire</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</c:if>
</body>
</html>
