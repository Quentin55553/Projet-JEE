<!DOCTYPE html>
<html>
<head>
    <title>Confirmation</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            text-align: center;
            margin-top: 100px;
            background-color: #f9f9f9;
        }
        .container {
            max-width: 600px;
            margin: auto;
            background: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
        }
        h1 {
            color: #4CAF50;
        }
        p {
            font-size: 16px;
            color: #555;
        }
        a {
            color: #4CAF50;
            text-decoration: none;
            font-weight: bold;
        }
        a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Merci!</h1>
    <p>Les notes ont été soumises avec succès.</p>
    <p><a href="<%= request.getContextPath() %>/Vue/Professeur/menu_Professeur.jsp">Retourner à l'accueil</a></p>
</div>
</body>
</html>
