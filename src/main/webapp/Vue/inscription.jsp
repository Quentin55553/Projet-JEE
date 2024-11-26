<%@ page import="java.util.List" %>
<%@ page import="org.jee.entity.Cours" %>
<%@ page import="org.hibernate.Session" %>
<%@ page import="org.hibernate.SessionFactory" %>
<%@ page import="org.hibernate.cfg.Configuration" %>
<%@ page import="org.jee.entity.Cours" %>
<%@ page import="java.util.List" %>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    // Configuration et ouverture d'une session Hibernate
    SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
    Session hibernateSession = factory.openSession(); // Utilisation d'un nom différent
    List<Cours> coursList = hibernateSession.createQuery("FROM Cours", Cours.class).list();
    hibernateSession.close();
%>


<!DOCTYPE html>
<html lang="fr">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Inscription aux cours</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                margin: 0;
                padding: 0;
                background-color: #f4f4f4;
                color: #333;
            }

            .container {
                max-width: 600px;
                margin: 50px auto;
                padding: 20px;
                background-color: white;
                border-radius: 8px;
                box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            }

            h1 {
                text-align: center;
            }

            form {
                display: flex;
                flex-direction: column;
            }

            label {
                margin-bottom: 5px;
                font-weight: bold;
            }

            input {
                margin-bottom: 15px;
                padding: 10px;
                font-size: 14px;
                border: 1px solid #ccc;
                border-radius: 4px;
            }

            button {
                padding: 10px;
                font-size: 16px;
                font-weight: bold;
                background-color: #004080;
                color: white;
                border: none;
                border-radius: 4px;
                cursor: pointer;
            }

            button:hover {
                background-color: #003366;
            }
        </style>
    </head>

    <body>
        <div class="container">
            <h1>Sélectionnez vos cours</h1>

            <form action="InscriptionCoursServlet" method="post">
                <!-- Informations personnelles -->
                <label for="prenom">Prénom</label>
                <input type="text" id="prenom" name="prenom" required>

                <label for="nom">Nom</label>
                <input type="text" id="nom" name="nom" required>

                <label for="motDePasse">Mot de passe</label>
                <input type="password" id="motDePasse" name="motDePasse" required>

                <label for="contact">Contact</label>
                <input type="email" id="contact" name="contact" required>


                <label for="dateNaissance">Date de naissance</label>
                <input type="date" id="dateNaissance" name="dateNaissance" required>

                <br>

                <!-- Liste des cours -->
                <%
                    if (coursList != null && !coursList.isEmpty()) {
                        for (Cours cours : coursList) {
                %>
                <div>
                    <input type="checkbox" id="cours<%= cours.getIdCours() %>" name="coursIds[]" value="<%= cours.getIdCours() %>">
                    <label for="cours<%= cours.getIdCours() %>"><%= cours.getNomCours() %> - <%= cours.getDescription() %></label>
                </div>
                <%
                    }
                } else {
                %>
                <p>Aucun cours disponible pour le moment.</p>
                <%
                    }
                %>

                <br>

                <button type="submit">S'inscrire</button>
            </form>
        </div>
    </body>
</html>