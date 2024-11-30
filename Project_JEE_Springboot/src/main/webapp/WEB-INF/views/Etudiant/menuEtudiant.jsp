<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Menu Étudiant</title>
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

<!-- Section Inscriptions -->
<div>
    <h3>Inscriptions en attente ou refusées</h3>
    <table>
        <thead>
        <tr>
            <th>Nom du Cours</th>
            <th>Professeur</th>
            <th>État</th>
        </tr>
        </thead>
        <tbody>
        <c:choose>
            <c:when test="${not empty inscriptions}">
                <c:forEach var="inscription" items="${inscriptions}">
                    <tr>
                        <td>${inscription.cours.nomCours}</td>
                        <td>${inscription.cours.personneByIdEnseignant.prenom} ${inscription.cours.personneByIdEnseignant.nom}</td>
                        <td>
                            <c:choose>
                                <c:when test="${inscription.etat == 0}">En attente</c:when>
                                <c:otherwise>Refusé</c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <tr>
                    <td colspan="3">Aucune inscription en attente ou refusée.</td>
                </tr>
            </c:otherwise>
        </c:choose>
        </tbody>
    </table>
</div>

<!-- Section Moyenne des Cours -->
<div>
    <h3>Mes Cours :</h3>
    <table>
        <thead>
        <tr>
            <th>Nom du Cours</th>
            <th>Professeur</th>
            <th>Détails</th>
        </tr>
        </thead>
        <tbody>
        <c:choose>
            <c:when test="${not empty courses}">
                <c:forEach var="cours" items="${courses}">
                    <tr>
                        <form action="${pageContext.request.contextPath}/Matiere" method="GET">
                            <td>${cours.nomCours}</td>
                            <td>${cours.personneByIdEnseignant.prenom} ${cours.personneByIdEnseignant.nom}</td>
                            <td>
                                <input type="hidden" name="idCours" value="${cours.idCours}">
                                <button type="submit">Voir Détails</button>
                            </td>
                        </form>
                    </tr>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <tr>
                    <td colspan="3">Aucun cours disponible.</td>
                </tr>
            </c:otherwise>
        </c:choose>
        </tbody>
    </table>
</div>
</body>
</html>
