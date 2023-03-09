/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.gui;

import edu.esprit.entities.Messagerie;
import edu.esprit.entities.Utilisateur;
import edu.esprit.services.ServiceMessagerie;
import edu.esprit.services.ServiceUtilisateur;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author User
 */
public class FXMLAjouterMessagerieController implements Initializable {

    @FXML
    private TextField text_message;
    @FXML
    private Button btn_ajouter;
    @FXML
    private Button btn_retour;
    @FXML
    private ComboBox<Integer> id_utilis;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ServiceUtilisateur m = new ServiceUtilisateur();
        ObservableList<Utilisateur> Utilisateurs = FXCollections.observableArrayList(m.getAll());
        List<Integer> id = Utilisateurs.stream().map(Utilisateur::getId).collect(Collectors.toList());
        ObservableList<Integer> observableIds = FXCollections.observableList(id);
        id_utilis.setItems(observableIds); //cle
    }    

     @FXML
private void Ajoutermessage(ActionEvent event) {
    String messageText = text_message.getText();
    
    // List of banned words
    List<String> bannedWords = Arrays.asList("Fait chier", "Putain", "con","Je t’emmerde","Casse-toi","salope","Barre-toi","Va te faire enculer ","Va te faire foutre","Salaud");
    
    // Check if the message or feedback contain banned words
    boolean hasBannedWords = bannedWords.stream().anyMatch(word -> messageText.matches(".*\\b" + word + "\\b.*"));
    
    if (messageText.isEmpty() || id_utilis.getSelectionModel().isEmpty()) {
        Alert a = new Alert(Alert.AlertType.ERROR, "Aucun champ vide n'est accepté!", ButtonType.OK);
        a.showAndWait();
    } else if (hasBannedWords) {
        Alert a = new Alert(Alert.AlertType.ERROR, "Le message contient des mots interdits!", ButtonType.OK);
        a.showAndWait();
    } else {
        try {
            ServiceMessagerie sr = new ServiceMessagerie();
            Messagerie r = new Messagerie(id_utilis.getValue(), messageText, LocalDateTime.now());
            sr.ajouter(r);
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Message envoyé", ButtonType.OK);
            alert.show();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Erreur lors de l'envoi du message", ButtonType.OK);
            alert.show();
        }
    }
}


    @FXML
    private void returnhome(ActionEvent event) {
               try {
            Parent root = FXMLLoader.load(getClass().getResource("FXMLMessagerie.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

    }

    @FXML
    private void drop_id_utilis(ActionEvent event) {
    }

 
    
}
