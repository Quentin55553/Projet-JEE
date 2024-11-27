<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Cours disponibles pour inscription</title>
</head>
<body>
<h1>Cours disponibles pour inscription</h1>
<table border="1">
    <thead>
    <tr>
        <th>Nom du cours</th>
        <th>Description</th>
        <th>Date de début</th>
        <th>Date de fin</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="cours" items="${coursNonInscrits}">
        <tr>
            <td>${cours.nomCours}</td>
            <td>${cours.description}</td>
            <td>${cours.dateDebut}</td>
            <td>${cours.dateFin}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
