/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.api;

import edu.esprit.entities.Livre;
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
 * @author Saleh
 */
public class MailLivre {
         public static void sendEmail(Utilisateur u,Livre l) {

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
         message.setSubject("Votre livre "+l.getTitre()+" a été ajouter avec succés!");
         message.setText("Cher/chère "+u.getNom() +" "+u.getPrenom()+",\n" +
"\n" +
                 "Bienvenue dans notre application \n" +
"Nous sommes ravis de vous annoncer que votre livre "+l.getTitre()+" a été ajouter a notre application ."
                 + " Nous sommes très impressionnés par la qualité de votre livre et nous sommes convaincus qu'il aura beaucoup de succès lors de la compétition.\n" +

"\n" +
"Sincèrement, \n"+"\n"+signature);

         Transport.send(message);

         System.out.println("Mail sent successfully");
         
         

      } catch (MessagingException| UnsupportedEncodingException e) {
         throw new RuntimeException(e);
      }
   }
}
