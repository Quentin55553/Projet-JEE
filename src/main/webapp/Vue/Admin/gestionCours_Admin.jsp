<%@ page import="java.util.List" %>
<%@ page import="org.hibernate.Session" %>
<%@ page import="org.hibernate.SessionFactory" %>
<%@ page import="org.hibernate.cfg.Configuration" %>
<%@ page import="org.jee.entity.Cours" %>
<%@ page import="org.jee.Controllers.Servlets.Admin.ControleurCours" %>
<%@ page import="org.jee.entity.Personne" %>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
    // Vérifiez si l'utilisateur est connecté et est administrateur
    Personne user = (Personne) session.getAttribute("user");
    if (user == null || user.getRole() != 1) {
        // Redirigez vers la page de connexion si l'utilisateur n'est pas connecté ou n'est pas administrateur
        response.sendRedirect(request.getContextPath()+"/Vue/login.jsp");
        return;
    }
    /**
     * Afficher le tableau des cours
     * */
    List<Cours> coursList = ControleurCours.getCoursList();
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
                <li><a href="menu_admin.jsp">Accueil</a></li>
                <li><a href="inscriptionsEtudiants_Admin.jsp">Créer profil étudiant</a></li>
                <li><a href="inscriptionsProfesseurs_Admin.jsp">Créer profil professeur</a></li>
                <li><a href="gestionCours_Admin.jsp">Gestion de cours</a></li>
                <li><a href="manageInscription.jsp">Gestion des inscriptions</a></li>
                <li><a href="creationCours_Admin.jsp">Créer profil professeur</a></li>

            </ul>
        </nav>
        <form action="../../logout" method="Get" style="display: inline;">
            <button type="submit">Déconnexion</button>
        </form>
    </div>

        <div>
            <form action="creationCours_Admin.jsp" method="post">
                <input type="hidden" name="action" value="creation">
                <input  type="submit" id="creationButton" value="Creer cours">
            </form>
            <h2>Tableau des Cours</h2>
            <table>
                <thead>
                    <tr>
                        <th>Nom du Cours</th>
                        <th>Professeur</th>
                        <th>Inscription</th>
                    </tr>
                </thead>

                <tbody>

                    <% for (Cours cours : coursList) {
                    %>
                    <tr>
                        <td><%= cours.getNomCours()%></td>
                        <td><%= cours.getPersonneByIdEnseignant() %></td>
                        <td>
                            <form action="creationCours_Admin.jsp" method="post">
                                <input type="hidden" name="action" value="modification">
                                <input type="hidden" name="id_cours" value="<%= cours.getIdCours()%>">
                                <input  type="submit" class="modifButton" value="Modifier">
                            </form>
                            <form action="creationCours_Admin.jsp" method="post">
                                <input type="hidden" name="action" value="suppression">
                                <input type="hidden" name="id_cours" value="<%= cours.getIdCours()%>">
                                <input  type="submit" class="deleteButton" value="Supprimer">
                            </form>
                        </td>
                    </tr>
                    <% } %>
                </tbody>
            </table>
        </div>
    </body>
</html>
