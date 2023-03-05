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
import javax.mail.Authenticator;

/**
 *
 * @author admin
 */
public class MailLivreAjouterACompetition {
    
     public static void sendEmail(Utilisateur u, Competition c , String titreLivre) {

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
         message.setSubject("Votre livre "+titreLivre+" a été sélectionné pour participer au concours de quiz!");
         message.setText("Cher/chère "+u.getNom() +" "+u.getPrenom()+",\n" +
"\n" +
"Nous sommes ravis de vous annoncer que votre livre "+titreLivre+" a été sélectionné pour participer à notre concours de quiz "+c.getNom()+"."
                 + " Nous sommes très impressionnés par la qualité de votre livre et nous sommes convaincus qu'il aura beaucoup de succès lors de la compétition.\n" +
"\n" +
"Le concours commencera le "+c.getDate_debut()+" et se terminera le "+c.getDate_fin()+". Les participants devront répondre à des questions concernant "
                 + "votre livre pour avoir une chance de gagner de superbes prix.\n" +
"\n" +
"Nous tenons à vous remercier pour votre travail acharné et votre engagement envers l'excellence littéraire."
                 + " Nous sommes convaincus que votre livre inspirera de nombreux lecteurs à participer au concours.\n" +
"\n" +
"Nous vous souhaitons bonne chance et sommes impatients de voir votre livre briller lors de la compétition.\n" +
"\n" +
"Sincèrement, \n"+"\n"+signature);

         Transport.send(message);

         System.out.println("Mail sent successfully");
         
         

      } catch (MessagingException| UnsupportedEncodingException e) {
         throw new RuntimeException(e);
      }
   }
    
    
}
