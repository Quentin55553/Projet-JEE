<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Détails de la personne</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<!-- Header avec navigation -->
<div class="header header-admin">
    <img src="${pageContext.request.contextPath}/images/cytech.png" class="logo">
    <h2>Menu administrateur</h2>
    <nav>
        <ul>
            <li><a href="${pageContext.request.contextPath}/admin/menu">Accueil</a></li>
            <li><a href="${pageContext.request.contextPath}/admin/inscription/etudiant">Créer profil étudiant</a></li>
            <li><a href="${pageContext.request.contextPath}/admin/inscription/professeur">Créer profil professeur</a></li>
            <li><a href="${pageContext.request.contextPath}/admin/cours/gestion">Gestion de cours</a></li>
            <li><a href="${pageContext.request.contextPath}/admin/listePersonne/affichagePersonne">Liste Personne</a></li>
        </ul>
    </nav>
    <form action="${pageContext.request.contextPath}/logout" method="GET">
        <button type="submit">Déconnexion</button>
    </form>
</div>

<!-- Affichage des détails -->
<h1>Détails de la personne</h1>
<c:if test="${not empty personne}">
    <table class="data-table" border="1">
        <tr>
            <th>ID</th>
            <td>${personne.idPersonne}</td>
        </tr>
        <tr>
            <th>Nom</th>
            <td>${personne.nom}</td>
        </tr>
        <tr>
            <th>Prénom</th>
            <td>${personne.prenom}</td>
        </tr>
        <tr>
            <th>Contact</th>
            <td>${personne.contact}</td>
        </tr>
        <tr>
            <th>Date de naissance</th>
            <td>${personne.dateNaissance}</td>
        </tr>
        <tr>
            <th>Mot de passe</th>
            <td>${personne.password}</td>
        </tr>
    </table>
</c:if>
<c:if test="${empty personne}">
    <p class="error">Aucune information trouvée pour cette personne.</p>
</c:if>

</body>
</html>
