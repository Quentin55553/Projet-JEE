<%@ page import="org.jee.entity.Personne" %>
<%@ page import="org.jee.entity.Resultat" %>
<%@ page import="org.jee.entity.Cours" %>
<%@ page import="java.util.*" %>
<%@ page import="com.google.gson.Gson" %>


<!DOCTYPE html>
<html lang="fr">
    <head>
        <meta charset="UTF-8">
        <title>Notes étudiant</title>
        <link rel="stylesheet" href="../style.css">
    </head>

    <body>
        <%
            List<Resultat> resultats = (List<Resultat>) request.getAttribute("resultats");
            Map<Cours, List<Resultat>> coursNotesMap = new HashMap<>();

            Personne etudiant = resultats.get(0).getPersonneByIdEtudiant();

            float sommeNotes = 0;
            float total = resultats.size();

            for (Resultat resultat : resultats) {
                Cours cours = resultat.getCoursByIdCours();
                coursNotesMap.computeIfAbsent(cours, k -> new ArrayList<>()).add(resultat);
                sommeNotes += resultat.getNote();
            }

            float moyenneGlobale = sommeNotes / total;
            java.text.DecimalFormat df = new java.text.DecimalFormat("#.##");
            String moyenneGlobaleFormatee = df.format(moyenneGlobale);

            Gson gson = new Gson();
            String resultatsJson = gson.toJson(resultats);
        %>

        <div class="header">
            <h2>Menu étudiant : <%= etudiant.getPrenom() + " " + etudiant.getNom() %></h2>
            <form action="logout_" method="GET">
                <button type="submit">Déconnexion</button>
            </form>
        </div>

        <br>

        <form action="${pageContext.request.contextPath}/ReleveResultatServlet" method="POST">
            <input type="hidden" name="moyenneGlobale" value="<%= moyenneGlobaleFormatee %>">
            <input type="hidden" name="resultatsJson" value='<%= resultatsJson %>'>
            <button type="submit">Génerer un relevé de notes</button>
        </form>

        <div>
            <h3>Moyenne des notes : <%= moyenneGlobaleFormatee %>/20</h3>
        </div>

        <div>
            <h3>Notes</h3>
            <table>
                <thead>
                    <tr>
                        <th>Nom du cours</th>
                        <th>Professeur</th>
                        <th>Moyenne</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        for (Map.Entry<Cours, List<Resultat>> entry : coursNotesMap.entrySet()) {
                            Cours cours = entry.getKey();
                            List<Resultat> notesCours = entry.getValue();

                            float sommeNotesCours = 0;
                            for (Resultat resultat : notesCours) {
                                sommeNotesCours += resultat.getNote();
                            }
                            float moyenneCours = sommeNotesCours / notesCours.size();
                            String moyenneCoursFormatee = df.format(moyenneCours);
                    %>
                    <tr>
                        <td><a href="detailsCours?idEtudiant=<%= etudiant.getIdPersonne() %>&idCours=<%= cours.getIdCours() %>"><%= cours.getNomCours() %></a></td>
                        <td><%= cours.getPersonneByIdEnseignant().getPrenom() + " " + cours.getPersonneByIdEnseignant().getNom() %></td>
                        <td><%= moyenneCoursFormatee %>/20</td>
                    </tr>
                    <%
                        }
                    %>
                </tbody>
            </table>
        </div>
    </body>
</html>
