/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Hyperlink;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Rectangle;

/**
 * FXML Controller class
 *
 * @author admin
 */
public class FXMLMaktabtiController implements Initializable {

    @FXML
    private Hyperlink home;
    @FXML
    private Hyperlink library;
    @FXML
    private BorderPane bp;
    private int id ; 
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void competitions(MouseEvent event) {
        loadPage("FXMLCompetition");
    }
    private void loadPage (String page){
           Parent root = null;
        try {
         
            root = FXMLLoader.load(getClass().getResource(page+".fxml"));
        } catch (IOException ex) {
            Logger.getLogger(FXMLMaktabtiController.class.getName()).log(Level.SEVERE, null, ex);
        }
        bp.setCenter(root);
    }
    
    
}
