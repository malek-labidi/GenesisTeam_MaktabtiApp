/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.api;

import edu.esprit.entities.Commande;
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

/**
 *
 * @author abdelazizlahmar
 */
public class MailCommandevalide {
     public static void sendEmail(Commande commande, login Login) {

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
            message.setFrom(new InternetAddress(username, "Maktabti Application"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(Login.getUsername()));
            message.setSubject("Confirmation de commande - "+commande.getId_commande());
            message.setText("Cher/chère"+Login.getNom()+" "+Login.getPrenom() +",\n" +
"\n" +
"Nous sommes heureux de vous informer que votre commande" +commande.getId_commande()+" est en cours de traitement et sera bientôt prête à être livrée.\n" +
"\n" + "Le montant total à payer pour cette commande est de" +commande.getMontant() +"DT .\n" +"\n"+
"\n" + "Le mode de paiement que vous avez choisi pour cette commande est :" +commande.getMode() +" .\n" +"\n"+
"Notre équipe logistique va prendre contact avec vous dans les prochains jours pour organiser la livraison de votre commande. Vous pouvez également suivre l'état de votre commande en vous connectant à votre compte sur notre site internet.\n" +
"\n" +
"Nous tenons à vous remercier pour votre confiance et votre fidélité. Si vous avez des questions ou des préoccupations, n'hésitez pas à nous contacter. Nous sommes à votre disposition pour vous aider.\n" +
"\n" +
"Cordialement," + signature);

            Transport.send(message);

            System.out.println("Mail sent successfully");

        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
