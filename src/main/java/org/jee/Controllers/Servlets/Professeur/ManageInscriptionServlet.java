package org.jee.Controllers.Servlets.Professeur;

import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
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
import com.sendgrid.*;
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
                        "Bonjour,\n\nVotre demande d'inscription au cours "+courseName+" a été acceptée.\nCommentaire du prof :"+commentaire+"\nCordialement,\nCY Tech.");
            } else if ("deny".equals(action)) {
                inscriptionToUpdate.setEtat(2);  // Refuser
                sendEmail(student.getContact(), "Votre demande d'inscription a été refusée",
                        "Bonjour,\n\nVotre demande d'inscription au cours "+courseName+" a été refusée.\nCommentaire du prof :"+commentaire+"\nCordialement,\nCY Tech.");
            }


            inscriptionToUpdate.setDescriptionRefus(commentaire);

            session.merge(inscriptionToUpdate);

            tx.commit();

            response.sendRedirect(request.getContextPath()+"/InscriptionProf");

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Erreur lors de la mise à jour de l'inscription.");
        }
    }


    public void sendEmail(String to, String subject, String body) {
        Email from = new Email("cytechjeemail@gmail.com");
        Email recipient = new Email(to);
        Content content = new Content("text/plain", body);
        Mail mail = new Mail(from, subject, recipient, content);

        SendGrid sg = new SendGrid("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");
        Request request = new Request();

        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println("Status Code: " + response.getStatusCode());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
