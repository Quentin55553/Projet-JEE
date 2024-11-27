<%@ page import="org.jee.entity.Personne" %>
<%@ page import="java.util.List" %>
<%@ page import="org.jee.entity.Cours" %>
<%@ page import="org.jee.entity.Inscription" %>
<%@ page import="org.jee.entity.Resultat" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    // Vérifiez si l'utilisateur est connecté et est étudiant
    Personne user = (Personne) session.getAttribute("user");
    if (user == null || user.getRole() != 3) {
        // Redirigez vers la page de connexion si l'utilisateur n'est pas connecté ou n'est pas étudiant
        response.sendRedirect(request.getContextPath()+"/Vue/login.jsp");
        return;
    }

    //TODO une méthode d'une servlet pour renvoyer la liste des cours auquel l'etudiant est inscrit
    //ça peut passer par une methode statique : voire la servlet ControlleurCours de Admin
    List<Inscription> listCoursThisEtudiant = null;//=CoursForEtudiantServlet.getCoursList();

    List<Inscription> listInscriptions = null;

    //TODO une méthode d'une servlet pour renvoyer la liste des resultats par matiere de l'etudiant
    List<Resultat> listResultatThisEtudiant = null;
%>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Tableau des Cours</title>
    <link rel="stylesheet" href="../style.css">
</head>

<body>
<div class="header header-admin">
    <img src="<%= request.getContextPath() %>/Images/cytech.png" class="logo">
    <h2>Administrateur</h2>
    <nav>
        <ul>
            <li><a href="notes_Etudiant.jsp">Aller aux notes</a></li>
        </ul>
    </nav>
    <form action="../../logout" method="Get" style="display: inline;">
        <button type="submit">Déconnexion</button>
    </form>
</div>

<div>
    <h3>Cours</h3>
    <button>Aller aux cours</button>
    <table>
        <thead>
        <tr>
            <th>Nom du Cours</th>
            <th>Professeur</th>
            <th>Inscription</th>
        </tr>
        </thead>
        <tbody>
        <% for (Inscription inscription : listInscriptions) {
            Cours cours = inscription.getCours();
        %>
        <tr>
            <td><%= cours.getNomCours()%></td>
            <td><%= cours.getPersonneByIdEnseignant().getNom() %></td>
            <td><%= inscription.getEtat()%></td>
        </tr>
        <% } %>
        </tbody>
    </table>
</div>

<div>
    <h3>Notes</h3>
    <button>Aller aux notes</button>
    <table>
        <thead>
        <tr>
            <th>Nom du Cours</th>
            <th>Professeur</th>
            <th>Notes</th>
        </tr>
        </thead>
        <tbody>
        <% for (Inscription inscription : listCoursThisEtudiant) {
            Cours cours = inscription.getCours();
        %>
        <tr>
            <td><%= cours.getNomCours()%></td>
            <td><%= cours.getPersonneByIdEnseignant().getNom() %></td>
            <td>moyenne pour cette matiere</td>
        </tr>
        <% } %>
        </tbody>
    </table>
</div>
</body>
</html>
