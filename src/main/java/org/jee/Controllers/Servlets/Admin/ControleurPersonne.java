package org.jee.Controllers.Servlets.Admin;

import jakarta.servlet.http.HttpServlet;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.jee.entity.Cours;
import org.jee.entity.Personne;

import java.util.List;

/**
 * La servlet est utilisée pour obtenir des informations sur les objets de la classe Personne
 */
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
        ).setParameter("role", role).list();
        hibernateSession.close();

        return personnesListByRole;
    }

    /**
     * Permet d'obtenir une liste d'étudiants correspondant aux filtres par la gauche.
     */
    public static List<Personne> getPersonnesListByFilter(int role, String like_id, String like_nom, String like_prenom, String like_contact) {

        SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        Session hibernateSession = factory.openSession();
        //Il ne faut pas faire de recherche par LIKE si les paramètres sont null
        List<Personne> personnesListByFilter = hibernateSession.createQuery(
                        "FROM Personne WHERE role = :role " +
                                "AND (:id IS NULL OR idPersonne LIKE :id) " +
                                "AND (:nom IS NULL OR nom LIKE :nom) " +
                                "AND (:prenom IS NULL OR prenom LIKE :prenom) " +
                                "AND (:contact IS NULL OR contact LIKE :contact)",
                        Personne.class
                )
                .setParameter("role", role)
                .setParameter("id", like_id != null && !like_id.isEmpty() ? "%" + like_id + "%" : null)
                .setParameter("nom", like_nom != null && !like_nom.isEmpty() ? "%" + like_nom + "%" : null)
                .setParameter("prenom", like_prenom != null && !like_prenom.isEmpty() ? "%" + like_prenom + "%" : null)
                .setParameter("contact", like_contact != null && !like_contact.isEmpty() ? "%" + like_contact + "%" : null)
                .list();

        return personnesListByFilter;
    }

}
