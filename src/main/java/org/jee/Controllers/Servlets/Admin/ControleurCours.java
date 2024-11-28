package org.jee.Controllers.Servlets.Admin;


import jakarta.servlet.http.HttpServlet;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.jee.entity.Cours;

import java.util.List;
import java.util.Map;

/**
 * La servlet est utilisée pour obtenir des informations sur les objets de la classe Cours
 */
public class ControleurCours extends HttpServlet {
    /**
     * Ouvre une session hibernate, accède à la base de données et renvoie la liste des cours existant.
     * */
    public static List<Cours> getCoursList() {

        SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        Session hibernateSession = factory.openSession();

        List<Cours> coursList = hibernateSession.createQuery(
                "FROM Cours",
                Cours.class
        ).list();
        hibernateSession.close();

        return coursList;
    }

    /**
     * @param id_cours
     * @return L'objet Cours associé à l'id.
     */
    public static Cours getCoursByID(Integer id_cours){
        List<Cours> coursList = getCoursList();
        for(Cours cours : coursList){
            if (Integer.valueOf(cours.getIdCours()).equals(id_cours)){
                return cours;
            }
        }

        return null;
    }
}
