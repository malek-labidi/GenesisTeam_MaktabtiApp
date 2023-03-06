/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.gui;

import edu.esprit.entities.Commentaire;
import edu.esprit.services.ServiceCommentaire;
import edu.esprit.services.ServiceTicket;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author SADOK
 */
public class FXMLCommentairemodelController implements Initializable {

    @FXML
    private Label user;
    @FXML
    private Label commentaire;
      private Commentaire commentaireData;
    @FXML
    private ImageView trash;
    private VBox parent;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        

    }

    public void setCommentaire(Commentaire c) {
        commentaireData = c;
        user.setText(String.valueOf(c.getId_client()));
        commentaire.setText(c.getCommentaire());
        this.parent = parent;
        
        

    }

    

    @FXML
    private void deletecomment(MouseEvent event) {
        ServiceCommentaire serviceCommentaire = new ServiceCommentaire();
        serviceCommentaire.delete(commentaireData.getId_commentaire());
        parent.getChildren().clear();
        
        

}}
