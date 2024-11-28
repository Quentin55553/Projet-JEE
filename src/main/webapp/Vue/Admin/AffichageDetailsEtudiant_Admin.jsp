<%@ page import="org.jee.entity.Personne" %>
<%@ page import="org.jee.Controllers.Servlets.Admin.ControleurPersonne" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
        </ul>
    </nav>
    <form action="<%= request.getContextPath() %>/logout" method="Get" style="display: inline;">
        <button type="submit">Déconnexion</button>
    </form>
</div>

<% String id_etudiant = request.getParameter("id_etudiant");
    Personne etudiant = null;

    try{
        etudiant = ControleurPersonne.getPersonneByID(id_etudiant);
    }catch (Exception e){
        response.setContentType("text/html");
        response.getWriter().println(e.getMessage());
    }

    if(etudiant != null){
        %>
<table>
    <tr>
        <th>ID</th>
        <td><%= etudiant.getIdPersonne() %></td>
    </tr>
    <tr>
        <th>Nom</th>
        <td><%= etudiant.getNom() %></td>
    </tr>
    <tr>
        <th>Prénom</th>
        <td><%= etudiant.getPrenom() %></td>
    </tr>
    <tr>
        <th>Contact</th>
        <td><%= etudiant.getContact() %></td>
    </tr>
    <tr>
        <th>Contact</th>
        <td><%= etudiant.getDateNaissance() %></td>
    </tr>
    <tr>
        <th>Contact</th>
        <td><%= etudiant.getPassword() %></td>
    </tr>
</table>
    <%}%>



</body>
</html>
