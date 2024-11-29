<%@ page import="org.jee.entity.Personne" %>
<%@ page import="org.jee.entity.Resultat" %>
<%@ page import="org.jee.entity.Cours" %>
<%@ page import="java.util.*" %>
<%@ page import="com.google.gson.Gson" %>
<%@ page import="com.google.gson.GsonBuilder" %>
<%@ page import="java.net.URLEncoder" %>


<!DOCTYPE html>
<html lang="fr">
    <head>
        <meta charset="UTF-8">
        <title>Notes étudiant</title>
        <link rel="stylesheet" href="../style.css">
    </head>

    <body>
    <div class="header header-etudiant">
        <img src="<%= request.getContextPath() %>/Images/cytech.png" class="logo">
        <h2>Étudiant</h2>
        <nav>
            <ul>
                <li><a href="<%= request.getContextPath() %>/MenuEtudiantServlet">Accueil</a></li>
                <li><a href="<%= request.getContextPath() %>/DemandeInscriptionServlet">Inscrire à un cours</a></li>
                <li><a href="<%= request.getContextPath() %>/DebutReleveResultatServlet">Générer le relevé de notes</a></li>
            </ul>
        </nav>
        <form action="<%= request.getContextPath() %>/logout" method="Get" style="display: inline;">
            <button type="submit">Déconnexion</button>
        </form>
    </div>
    <%if (session.getAttribute("resultats") != null && !((List<Resultat>) session.getAttribute("resultats")).isEmpty()){%>
        <%
            List<Resultat> resultats = (List<Resultat>) session.getAttribute("resultats");
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

            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
            String resultatsJson = gson.toJson(resultats);
            resultatsJson = URLEncoder.encode(resultatsJson, "UTF-8");
        %>
        <br>
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

                            // Generate a unique ID for the form
                            String formId = "coursForm_" + cours.getIdCours();
                    %>
                    <form action="<%= request.getContextPath() %>/MatiereServlet" id="<%= formId %>" method="GET">
                        <tr>
                            <td>
                                <input type="hidden" name="idCours" value="<%= cours.getIdCours() %>">
                                <a href="javascript:void(0);" onclick="document.getElementById('<%= formId %>').submit();" class="link-style">
                                    <%= cours.getNomCours() %>
                                </a>
                            </td>
                            <td><%= cours.getPersonneByIdEnseignant().getPrenom() + " " + cours.getPersonneByIdEnseignant().getNom() %></td>
                            <td><%= moyenneCoursFormatee %>/20</td>
                        </tr>
                    </form>
                    <%
                        }
                    %>
                </tbody>
            </table>
        </div>
        <br>
        <form action="${pageContext.request.contextPath}/ReleveResultatServlet" method="POST" class="no-data">
            <input type="hidden" name="moyenneGlobale" value="<%= moyenneGlobaleFormatee %>">
            <input type="hidden" name="resultatsJson" value="<%= resultatsJson %>">
            <button type="submit">Génerer un relevé de notes</button>
        </form>
    <%}else {%>
    <div>
        <h3>Pas de note</h3>
    </div>
    <%}%>
    </body>
</html>
