<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Inscription Étudiant</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<!-- Header avec menu de navigation -->
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
    <form action="${pageContext.request.contextPath}/logout" method="GET" style="display: inline;">
        <button type="submit">Déconnexion</button>
    </form>
</div>
<div class="form-container">
    <h2>Inscription Étudiant</h2>
    <form action="${pageContext.request.contextPath}/admin/inscription/save" method="POST">
        <label for="nom">Nom</label>
        <input type="text" id="nom" name="nom" required>

        <label for="prenom">Prénom</label>
        <input type="text" id="prenom" name="prenom" required>

        <label for="date_naissance">Date de naissance</label>
        <input type="date" id="date_naissance" name="date_naissance" required>

        <label for="contact">Email personnel</label>
        <input type="email" id="contact" name="contact" required>

        <label for="password">Mot de passe</label>
        <input type="password" id="password" name="password" required>

        <input type="hidden" id="role" name="role" value="3">

        <button type="submit">Créer profil</button>
    </form>
    <div class="back-link">
        <a href="${pageContext.request.contextPath}/admin/menu">Retour au menu administrateur</a>
    </div>
</div>
</body>
</html>
