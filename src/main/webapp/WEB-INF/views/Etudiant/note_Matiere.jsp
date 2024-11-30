<%@ page import="com.example.demo.entity.Resultat" %>
<%@ page import="com.example.demo.entity.Cours" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Détails des Notes</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="header">
    <h2>Détails des Notes pour le Cours</h2>
</div>

<h3>Cours : ${cours.nomCours}</h3>
<p>Enseignant : ${cours.personneByIdEnseignant.prenom} ${cours.personneByIdEnseignant.nom}</p>

<h3>Résultats</h3>
<table>
    <thead>
    <tr>
        <th>Note</th>
    </tr>
    </thead>
    <tbody>
    <c:choose>
        <c:when test="${not empty resultats}">
            <c:forEach var="resultat" items="${resultats}">
                <tr>
                    <td>${resultat.note}</td>
                </tr>
            </c:forEach>
        </c:when>
        <c:otherwise>
            <tr>
                <td colspan="2">Aucun résultat disponible pour ce cours.</td>
            </tr>
        </c:otherwise>
    </c:choose>

    </tbody>
</table>

<a href="${pageContext.request.contextPath}/etudiant/MenuEtudiant">Retour au Menu</a>
</body>
</html>

