/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.gui;

import edu.esprit.entities.Panier;
import edu.esprit.services.ServicePanier;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ServicePanier c = new ServicePanier();
        viewPanier.setItems(FXCollections.observableArrayList(c.getAll()));
        
        
     
    }    
    
    
    
    }
    

