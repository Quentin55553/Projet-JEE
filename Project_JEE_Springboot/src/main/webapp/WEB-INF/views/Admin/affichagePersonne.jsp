<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Affichage des personnes</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <script>
        // Fonction pour soumettre automatiquement le formulaire de typePersonne
        function submitTypePersonneForm() {
            document.getElementById("typePersonneForm").submit();
        }
    </script>
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

<h1 class="actions no-data">Liste des personnes</h1>

<!-- Affichage des messages -->
<c:if test="${not empty error}">
    <p class="error">${error}</p>
</c:if>
<c:if test="${not empty success}">
    <p class="success">${success}</p>
</c:if>

<!-- Formulaire pour choisir entre Étudiants et Professeurs -->
<div class="actions no-data" style="margin-bottom: 20px;">
    <form id="typePersonneForm" action="${pageContext.request.contextPath}/admin/listePersonne/affichagePersonne" method="post">
        <input type="hidden" name="form_filtre" value="false">
        <label>
            <input type="radio" name="typePersonne" value="etudiant"
                   onchange="submitTypePersonneForm()" <c:if test="${typePersonne == null || typePersonne == 'etudiant'}">checked</c:if>> Étudiants
        </label>
        <label>
            <input type="radio" name="typePersonne" value="professeur"
                   onchange="submitTypePersonneForm()" <c:if test="${typePersonne == 'professeur'}">checked</c:if>> Professeurs
        </label>
    </form>
</div>

<!-- Tableau des filtres -->
<div>
    <form id="filterForm" action="${pageContext.request.contextPath}/admin/listePersonne/affichagePersonne" method="post">
        <input type="hidden" name="form_filtre" value="true">
        <table class="data-table" border="1">
            <thead>
            <tr>
                <th>
                    <input type="text" name="filtre_id" value="${filtre_id}" placeholder="Filtrer par ID">
                </th>
                <th>
                    <input type="text" name="filtre_nom" value="${filtre_nom}" placeholder="Filtrer par Nom">
                </th>
                <th>
                    <input type="text" name="filtre_prenom" value="${filtre_prenom}" placeholder="Filtrer par Prénom">
                </th>
                <th>
                    <input type="text" name="filtre_contact" value="${filtre_contact}" placeholder="Filtrer par Contact">
                </th>
                <th>
                    <button type="submit">Appliquer les filtres</button>
                </th>
            </tr>
            </thead>
        </table>
    </form>
</div>

<!-- Tableau principal avec données -->
<div>
    <table class="data-table" border="1">
        <thead>
        <tr>
            <th>ID</th>
            <th>Nom</th>
            <th>Prénom</th>
            <th>Contact</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:if test="${not empty liste}">
            <c:forEach var="personne" items="${liste}">
                <tr>
                    <td><a href="${pageContext.request.contextPath}/admin/listePersonne/details?id=${personne.idPersonne}">${personne.idPersonne}</a></td>
                    <td>${personne.nom}</td>
                    <td>${personne.prenom}</td>
                    <td>${personne.contact}</td>
                    <td>
                        <!-- Formulaire pour modifier -->
                        <form action="${pageContext.request.contextPath}/admin/listePersonne/modifier" method="get" style="display:inline;">
                            <input type="hidden" name="idPersonne" value="${personne.idPersonne}">
                            <button type="submit">Modifier</button>
                        </form>
                        <!-- Formulaire pour supprimer -->
                        <form action="${pageContext.request.contextPath}/admin/listePersonne/supprimer" method="post" style="display:inline;">
                            <input type="hidden" name="idPersonne" value="${personne.idPersonne}">
                            <button type="submit" class="deleteButton">Supprimer</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </c:if>
        <c:if test="${empty liste}">
            <tr>
                <td colspan="5">Aucune personne trouvée.</td>
            </tr>
        </c:if>
        </tbody>
    </table>
</div>

</body>
</html>