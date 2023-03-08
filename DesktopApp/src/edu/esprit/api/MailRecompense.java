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
public class MailRecompense {
         public static void sendEmail(Utilisateur u, Competition c ) {

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
         message.setSubject("Félicitations pour votre victoire au quiz de "+ c.getNom() +" et livraison de votre prix");
         message.setText("Mme/Mr "+u.getPrenom()+" "+u.getNom()+",\n" +
"\n" +
"Nous sommes ravis de vous informer que vous avez remporté la compétition"+c.getNom()+" organisée par notre entreprise."
        + " Votre participation a été exceptionnelle et nous tenons à vous féliciter pour votre victoire.\n" +
"\n" +
"En reconnaissance de votre succès, nous sommes heureux de vous offrir "+c.getRecompense()+", "
        + "qui sera livré chez vous dans les prochains jours. Notre équipe de livraison prendra contact avec vous pour organiser "
        + "la livraison à l'adresse que vous nous avez fournie.\n" +
"\n" +
"Nous espérons que vous serez ravi(e) de recevoir votre prix et que vous en profiterez pleinement."
        + " Nous tenons également à vous remercier de votre participation à notre quiz et espérons que vous avez apprécié l'expérience autant que nous.\n" +
"\n" +
"Félicitations encore pour votre réussite et merci de votre engagement envers notre entreprise.\n" +
"\n" +
"Cordialement,\n\n"+signature);

         Transport.send(message);

         System.out.println("Mail sent successfully");
         
         

      } catch (MessagingException| UnsupportedEncodingException e) {
         throw new RuntimeException(e);
      }
   }
    
}
