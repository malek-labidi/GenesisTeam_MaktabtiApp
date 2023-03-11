/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.gui;

import edu.esprit.entities.Panier;
import edu.esprit.services.ServicePanier;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author abdelazizlahmar
 */
public class FXMLPanierController implements Initializable {

    @FXML
    private Label labelPanier;
    @FXML
    private ListView<Panier> viewPanier;
    @FXML
    private Button ajout_boutton;
    @FXML
    private Button supprimer_boutton;
    @FXML
    private AnchorPane bg_theme;
    @FXML
    private TextField txt_quantité;
    @FXML
    private TextField txt_prixTotal;
    @FXML
    private ImageView retour;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ServicePanier c = new ServicePanier();
        viewPanier.setItems(FXCollections.observableArrayList(c.getAll()));
        
        
     
    }    

    @FXML
    private void retour(MouseEvent event) {
         try {
            Parent root = FXMLLoader.load(getClass().getResource("FXMLMaktabti.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    
    
    }
    

