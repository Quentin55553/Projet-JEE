<%@ page import="java.util.List" %>
<%@ page import="org.hibernate.Session" %>
<%@ page import="org.hibernate.SessionFactory" %>
<%@ page import="org.hibernate.cfg.Configuration" %>
<%@ page import="org.jee.entity.Cours" %>
<%@ page import="org.jee.Controllers.ControleurCours" %>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
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
        <div class="header">
            <h2>Administrateur</h2>
            <button>Déconnexion</button>
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
