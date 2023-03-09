/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.gui;

import edu.esprit.entities.Commentaire;
import edu.esprit.entities.Utilisateur;
import edu.esprit.services.ServiceCommentaire;
import edu.esprit.services.ServiceTicket;
import edu.esprit.services.ServiceUtilisateur;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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
    private VBox parent;
    @FXML
    private Button del_btn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        

    }

    public void setCommentaire(Commentaire c) {
        ServiceUtilisateur u = new ServiceUtilisateur();
        Utilisateur ut = u.getOneById(c.getId_client());
        commentaireData = c;
        user.setText(ut.getPrenom()+" "+ut.getNom());
        commentaire.setText(c.getCommentaire());
     
        
        

    }

    


    @FXML
    private void deletecomment(ActionEvent event) {
         ServiceCommentaire serviceCommentaire = new ServiceCommentaire();
        serviceCommentaire.delete(commentaireData.getId_commentaire());
        try {
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLCommentaire.fxml"));
                                Parent root = loader.load();
                                FXMLCommentaireController controller = loader.getController();
                                controller.setEvenementId(commentaireData.getId_evenement());
                                Scene scene = new Scene(root);
                                Stage stage = (Stage) del_btn.getScene().getWindow();
                                stage.setScene(scene);
                                stage.show();
                            } catch (IOException ex) {
                                System.out.println(ex.getMessage());
                            }

        
    }
}
