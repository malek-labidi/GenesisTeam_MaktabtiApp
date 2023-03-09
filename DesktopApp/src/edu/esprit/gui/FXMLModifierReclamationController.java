/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.gui;

import edu.esprit.entities.Reclamation;
import edu.esprit.services.ServiceReclamation;
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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Ilef
 */
public class FXMLModifierReclamationController implements Initializable {

    @FXML
    private TextField Feedback;
    @FXML
    private TextField Message;
    @FXML
    private Button Modifier;
    @FXML
    private Button btn_retour;
   
    private int id;
        
    private int id_reclamation;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void btn_modifier(ActionEvent event) {
      if (Message.getText().isEmpty() || Feedback.getText().isEmpty()) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Aucun champ vide n'est accepté!", ButtonType.OK);
            a.showAndWait();
        } else {
            ServiceReclamation sr = new ServiceReclamation();
            Reclamation r;
          r = new Reclamation(id_reclamation, Message.getText(), Feedback.getText());
            sr.modifier(r);
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Reclamation modifiée", ButtonType.OK);
            alert.show();
        }
    }

    @FXML
    private void returnbutton(ActionEvent event) {
              try {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLReclamation.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    } catch (IOException ex) {
        System.out.println(ex.getMessage());
    }
    }


    void getReclamation(int id) {
    this.id_reclamation = id;


        ServiceReclamation sc = new ServiceReclamation();
        System.out.println(id);
        Reclamation c = sc.getOneById(id);
        System.out.println(c);
          Message.setText(c.getMessage());
        Feedback.setText(c.getFeedback());
        
    }
    }
    

