<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Confirmation</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/Vue/style.css">
</head>
<body>
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
<div class="container">
    <br><br><br><br><br><br><br><br>
    <h1 class="no-data">Merci!</h1>
    <p class="no-data">Les notes ont été soumises avec succès.</p>
</div>
</body>
</html>
