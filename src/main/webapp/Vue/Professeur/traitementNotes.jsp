<%@ page import="org.jee.entity.Personne" %>
<%@ page import="java.util.List" %>
<%@ page import="org.jee.entity.Cours" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Saisie des Notes</title>
    <script>
        // Validate that all notes are filled before form submission
        function validateForm() {
            var inputs = document.querySelectorAll('input[type="number"]');
            for (var i = 0; i < inputs.length; i++) {
                if (inputs[i].value === "") {
                    alert("Veuillez saisir toutes les notes avant de soumettre.");
                    return false;
                }
            }
            return true;
        }
    </script>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/Vue/style.css">
</head>
<body>
<!-- Header avec menu de navigation -->
<div class="header header-professeur">
    <img src="<%= request.getContextPath() %>/Images/cytech.png" class="logo">
    <h2>Professeur</h2>
    <nav>
        <ul>
            <li><a href="<%= request.getContextPath() %>/Vue/Professeur/menu_Professeur.jsp">Accueil</a></li>
            <li><a href="<%= request.getContextPath() %>/InscriptionProf">Inscription Professeur</a></li>
            <li><a href="<%= request.getContextPath() %>/Servlet_Debut_Note_Prof">Saisie de Notes</a></li>
        </ul>
    </nav>
    <form action="<%= request.getContextPath() %>/logout" method="Get" style="display: inline;">
        <button type="submit">Déconnexion</button>
    </form>
</div>

<%
    // Retrieve course and student data from the request
    Object coursObject = request.getAttribute("cours");
    List<Personne> students = (List<Personne>) request.getAttribute("students");

    // Check if cours is null
    if (coursObject != null) {
        Cours cours = (Cours) coursObject;
%>
<h2 class="no-data">Cours: <%= cours.getNomCours() %></h2>

<%
    if (students != null && !students.isEmpty()) {
%>
<form action="<%= request.getContextPath() %>/UpdateNotesServlet" method="post" onsubmit="return validateForm();">
    <table>
        <thead>
        <tr>
            <th>Nom</th>
            <th>Prénom</th>
            <th>Email</th>
            <th>Note (0-20)</th>
        </tr>
        </thead>
        <tbody>
        <%
            for (Personne student : students) {
        %>
        <tr>
            <td><%= student.getNom() %></td>
            <td><%= student.getPrenom() %></td>
            <td><%= student.getIdPersonne() %></td>
            <td>
                <input type="number"
                       name="note_<%= student.getIdPersonne() %>"
                       min="0"
                       max="20"
                       required>
            </td>
        </tr>
        <%
            }
        %>
        </tbody>
    </table>

    <!-- Hidden field to pass the course ID -->
    <input type="hidden" name="idCours" value="<%= cours.getIdCours() %>">
    <br>
    <div style="text-align: center; margin-left: 10px; margin-top: 10px;"> <!-- Décalage vers la gauche et saut de ligne -->
        <button type="submit">Soumettre les notes</button>
    </div>
</form>
<%
} else {
%>
<p class="no-data">Aucun étudiant à évaluer.</p>
<%
    }
} else {
%>
<p class="no-data">Le cours n'a pas été trouvé.</p>
<%
    }
%>

</body>
</html>
