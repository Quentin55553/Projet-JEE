package org.jee.Controllers.Servlets.Professeur;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
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
import org.jee.entity.Resultat;
import org.jee.entity.Cours;
import org.jee.entity.Personne;
import org.jee.Util.HibernateUtil;

import java.io.IOException;
import java.util.Enumeration;

@WebServlet("/UpdateNotesServlet")
public class updateNoteServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            int idCours = Integer.parseInt(request.getParameter("idCours"));

            Enumeration<String> parameterNames = request.getParameterNames();
            while (parameterNames.hasMoreElements()) {
                String paramName = parameterNames.nextElement();
                if (paramName.startsWith("note_")) {
                    String studentEmail = paramName.substring(5);
                    String noteStr = request.getParameter(paramName);

                    double note = Double.parseDouble(noteStr);

                    System.out.println("Student Email: " + studentEmail + ", Note: " + note);

                    Resultat resultat = new Resultat();

                    Personne etudiant = session.get(Personne.class, studentEmail);
                    Cours cours = session.get(Cours.class, idCours);

                    if (etudiant != null && cours != null) {
                        resultat.setPersonneByIdEtudiant(etudiant);
                        resultat.setCoursByIdCours(cours);
                        resultat.setNote(note);

                        session.persist(resultat);
                    }
                    sendEmail(etudiant.getContact(),"Nouvelle note","Bonjour,\n\nVous avais une nouvelle note dans le cours : "+cours.getNomCours()+".\nNote :"+note+"\nCordialement,\nCY Tech.");
                }
            }
            transaction.commit();


            response.sendRedirect("Vue/Professeur/confirmation.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erreur lors de la mise à jour des notes.");
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

