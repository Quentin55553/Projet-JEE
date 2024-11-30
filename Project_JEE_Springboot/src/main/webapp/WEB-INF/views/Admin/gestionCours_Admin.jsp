<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Gestion des Cours</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<!-- Header avec menu de navigation -->
<div class="header header-admin">
    <img src="${pageContext.request.contextPath}/images/cytech.png" class="logo">
    <h2>Administrateur</h2>
    <nav>
        <ul>
            <li><a href="${pageContext.request.contextPath}/admin/menu">Accueil</a></li>
            <li><a href="${pageContext.request.contextPath}/admin/inscription/etudiant">Créer profil étudiant</a></li>
            <li><a href="${pageContext.request.contextPath}/admin/inscription/professeur">Créer profil professeur</a></li>
            <li><a href="${pageContext.request.contextPath}/admin/cours/gestion">Gestion de cours</a></li>
            <li><a href="${pageContext.request.contextPath}/admin/listePersonne/affichagePersonne">Liste Personne</a></li>
        </ul>
    </nav>
    <form action="${pageContext.request.contextPath}/logout" method="GET" style="display: inline;">
        <button type="submit">Déconnexion</button>
    </form>
</div>

<div>
    <h1 class="no-data">Gestion des Cours</h1>
    <form action="${pageContext.request.contextPath}/admin/cours/creation" method="GET" class="form-actions no-data">
        <button type="submit">Créer un cours</button>
    </form>

    <table class="data-table">
        <thead>
        <tr>
            <th>Nom du Cours</th>
            <th>Description</th>
            <th>Date Début</th>
            <th>Date Fin</th>
            <th>Professeur</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="cours" items="${coursList}">
            <tr>
                <td>${cours.nomCours}</td>
                <td>${cours.description}</td>
                <td>${cours.dateDebut}</td>
                <td>${cours.dateFin}</td>
                <td>${cours.personneByIdEnseignant.nom}</td>
                <td>
                    <form action="${pageContext.request.contextPath}/admin/cours/modification/${cours.idCours}" method="get" style="display: inline;">
                        <button type="submit">Modifier</button>
                    </form>
                    <form action="${pageContext.request.contextPath}/admin/cours/delete/${cours.idCours}" method="post" style="display: inline;">
                        <button type="submit" class="deleteButton">Supprimer</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>
