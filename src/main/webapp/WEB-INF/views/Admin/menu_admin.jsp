<%@ page import="com.example.demo.entity.Personne" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Tableau des Cours</title>
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

<!-- Contenu principal -->
<div class="main-content">
    <h1 class="no-data">Bienvenue, ${user.prenom} ${user.nom}!</h1>

    <!-- Boutons d'action -->
    <div class="actions no-data">
        <button onclick="window.location.href='${pageContext.request.contextPath}/admin/inscription/etudiant'">Créer profil étudiant</button>
        <button onclick="window.location.href='${pageContext.request.contextPath}/admin/inscription/professeur'">Créer profil professeur</button>
        <button onclick="window.location.href='${pageContext.request.contextPath}/admin/cours/gestion'">Accéder à la gestion de cours</button>
        <button onclick="window.location.href='${pageContext.request.contextPath}/admin/listePersonne/affichagePersonne'">Liste étudiant et professeur</button>
    </div>

    <!-- Section dynamique selon le choix -->
    <div id="dynamic-content">
        <p class="actions no-data">Sélectionnez une action dans le menu ou cliquez sur un des boutons ci-dessus pour commencer.</p>
    </div>
</div>
</body>
</html>
