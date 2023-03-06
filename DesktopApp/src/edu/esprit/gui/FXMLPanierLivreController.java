/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.gui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;

/**
 * FXML Controller class
 *
 * @author abdelazizlahmar
 */
public class FXMLPanierLivreController implements Initializable {

    @FXML
    private ListView<?> listViewPanier;
    @FXML
    private Spinner<Integer> idSpinner;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
