<%@ page import="java.util.List" %>
<%@ page import="org.hibernate.Session" %>
<%@ page import="org.hibernate.SessionFactory" %>
<%@ page import="org.hibernate.cfg.Configuration" %>
<%@ page import="org.jee.Data.Inscription" %>
<%@ page import="org.jee.Data.Cours" %>
<%@ page import="org.jee.Data.Personne" %>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    // Vérifiez si l'utilisateur est connecté et est administrateur
    Personne user = (Personne) session.getAttribute("user");
    if (user == null || user.getRole() != 1) {
        // Redirigez vers la page de connexion si l'utilisateur n'est pas connecté ou n'est pas administrateur
        response.sendRedirect(request.getContextPath()+"/Vue/login.jsp");
        return;
    }

    // Configuration et ouverture d'une session Hibernate
    SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
    Session hibernateSession = factory.openSession();

    // Récupération des inscriptions avec état = 1 en utilisant JOIN FETCH pour éviter LazyInitializationException
    List<Inscription> inscriptionsList = hibernateSession.createQuery(
            "SELECT DISTINCT i FROM Inscription i LEFT JOIN FETCH i.cours WHERE i.etat = 1",
            Inscription.class
    ).list();
    hibernateSession.close();
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

        <div class="container">
            <h1>Gérer les Inscriptions</h1>
            <table>
                <thead>
                    <tr>
                        <th>Nom de l'Étudiant</th>
                        <th>Prénom de l'Étudiant</th>
                        <th>Cours</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        if (inscriptionsList != null && !inscriptionsList.isEmpty()) {
                            for (Inscription inscription : inscriptionsList) {
                                Personne etudiant = inscription.getPersonneByIdEtudiant();
                                List<Cours> coursList = (List<Cours>) inscription.getCours(); // Les cours sont chargés grâce à JOIN FETCH
                    %>
                    <tr>
                        <td><%= etudiant.getNom() %></td>
                        <td><%= etudiant.getPrenom() %></td>
                        <td>
                            <ul>
                                <%
                                    for (Cours cours : coursList) {
                                %>
                                <li><%= cours.getNomCours() %></li>
                                <%
                                    }
                                %>
                            </ul>
                        </td>
                        <td>
                            <form action="UpdateInscriptionServlet" method="post" style="margin: 0;">
                                <input type="hidden" name="idInscription" value="<%= inscription.getIdInscription() %>">
                                <button type="submit" class="btn">Valider (Passer à l'état 2)</button>
                            </form>
                        </td>
                    </tr>
                    <%
                        }
                    } else {
                    %>
                    <tr>
                        <td colspan="4">Aucune inscription trouvée avec l'état 1.</td>
                    </tr>
                    <%
                        }
                    %>
                </tbody>
            </table>
        </div>
    </body>
</html>
