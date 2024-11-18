<!DOCTYPE html>


<html>
<head>
    <%@ page contentType="text/html; charset=UTF-8" %>
    <meta charset="UTF-8">
    <title>Connexion</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f9;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }
        .container {
            width: 100%;
            max-width: 400px;
            background-color: #ffffff;
            padding: 20px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            border-radius: 8px;
            text-align: center;
        }
        .header {
            margin-bottom: 20px;
        }
        .header img {
            max-width: 100px;
            height: auto;
        }
        h1 {
            margin-bottom: 20px;
            font-size: 24px;
            color: #333;
        }
        form {
            display: flex;
            flex-direction: column;
        }
        label {
            margin-bottom: 5px;
            text-align: left;
        }
        input[type="text"], input[type="password"] {
            padding: 10px;
            margin-bottom: 15px;
            border: 1px solid #ccc;
            border-radius: 4px;
            font-size: 16px;
        }
        button {
            padding: 10px 15px;
            background-color: #007bff;
            color: #ffffff;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
        }
        button:hover {
            background-color: #0056b3;
        }
        .error-message {
            color: red;
            margin-top: 10px;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="header">
        <!-- Place your logo image file here -->
        <img src="cytech.png" alt="Logo">
    </div>
    <h1>Connexion</h1>
    <form method="post" action="login">
        <label for="idPersonne">Mail :</label>
        <input type="text" id="idPersonne" name="idPersonne" placeholder="Entrez votre mail" required>
        <label for="password">Mot de passe</label>
        <input type="password" id="password" name="password" placeholder="Entrez votre mot de passe" required>
        <button type="submit">Connexion</button>
    </form>
    <% if (request.getParameter("error") != null) { %>
    <p class="error-message">Identifiants invalides. Veuillez réessayer.</p>
    <% } %>
    <li><a href="inscription.jsp">Nouvel élève </a></li>
</div>
</body>
</html>
