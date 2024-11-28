<%@ page import="org.jee.entity.Personne" %>
<%@ page import="java.util.List" %>
<%@ page import="org.jee.entity.Cours" %>
<%@ page import="org.jee.entity.Inscription" %>
<%@ page import="java.util.Map" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    // Vérification de l'utilisateur connecté et de son rôle
    Personne user = (Personne) session.getAttribute("user");
    if (user == null || user.getRole() != 3) {
        response.sendRedirect(request.getContextPath() + "/Vue/login.jsp");
        return;
    }

    // Récupération de la liste des inscriptions (état = 0 ou 2)
    List<Inscription> inscriptions = (List<Inscription>) request.getAttribute("inscriptions");

    // Récupération des moyennes des cours et des cours
    Map<Integer, Double> courseAverages = (Map<Integer, Double>) request.getAttribute("courseAverages");
    Map<Integer, Cours> courses = (Map<Integer, Cours>) request.getAttribute("courses");
%>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Menu Étudiant</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/Vue/style.css">
</head>

<body>
<div class="header header-etudiant">
    <img src="<%= request.getContextPath() %>/Images/cytech.png" class="logo">
    <h2>Étudiant</h2>
    <nav>
        <ul>
            <li><a href="<%= request.getContextPath() %>/MenuEtudiantServlet">Accueil</a></li>
            <li><a href="<%= request.getContextPath() %>/DemandeInscriptionServlet">Inscrire à un cours</a></li>
        </ul>
    </nav>
    <form action="<%= request.getContextPath() %>/logout" method="Get" style="display: inline;">
        <button type="submit">Déconnexion</button>
    </form>
</div>

<!-- Section Inscription -->
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
        <%
            if (inscriptions != null) {
                for (Inscription inscription : inscriptions) {
                    if (inscription.getEtat() == 0 || inscription.getEtat() == 2) {
        %>
        <tr>
            <td><%= inscription.getCours().getNomCours() %></td>
            <td><%= inscription.getCours().getPersonneByIdEnseignant().getPrenom() + " " + inscription.getCours().getPersonneByIdEnseignant().getNom() %></td>
            <td><%= inscription.getEtat() == 0 ? "En attente" : "Refusé" %></td>
        </tr>
        <%
                }
            }
        } else {
        %>
        <tr>
            <td colspan="3">Aucune inscription en attente ou refusée.</td>
        </tr>
        <%
            }
        %>
        </tbody>
    </table>
</div>

<!-- Section Moyenne des Cours -->
<div>
    <h3>Moyennes des Cours</h3>
    <table>
        <thead>
        <tr>
            <th>Nom du Cours</th>
            <th>Professeur</th>
            <th>Moyenne</th>
            <th>Détails</th>
        </tr>
        </thead>
        <tbody>
        <%
            if (courseAverages != null && courses != null && !courseAverages.isEmpty()) {
                for (Map.Entry<Integer, Double> entry : courseAverages.entrySet()) {
                    int courseId = entry.getKey();
                    double average = entry.getValue();
                    Cours cours = courses.get(courseId); // Retrieve the Cours object from the map
                    if (cours != null) {
        %>
        <tr>
            <form action="<%= request.getContextPath() %>/MatiereServlet" method="GET">
                <td><%= cours.getNomCours() %></td>
                <td><%= cours.getPersonneByIdEnseignant().getNom() %></td>
                <td><%= String.format("%.2f", average) %></td>
                <td>
                    <!-- Hidden input to pass the course ID -->
                    <input type="hidden" name="idCours" value="<%= courseId %>">
                    <button type="submit">Voir Détails</button>
                </td>
            </form>
        </tr>
        <%
                }
            }
        } else {
        %>
        <tr>
            <td colspan="3">Aucune moyenne de cours disponible.</td>
        </tr>
        <%
            }
        %>
        </tbody>
    </table>
</div>

</body>
</html>
