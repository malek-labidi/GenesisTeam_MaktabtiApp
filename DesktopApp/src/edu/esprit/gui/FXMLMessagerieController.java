/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.gui;

import edu.esprit.entities.Messagerie;
import edu.esprit.services.ServiceMessagerie;
import edu.esprit.services.ServiceReclamation;
import edu.esprit.services.ServiceUtilisateur;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Ilef
 */
public class FXMLMessagerieController implements Initializable {

    @FXML
    private ListView<Messagerie> list_msg;
    @FXML
    private Button btn_ajouter;
    @FXML
    private Button btn_modifier;
    @FXML
    private Button btn_supprimer;
    private Object event;
    private ServiceUtilisateur serviceUtilisateur = new ServiceUtilisateur();
    @FXML
    private Button btn_stat;


    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        affiche();
    }    

    @FXML
private void ajouter_message(ActionEvent event) {
          try {
            Parent root = FXMLLoader.load(getClass().getResource("../gui/FXMLAjouterMessagerie.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
}



    @FXML
    private void supprimermessage(ActionEvent event) {
         int id = list_msg.getSelectionModel().getSelectedItem().getId_messagerie();
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Confirmation Suppression");
        dialog.setHeaderText("Voulez-vous vraiment supprimer le message N°" + id + "?");

        ButtonType buttonTypeYes = new ButtonType("Oui", ButtonBar.ButtonData.YES);
        ButtonType buttonTypeNo = new ButtonType("Non", ButtonBar.ButtonData.NO);

        dialog.getDialogPane().getButtonTypes().addAll(buttonTypeYes, buttonTypeNo);

        Optional<ButtonType> result = dialog.showAndWait();

        if (result.isPresent() && result.get() == buttonTypeYes) {
            ServiceMessagerie sc = new ServiceMessagerie();
            sc.delete(id);
            System.out.println(id);
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "message deleted", ButtonType.OK);
            alert.show();
            affiche();
        } else {
            dialog.close();
        }

    }
    

public void affiche(){
        ServiceMessagerie sm = new ServiceMessagerie();
        list_msg.setItems(FXCollections.observableArrayList(sm.getAll()));
        
        // Définir des cellules personnalisée pour afficher les informations sur la compétition
        list_msg.setCellFactory(list -> new MessagerieListCell());
        
    }

 


    @FXML
    private void stat(ActionEvent event) {
              try {
            Parent root = FXMLLoader.load(getClass().getResource("BarChart.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        
    }
      // Définir un ListCell personnalisé pour afficher les informations sur la compétition
     private class MessagerieListCell extends javafx.scene.control.ListCell<Messagerie> {
        public void updateItem(Messagerie messagerie, boolean empty) {
            super.updateItem(messagerie, empty);

            if (empty || messagerie == null) {
                setText(null);
                setGraphic(null);
            } else {
                Label nameLabel = new Label(messagerie.getMessage());
                nameLabel.setStyle("-fx-font-weight: bold;");
                ServiceReclamation sl= new ServiceReclamation();
                

                Label idLabel = new Label("id: " + messagerie.getId_messagerie());
                Label messagedLabel = new Label("message: " + messagerie.getMessage());
                Label date = new Label("Date: " + messagerie.getDate_heure());
                

                VBox vbox = new VBox(idLabel, messagedLabel, date);
                vbox.setSpacing(5);

                setGraphic(vbox);
            }
        }
    }
}