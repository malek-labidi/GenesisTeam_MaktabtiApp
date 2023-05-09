/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.api;

import edu.esprit.entities.Evenement;
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
 * @author wassi
 */
public class emailwhenregistration {
             public static void sendEmail(Utilisateur u) {
        

      final String username = "maktabti10@gmail.com";
      final String password = "gjq egd dtf lhl jeto";

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
          message.setSubject("Création de compte effecuté avec succés");
         message.setText("Cher(e) "+u.getNom()+" "+u.getPrenom()+","
+ "\n\nNous sommes ravis de vous informer que votre compte a été créé avec succès sur notre plateforme. Votre adresse email "+u.getEmail()+" a été enregistrée avec succès."
+ "\n\nNous vous remercions de votre confiance et espérons que vous apprécierez l'ensemble des fonctionnalités proposées sur notre site."
+ "\n\nCordialement,\n\n"+signature);

         Transport.send(message);

         System.out.println("Mail sent successfully");

      } catch (MessagingException  | UnsupportedEncodingException e) {
         throw new RuntimeException(e);
      }
   }
}
