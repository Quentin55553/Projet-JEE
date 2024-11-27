package org.jee.Controllers;

import jakarta.servlet.http.HttpServlet;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.jee.entity.Cours;
import org.jee.entity.Personne;

import java.util.List;

public class ControleurPersonne extends HttpServlet {

    /**
     * Ouvre une session hibernate, accède à la base de données et renvoie la liste des Personne existantes.
     * */
    public static List<Personne> getPersonnesList() {

        SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        Session hibernateSession = factory.openSession();

        List<Personne> personnesList = hibernateSession.createQuery(
                "FROM Personne",
                Personne.class
        ).list();
        hibernateSession.close();

        return personnesList;
    }


    /**
     * Renvoie l'objet Personne correspondant à l'id indiquée ou null s'il l'objet n'existe pas.
     * @param id_personne
     * @return
     */
    public static Personne getPersonneByID(String id_personne){
        List<Personne> personnesList = getPersonnesList();
        for(Personne personne : personnesList){
            if (personne.getIdPersonne().equals(id_personne)){
                return personne;
            }
        }

        return null;
    }

    /**
     * Renvoie une liste des Personne dans la base de données correspondant au role indiqué.
     * @param role : 1 = étudiant, 2 = professeur, 3 = administrateur
     * @return Liste des personnes correspondant au role
     */
    public static List<Personne> getPersonnesListByRole(int role) {

        SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        Session hibernateSession = factory.openSession();

        List<Personne> personnesListByRole = hibernateSession.createQuery(
                "FROM Personne WHERE role = :role",
                Personne.class
        ).list();
        hibernateSession.close();

        return personnesListByRole;
    }

}
