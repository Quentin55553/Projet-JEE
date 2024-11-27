package org.jee.Controllers;


import jakarta.servlet.http.HttpServlet;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.jee.entity.Cours;

import java.util.List;
import java.util.Map;


public class ControleurCours extends HttpServlet {

    private Integer current_cours_id;

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

    public static Cours getCoursByID(Integer id_cours){
        List<Cours> coursList = getCoursList();
        for(Cours cours : coursList){
            if (Integer.valueOf(cours.getIdCours()).equals(id_cours)){
                return cours;
            }
        }

        return null;
    }

    public Integer getCurrent_cours_id() {
        return current_cours_id;
    }

    public void setCurrent_cours_id(Integer current_cours_id) {
        this.current_cours_id = current_cours_id;
    }
}
