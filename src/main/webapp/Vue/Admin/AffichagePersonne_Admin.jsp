<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="org.jee.entity.Personne" %>
<%
    // Vérifiez si l'utilisateur est connecté et est administrateur
    Personne user = (Personne) session.getAttribute("user");
    if (user == null || user.getRole() != 1) {
        // Redirigez vers la page de connexion si l'utilisateur n'est pas connecté ou n'est pas administrateur
        response.sendRedirect(request.getContextPath()+"/Vue/login.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Affichage des personnes</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/Vue/style.css">
</head>
<body>

<div class="header header-admin">
    <img src="<%= request.getContextPath() %>/Images/cytech.png" class="logo">
    <h2>Administrateur</h2>
    <nav>
        <ul>
            <li><a href="<%= request.getContextPath() %>/Vue/Admin/menu_admin.jsp">Accueil</a></li>
            <li><a href="<%= request.getContextPath() %>/Vue/Admin/inscriptionsEtudiants_Admin.jsp">Créer profil étudiant</a></li>
            <li><a href="<%= request.getContextPath() %>/Vue/Admin/inscriptionsProfesseurs_Admin.jsp">Créer profil professeur</a></li>
            <li><a href="<%= request.getContextPath() %>/Vue/Admin/gestionCours_Admin.jsp">Gestion de cours</a></li>
            <li><a href="<%= request.getContextPath() %>/Vue/Admin/AffichagePersonne_Admin.jsp">Affichage de professeur et étudiant</a></li>
        </ul>
    </nav>
    <form action="<%= request.getContextPath() %>/logout" method="Get" style="display: inline;">
        <button type="submit">Déconnexion</button>
    </form>
</div>

<h1>Afficher la liste des Étudiants ou Professeurs</h1>

<form action="<%= request.getContextPath() %>/AffichagePersonne" method="post">
    <label>
        <input type="radio" name="typePersonne" value="etudiant" required> Étudiants
    </label>
    <label>
        <input type="radio" name="typePersonne" value="professeur" required> Professeurs
    </label>
    <button type="submit">Voir la liste</button>
</form>

<hr>

<%
    String typePersonne = request.getParameter("typePersonne");
    List<Personne> listePersonne = (List<Personne>) request.getAttribute("liste");

    if(listePersonne != null){
%>

<% if("etudiant".equals(typePersonne)){%>
<h2>Liste des étudiants</h2>
<% } else if ("professeur".equals(typePersonne)){ %>
<h2> Liste des professeurs</h2><%}%>

<table>
    <thead>
    <tr>
        <form action="<%= request.getContextPath() %>/AffichagePersonne" method="POST">
        <td><input type="text" name="filtre_id"></td>
        <td><input type="text" name="filtre_nom"></td>
        <td><input type="text" name="filtre_prenom"></td>
        <td><input type="text" name="filtre_contact"></td>
            <input type="hidden" name="typePersonne" value="<%= typePersonne %>">
            <input type="hidden" name="form_filtre" value="true">
            <td><button type="submit">Filtrer</button></td>
        </form>
    </tr>
    <tr>
        <th>ID</th>
        <th>Nom</th>
        <th>Prénom</th>
        <th>Contact</th>
        <th></th>
    </tr>
    </thead>
    <tbody>
    <%
        for (Personne p : listePersonne){
            String id_etudiant = p.getIdPersonne();
            String lienDetails = "/Vue/Admin/AffichageDetailsEtudiant_Admin.jsp?id_etudiant=" + id_etudiant;
    %>
    <tr>
        <td><a href=<%=lienDetails%>><%= id_etudiant%></a></td>
        <td><%= p.getNom() %></td>
        <td><%= p.getPrenom()%></td>
        <td><%= p.getContact()%></td>
    </tr>

    <% }
    }else{ %>

    <p>Choisissez les personnes à afficher.</p>

    <%}%>
    </tbody>
</table>
</body>
</html>
