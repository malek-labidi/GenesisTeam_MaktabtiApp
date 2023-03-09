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
public class FXMLReclamationController implements Initializable {

    @FXML
    private ListView<Reclamation> listeReclamation;
    @FXML
    private Button buttonAjouter;
    @FXML
    private Button buttonModifier;
    @FXML
    private Button buttonSupprimer;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        affiche();
    }    

    @FXML
    private void AjouterButton(ActionEvent event) {
         try {
            Parent root = FXMLLoader.load(getClass().getResource("FXMLAjouterReclamation.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void ModifierButton(ActionEvent event) {
 int id = listeReclamation.getSelectionModel().getSelectedItem().getId_reclamation();
        System.out.println(id);
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Confirmation Modification");
        dialog.setHeaderText("Voulez-vous vraiment modifier la competition N°" + id + "?");

        ButtonType buttonTypeYes = new ButtonType("Oui", ButtonBar.ButtonData.YES);
        ButtonType buttonTypeNo = new ButtonType("Non", ButtonBar.ButtonData.NO);

        dialog.getDialogPane().getButtonTypes().addAll(buttonTypeYes, buttonTypeNo);

        Optional<ButtonType> result = dialog.showAndWait();

        if (result.isPresent() && result.get() == buttonTypeYes) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLModifierReclamation.fxml"));
                Parent root = loader.load();
                FXMLModifierReclamationController controller = loader.getController();
                controller.getReclamation(id);
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        } else {
            dialog.close();
        }

    }

    @FXML
    private void SupprimerButton(ActionEvent event) {
          int id = listeReclamation.getSelectionModel().getSelectedItem().getId_reclamation();
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Confirmation Suppression");
        dialog.setHeaderText("Voulez-vous vraiment supprimer la reclamation N°" + id + "?");

        ButtonType buttonTypeYes = new ButtonType("Oui", ButtonBar.ButtonData.YES);
        ButtonType buttonTypeNo = new ButtonType("Non", ButtonBar.ButtonData.NO);

        dialog.getDialogPane().getButtonTypes().addAll(buttonTypeYes, buttonTypeNo);

        Optional<ButtonType> result = dialog.showAndWait();

        if (result.isPresent() && result.get() == buttonTypeYes) {
            ServiceReclamation sc = new ServiceReclamation();
            sc.delete(id);
            System.out.println(id);
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "reclamation deleted", ButtonType.OK);
            alert.show();
            affiche();
        } else {
            dialog.close();
        }

    }
    
     public void affiche(){
        ServiceReclamation se = new ServiceReclamation();
        listeReclamation.setItems(FXCollections.observableArrayList(se.getAll()));
        
        // Définir des cellules personnalisée pour afficher les informations sur la compétition
        listeReclamation.setCellFactory(list -> new ReclamationListCell());
        
    }
      // Définir un ListCell personnalisé pour afficher les informations sur la compétition
     private class ReclamationListCell extends javafx.scene.control.ListCell<Reclamation> {
        @Override
        public void updateItem(Reclamation reclamation, boolean empty) {
            super.updateItem(reclamation, empty);

            if (empty || reclamation == null) {
                setText(null);
                setGraphic(null);
            } else {
                Label nameLabel = new Label(reclamation.getMessage());
                nameLabel.setStyle("-fx-font-weight: bold;");
                ServiceReclamation sl= new ServiceReclamation();
                

                Label idLabel = new Label("Id Message: " + reclamation.getId_reclamation());
                Label messagedLabel = new Label("Message: " + reclamation.getMessage());
                Label participantsLabel = new Label("Feedback: " + reclamation.getFeedback());
                Label partici = new Label("User Id: " + reclamation.getUser_id());

                

                VBox vbox = new VBox(idLabel, messagedLabel, participantsLabel,partici);
                vbox.setSpacing(4);

                setGraphic(vbox);
            }
        }
    }
}
