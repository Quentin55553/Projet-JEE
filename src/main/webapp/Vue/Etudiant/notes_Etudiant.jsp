<%@ page import="org.jee.entity.Personne" %>
<%@ page import="org.jee.entity.Resultat" %>
<%@ page import="org.jee.entity.Cours" %>
<%@ page import="java.util.*" %>


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

                if (!coursNotesMap.containsKey(cours)) {
                    coursNotesMap.put(cours, new ArrayList<>(List.of(resultat)));

                } else {
                    coursNotesMap.get(cours).add(resultat);
                }

                sommeNotes += resultat.getNote();
            }

            float moyenneGlobale = sommeNotes / total;
            java.text.DecimalFormat df = new java.text.DecimalFormat("#.##");
            String moyenneGlobaleFormatee = df.format(moyenneGlobale);
        %>

        <div class="header">
            <h2>Menu étudiant : <%= etudiant.getPrenom() + " " + etudiant.getNom() %></h2>
            <button>Déconnexion</button>
        </div>

        <br>

        <button>Génerer un relevé de notes</button>

        <div>
            <h3>Moyenne des notes : <%= moyenneGlobaleFormatee %>/20</h3>
        </div>

        <div>
            <h3>Notes</h3>
            <table>
                <thead>
                    <tr>
                        <th>Nom du Cours</th>
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
