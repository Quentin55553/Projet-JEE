<%@ page import="org.jee.entity.Personne" %>
<%@ page import="java.util.List" %>
<%@ page import="org.jee.entity.Cours" %>
<!DOCTYPE html>
<html>
<head>
    <title>Saisie des Notes</title>
    <style>
        table {
            width: 100%;
            border-collapse: collapse;
        }
        th, td {
            border: 1px solid black;
            padding: 8px;
            text-align: left;
        }
        th {
            background-color: #f2f2f2;
        }
    </style>
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
</head>
<body>

<%
    // Retrieve course and student data from the request
    Object coursObject = request.getAttribute("cours");
    List<Personne> students = (List<Personne>) request.getAttribute("students");

    // Check if cours is null
    if (coursObject != null) {
        Cours cours = (Cours) coursObject;
%>
<h2>Cours: <%= cours.getNomCours() %></h2>

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

    <button type="submit">Soumettre les notes</button>
</form>
<%
} else {
%>
<p>Aucun étudiant à évaluer.</p>
<%
    }
} else {
%>
<p>Le cours n'a pas été trouvé.</p>
<%
    }
%>

</body>
</html>
