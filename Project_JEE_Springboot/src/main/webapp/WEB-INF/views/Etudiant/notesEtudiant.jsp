<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Notes Étudiant</title>
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
<h2>Notes de l'Étudiant</h2>

<!-- Message éventuel -->
<c:if test="${not empty message}">
    <p>${message}</p>
</c:if>

<!-- Affichage des notes -->
<c:if test="${not empty coursNotesMap}">
    <h3>Moyenne Globale : ${moyenneGlobale}/20</h3>
    <table>
        <thead>
        <tr>
            <th>Cours</th>
            <th>Moyenne</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="entry" items="${coursNotesMap}">
            <tr>
                <!-- Nom du cours -->
                <td>${entry.key.nomCours}</td>
                <!-- Calcul de la moyenne -->
                <td>
                    <c:set var="moyenne" value="0.0" />
                    <c:forEach var="note" items="${entry.value}">
                        <c:set var="moyenne" value="${moyenne + note.note}" />
                    </c:forEach>
                        ${moyenne / fn:length(entry.value)}/20
                </td>
                <!-- Bouton Voir Détails -->
                <td>
                    <form action="${pageContext.request.contextPath}/Matiere" method="GET">
                        <input type="hidden" name="idCours" value="${entry.key.idCours}">
                        <button type="submit">Voir Détails</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <!-- Génération du relevé de notes -->
    <form action="${pageContext.request.contextPath}/etudiant/releve" method="POST">
        <input type="hidden" name="moyenneGlobale" value="${moyenneGlobale}">
        <button type="submit">Générer le Relevé</button>
    </form>
</c:if>

<!-- Aucune donnée disponible -->
<c:if test="${empty coursNotesMap}">
    <p>Aucune donnée disponible pour afficher les notes.</p>
</c:if>
</body>
</html>
