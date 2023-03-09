/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.esprit.api;

import edu.esprit.entities.Fidelite;
import edu.esprit.entities.Livre;
import edu.esprit.entities.Offre;
import edu.esprit.entities.Utilisateur;
import edu.esprit.services.ServiceFidelite;
import edu.esprit.services.ServiceLivre;
import edu.esprit.services.ServiceOffre;
import edu.esprit.services.ServiceUtilisateur;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

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
 * @author Gaaloul
 */
public class SendOffreByMail {
     
    public static void Envoyer() {

        // Set up your email account credentials
        final String username = "maktabti10@gmail.com";
        final String password = "dae rta agl jjg igfw";

        // Set up the email properties
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        // Create a new session with an authenticator
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
            List<String> nomsLivres = new ArrayList<>();
        ServiceLivre sl=new ServiceLivre();
        ServiceOffre so=new ServiceOffre();
            List<Livre> livres = sl.getAll();
        List <Offre> lo=so.getAll();
            for (Offre oo : lo) {
                Livre l1=sl.getOneById(oo.getId_livre());
                if(l1!=null){
                    nomsLivres.add(l1.getTitre());
                    nomsLivres.add(oo.getPourcentage_solde());
                    String prix=String.valueOf(oo.getPrix_soldé());
                    nomsLivres.add(prix);
                }
            }
            //System.out.println(nomsLivres);
            ServiceFidelite sf=new ServiceFidelite();
            List<Fidelite> fidelites = sf.getAll();
            List<Utilisateur> listeclients=new ArrayList<>();
            ServiceUtilisateur su =new ServiceUtilisateur();
            List <String> adresseCLients=new ArrayList<>();
             for(Fidelite ff : fidelites){

                    listeclients.add(su.getOneById(ff.getId_client())); 
                             // System.out.println(listeclients);

             }
             
            List <String>emails=new ArrayList<>();
            for(Utilisateur cl : listeclients){
                emails.add(cl.getEmail());
                
            }
                  

          
        try {
            // Create a new message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
           
             InternetAddress[] addresses = new InternetAddress[emails.size()];
            for(int i = 0; i < emails.size(); i++) {
                addresses[i] = new InternetAddress(emails.get(i));
            }
            message.setRecipients(Message.RecipientType.TO, addresses);
            message.setSubject("Nouveau Offre chez MAKTABTI");
            message.setText("Bonjour voici les offres de la semaine" + nomsLivres);

            // Send the message
            Transport.send(message);

            System.out.println("Message sent successfully.");

        } catch (MessagingException e) {
            System.out.println("Error sending message: " + e.getMessage());
        }
    }
}
    


    

