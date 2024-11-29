package org.jee.Controllers.Servlets.Professeur;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.jee.entity.Cours;
import org.jee.entity.Inscription;
import org.jee.Util.HibernateUtil;
import org.jee.entity.Personne;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Collection;
import java.util.Properties;

/**
 * La servlet gére l'acceptation ou le refus d'un postulant au cours indiqué.
 */
@WebServlet("/ManageInscriptionServlet")
public class ManageInscriptionServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String studentEmail = request.getParameter("studentEmail");
        String courseName = request.getParameter("courseName");
        String action = request.getParameter("action");
        String commentaire = request.getParameter("commentaire");

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();

            Personne student = session.get(Personne.class, studentEmail);

            if (student == null) {
                response.getWriter().println("Erreur : L'étudiant avec cet email n'a pas été trouvé.");
                return;
            }

            Cours course = (Cours) session.createQuery("FROM Cours WHERE nomCours = :courseName",Cours.class)
                    .setParameter("courseName", courseName)
                    .uniqueResult();

            if (course == null) {
                response.getWriter().println("Erreur : Le cours n'a pas été trouvé.");
                return;
            }

            Collection<Inscription> inscriptions = student.getInscriptionsByIdPersonne();

            Inscription inscriptionToUpdate = null;
            for (Inscription inscription : inscriptions) {
                if (inscription.getCours().equals(course)) {
                    inscriptionToUpdate = inscription;
                    break;
                }
            }

            if (inscriptionToUpdate == null) {
                response.getWriter().println("Erreur : L'inscription à ce cours n'a pas été trouvée.");
                return;
            }


            if ("accept".equals(action)) {
                inscriptionToUpdate.setEtat(1);  // Accepter
                sendEmail(student.getContact(), "Votre demande d'inscription a été acceptée",
                        "Bonjour,\n\nVotre demande d'inscription au cours a été acceptée.\n\nCordialement,\nCY Tech.");
            } else if ("deny".equals(action)) {
                inscriptionToUpdate.setEtat(2);  // Refuser
                sendEmail(student.getContact(), "Votre demande d'inscription a été refusée",
                        "Bonjour,\n\nVotre demande d'inscription au cours a été refusée.\n\nCordialement,\nCY Tech.");
            }


            inscriptionToUpdate.setDescriptionRefus(commentaire);

            session.merge(inscriptionToUpdate);

            tx.commit();

            response.sendRedirect(STR."\{request.getContextPath()}/InscriptionProf");

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Erreur lors de la mise à jour de l'inscription.");
        }
    }
    public void sendEmail(String to, String subject, String body) {
        String from = "cytechjeemail@gmail.com";
        String host = "smtp.gmail.com";

        // Set up system properties
        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        // Set up the email session
        javax.mail.Session session = javax.mail.Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("cytechjeemail@gmail.com", "wjvsfjprrudjbqen");
            }
        });

        try {
            // Create a default MimeMessage object
            MimeMessage message = new MimeMessage(session);

            // Set From: header field
            message.setFrom(new InternetAddress(from));

            // Set To: header field
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: header field
            message.setSubject(subject);

            // Set the actual message
            message.setText(body);

            // Send the message
            Transport.send(message);
            System.out.println("Email sent successfully to " + to);
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}
