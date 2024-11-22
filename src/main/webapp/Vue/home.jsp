<%@ page import="org.jee.entity.Personne" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    // Vérifiez si l'utilisateur est connecté
    Personne user = (Personne) session.getAttribute("user");
    if (user == null) {
        // Redirigez vers la page de connexion si l'utilisateur n'est pas connecté
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Accueil - Gestion de Scolarité</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f4f4f4;
            color: #333;
        }
        header {
            background-color: #004080;
            color: white;
            padding: 15px 20px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        header h1 {
            margin: 0;
        }
        nav {
            margin-left: 20px;
        }
        nav ul {
            list-style: none;
            padding: 0;
            margin: 0;
            display: flex;
        }
        nav ul li {
            margin-right: 15px;
        }
        nav ul li a {
            color: white;
            text-decoration: none;
        }
        nav ul li a:hover {
            text-decoration: underline;
        }
        .logout-link {
            background-color: #d9534f;
            padding: 5px 10px;
            color: white;
            text-decoration: none;
            border-radius: 5px;
            margin-left: 15px;
        }
        .logout-link:hover {
            background-color: #c9302c;
        }
        .container {
            padding: 20px;
            max-width: 800px;
            margin: 20px auto;
            background-color: white;
            border-radius: 8px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }
    </style>
</head>
<body>
<header>
    <div>
        <h1>Bienvenue, <%= user.getPrenom() %> <%= user.getNom() %>!</h1>
        <p>Vous êtes connecté en tant que :
            <%
                String roleText = "Utilisateur non reconnu";
                switch(user.getRole()) {
                    case 1: roleText = "Administrateur"; break;
                    case 2: roleText = "Enseignant"; break;
                    case 3: roleText = "Étudiant"; break;
                }
            %>
            <strong><%= roleText %></strong>
        </p>
    </div>
    <nav>
        <ul>
            <%
                if (user.getRole() == 1) { // ADMIN
            %>
            <li><a href="admin/manageUsers.jsp">Gérer les utilisateurs</a></li>
            <li><a href="admin/manageCourses.jsp">Gérer les cours</a></li>
            <li><a href="admin/manageInscription.jsp">Gérer les inscriptions</a></li>
            <%
            } else if (user.getRole() == 2) { // ENSEIGNANT
            %>
            <li><a href="teacher/viewCourses.jsp">Voir mes cours</a></li>
            <li><a href="teacher/enterGrades.jsp">Saisir des notes</a></li>
            <%
            } else if (user.getRole() == 3) { // ÉTUDIANT
            %>
            <li><a href="student/viewCourses.jsp">Mes cours</a></li>
            <li><a href="student/viewResults.jsp">Voir mes résultats</a></li>
            <%
                }
            %>
        </ul>
    </nav>
    <a href="logout" class="logout-link">Se déconnecter</a>
</header>
<div class="container">
    <!-- Contenu principal ici -->
    <p>Contenu de la page spécifique à l'utilisateur...</p>
</div>
</body>
</html>
