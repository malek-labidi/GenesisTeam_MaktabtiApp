/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.gui;

import edu.esprit.api.MailEtatCommandeModifie;
import edu.esprit.entities.Commande;
import edu.esprit.entities.Etat;
import static edu.esprit.entities.Role.Client;
import edu.esprit.entities.Utilisateur;
import edu.esprit.entities.login;
import edu.esprit.services.ServiceCommande;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author abdelazizlahmar
 */
public class FXMLModifierCommandeController implements Initializable {

    @FXML
    private Button valider;
    @FXML
    private ComboBox<String> comboEtat;
    private int id;
    @FXML
    private ImageView retour;
  private login Log_in = login.getInstance();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
                comboEtat.getItems().addAll("encours", "livre", "annuler");

    }    

    @FXML
    private void modifierEtat(ActionEvent event) {
        Etat e = Etat.valueOf(comboEtat.getSelectionModel().getSelectedItem());
        ServiceCommande sc = new ServiceCommande();
        Commande c = sc.getOneById(id);
        Commande com = new Commande(c.getId_livre(), c.getId_client(), id, c.getStatus(), c.getMode(), e, c.getMontant());
        sc.modifier(com);
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Commande modifier avec succés ", ButtonType.OK);
        alert.show();
         // Get the user email
        ServiceUtilisateur utilisateurService = new ServiceUtilisateur();
        Utilisateur client = utilisateurService.getOneById(c.getId_client());
        String userEmail = client.getEmail();
        //if(e==Etat.annuler){
            MailEtatCommandeModifie.sendEmail(com,Log_in);
        //}
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLCommande.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
       
    }
    
    public void getEtat(int id) {
        this.id = id;
        ServiceCommande sc = new ServiceCommande();
        Commande c = sc.getOneById(id);
        comboEtat.getSelectionModel().select(c.getEtat().toString());
        
    }

    @FXML
    private void retour(MouseEvent event) {
         try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLMaktabti.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
}
