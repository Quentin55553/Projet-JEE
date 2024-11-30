<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>${action == 'creation' ? 'Créer un Cours' : 'Modifier un Cours'}</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<<!-- Header avec menu de navigation -->
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
    <h2>${action == 'creation' ? 'Créer un Cours' : 'Modifier un Cours'}</h2>
    <form action="${pageContext.request.contextPath}/admin/cours/save" method="POST">
        <input type="hidden" name="action" value="${action}">
        <input type="hidden" name="id_cours" value="${cours.idCours}">

        <label>Nom du cours</label>
        <input type="text" name="nom_cours" value="${cours.nomCours}" required>

        <label>Description</label>
        <input type="text" name="description" value="${cours.description}">

        <label>Date de début</label>
        <input type="date" name="date_debut" value="${cours.dateDebut}" required>

        <label>Date de fin</label>
        <input type="date" name="date_fin" value="${cours.dateFin}" required>

        <label>ID du Professeur</label>
        <input type="text" name="id_professeur" value="${cours.personneByIdEnseignant.idPersonne}" required>

        <button type="submit">${action == 'creation' ? 'Créer' : 'Modifier'} le Cours</button>
    </form>
</div>
</body>
</html>
