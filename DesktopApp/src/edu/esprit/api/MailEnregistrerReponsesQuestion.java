/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.api;

import edu.esprit.entities.Competition;
import edu.esprit.entities.Utilisateur;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author admin
 */
public class MailEnregistrerReponsesQuestion {
    
    public static void sendEmail(Utilisateur u,Competition c) {

      final String username = "maktabti10@gmail.com";
      final String password = "dae rta agl jjg igf w";

      Properties props = new Properties();
      props.put("mail.smtp.auth", "true");
      props.put("mail.smtp.starttls.enable", "true");
      props.put("mail.smtp.host", "smtp.gmail.com");
      props.put("mail.smtp.port", "587");

      Session session = Session.getInstance(props,
         new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
               return new PasswordAuthentication(username, password);
            }
         });

      try {

          String signature = "\n\n-- \nMaktabti Application \nNuméro de téléphone : +216 52 329 813 \nAdresse e-mail : maktabti10@gmail.com \nSite web : www.maktabti.com";
          
         Message message = new MimeMessage(session);
         message.setFrom(new InternetAddress(username,"Maktabti Application"));
         message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(u.getEmail()));
         message.setSubject("Confirmation de participation a la compétition : "+c.getNom());
         message.setText("Cher/Chère "+u.getNom()+" "+u.getPrenom()+",\n" +
"\n" +
"Nous tenons à vous remercier d'avoir participé à notre compétition. "
                 + "Nous sommes ravis que vous ayez décidé de participer et nous espérons que vous avez trouvé l'expérience enrichissante.\n" +
"\n" +
"Nous sommes heureux de vous informer que vos réponses ont été enregistrées avec succès. "
                 + "Nous avons reçu votre formulaire de réponse et nous sommes impatients de vous donner les résultats dès que possible."
                 + " Nous vous contacterons dès que nous aurons terminé d'évaluer toutes les réponses.\n" +
"\n" +
"Encore une fois, merci d'avoir participé et d'avoir montré votre intérêt pour notre entreprise. "
                 + "Nous espérons que vous avez apprécié l'expérience et nous sommes impatients de vous proposer d'autres activités intéressantes à l'avenir.\n" +
"\n" +
"Sincèrement,\n"+"\n"+signature);

         Transport.send(message);

         System.out.println("Mail sent successfully");
         
         

      } catch (MessagingException| UnsupportedEncodingException e) {
         throw new RuntimeException(e);
      }
   }
}
