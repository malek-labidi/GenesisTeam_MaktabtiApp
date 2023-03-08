/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.gui;


import edu.esprit.entities.Auteur;
import edu.esprit.entities.Evenement;
import edu.esprit.entities.Utilisateur;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import net.glxn.qrgen.core.image.ImageType;
import net.glxn.qrgen.javase.QRCode;


/**
 * FXML Controller class
 *
 * @author SADOK
 */
public class FXMLTicketController implements Initializable {

    @FXML
    private Label auteur;
    @FXML
    private Label date;
    @FXML
    private Label heure;
    @FXML
    private ImageView qrCodeImageView;
    @FXML
    private Pane ticketPane;
    @FXML
    private Label event;
   

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        


      
    }   
    public void QR(Utilisateur auteur,Evenement evenement){
           String text = "7ot lena l utilisateur connecté";
        ByteArrayOutputStream out = QRCode.from(text).withSize(600, 600).to(ImageType.PNG).stream();
        try {
            Files.write(Paths.get("qrcode.png"), out.toByteArray());
        } catch (IOException ex) {
            Logger.getLogger(FXMLTicketController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Image qrCodeImage = new Image("file:qrcode.png");
        qrCodeImageView.setImage(qrCodeImage);
        this.auteur.setText(auteur.getNom()+" "+auteur.getPrenom());
        date.setText(evenement.getDate().toString());
        heure.setText(evenement.getHeure().toString());
        event.setText(evenement.getNom());
        
        
    }
     public Pane getTicketPane() {
        return ticketPane;
    }
}
   



    

