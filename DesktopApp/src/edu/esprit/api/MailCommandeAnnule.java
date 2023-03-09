/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.api;

import edu.esprit.entities.Commande;
import edu.esprit.entities.Evenement;
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
 * @author abdelazizlahmar
 */
public class MailCommandeAnnule {
     public static void sendEmail(Commande commande) {
        

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
         message.setRecipients(Message.RecipientType.TO,InternetAddress.parse("soaspon@gmail.com"));
         message.setSubject("Annulation de la commande ");
         message.setText("Bonjour ,\n" +
"\n" +
"Nous tenons à vous informer que l'état de votre commande a été mis à jour. Veuillez trouver ci-dessous le nouvel état de votre commande :\n" +
"\n" + commande.getEtat().toString()+"\n" +
"Si vous avez des questions ou des préoccupations concernant votre commande, n'hésitez pas à nous contacter. Nous sommes à votre disposition pour vous aider.\n" +
"\n" +
"Cordialement,"+signature);

         Transport.send(message);

         System.out.println("Mail sent successfully");

      } catch (MessagingException| UnsupportedEncodingException e) {
         throw new RuntimeException(e);
      }
   }
}
