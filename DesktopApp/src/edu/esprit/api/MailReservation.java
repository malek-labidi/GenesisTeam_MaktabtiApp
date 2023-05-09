/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.api;

import edu.esprit.entities.Evenement;
import edu.esprit.entities.login;
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
 * @author SADOK
 */
public class MailReservation {
    public static void sendEmail(login login, Evenement event) {

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
         message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(login.getUsername()));
         message.setSubject("Confirmation de réservation pour l'événement "+event.getNom());
         message.setText("Bonjour "+ login.getNom()+" "+login.getPrenom()+",\n" +
"\n" +
"Nous vous remercions de votre réservation pour l'événement " + event.getNom() +" , qui aura lieu le " +event.getDate()+ " " +event.getHeure() +" à "+event.getLieu()+". Nous sommes ravis de vous accueillir parmi nous et de vous offrir une expérience mémorable.\n" +
"\n" +
"Votre réservation a bien été enregistrée, et nous confirmons par la présente que votre place est réservée pour l'événement. Nous vous rappelons que le paiement sera exigé lors de votre arrivée à l'événement.\n" +
"\n" +
"Si vous avez des questions ou des préoccupations, n'hésitez pas à nous contacter .\n" +
"\n" +
"Nous avons hâte de vous voir à l'événement !\n" +
"\n" +
"Cordialement, "+signature);

         Transport.send(message);

         System.out.println("Mail sent successfully");

      } catch (MessagingException  | UnsupportedEncodingException e) {
         throw new RuntimeException(e);
      }
   }
}
