<%@ page import="org.jee.entity.Personne" %>
<%@ page import="org.jee.entity.Resultat" %>
<%@ page import="org.jee.entity.Cours" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.SimpleDateFormat" %>
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
            List<Resultat> resultats = new ArrayList<>();
            Personne professeur1 = new Personne();
            professeur1.setIdPersonne("1");
            professeur1.setNom("Terrieur");
            professeur1.setPrenom("Alex");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date utilDate = sdf.parse("2005-01-01");
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            professeur1.setDateNaissance(sqlDate);
            professeur1.setContact("marie@gmail.com");
            professeur1.setRole(2);
            professeur1.setPassword("ae");

            Personne professeur2 = new Personne();
            professeur2.setIdPersonne("2");
            professeur2.setNom("Terrieur");
            professeur2.setPrenom("Alain");
            Date utilDateNaissance2 = sdf.parse("2002-01-01");
            professeur2.setDateNaissance(new java.sql.Date(utilDateNaissance2.getTime()));
            professeur2.setContact("lestrade@gmail.com");
            professeur2.setRole(2);
            professeur2.setPassword("ae");

            Personne etudiant1 = new Personne();
            etudiant1.setIdPersonne("3");
            etudiant1.setNom("Fillion");
            etudiant1.setPrenom("Quentin");
            Date utilDateNaissance3 = sdf.parse("2003-05-03");
            etudiant1.setDateNaissance(new java.sql.Date(utilDateNaissance3.getTime()));
            etudiant1.setContact("quentin@gmail.com");
            etudiant1.setRole(1);
            etudiant1.setPassword("ae");

            Cours cours1 = new Cours();
            cours1.setIdCours(1);
            cours1.setNomCours("Mathématiques");
            cours1.setDescription("cours de maths classiques");
            Date utilDateDebut1 = sdf.parse("2024-11-29");
            Date utilDateFin1 = sdf.parse("2024-12-29");
            cours1.setDateDebut(new java.sql.Date(utilDateDebut1.getTime()));
            cours1.setDateFin(new java.sql.Date(utilDateFin1.getTime()));
            cours1.setPersonneByIdEnseignant(professeur1);

            Cours cours2 = new Cours();
            cours2.setIdCours(2);
            cours2.setNomCours("Anglais");
            cours2.setDescription("cours d'anglais classique");
            Date utilDateDebut2 = sdf.parse("2024-11-29");
            Date utilDateFin2 = sdf.parse("2024-12-29");
            cours2.setDateDebut(new java.sql.Date(utilDateDebut2.getTime()));
            cours2.setDateFin(new java.sql.Date(utilDateFin2.getTime()));
            cours2.setPersonneByIdEnseignant(professeur2);

            Resultat resultat1 = new Resultat();
            resultat1.setIdResultat(1);
            resultat1.setNote(14.5);
            resultat1.setCoursByIdCours(cours1);
            resultat1.setPersonneByIdEtudiant(etudiant1);

            Resultat resultat2 = new Resultat();
            resultat2.setIdResultat(2);
            resultat2.setNote((double) 12);
            resultat2.setCoursByIdCours(cours1);
            resultat2.setPersonneByIdEtudiant(etudiant1);

            Resultat resultat3 = new Resultat();
            resultat3.setIdResultat(3);
            resultat3.setNote((double) 17);
            resultat3.setCoursByIdCours(cours2);
            resultat3.setPersonneByIdEtudiant(etudiant1);

            HttpSession httpSession = request.getSession();
            httpSession.setAttribute("user", etudiant1);
            resultats.add(resultat1);
            resultats.add(resultat2);
            resultats.add(resultat3);

            //List<Resultat> resultats = (List<Resultat>) request.getAttribute("resultats");

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
