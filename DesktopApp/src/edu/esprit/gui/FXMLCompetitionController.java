/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.gui;

import edu.esprit.entities.Competition;
import edu.esprit.services.ServiceCompetition;
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
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author admin
 */
public class FXMLCompetitionController implements Initializable {

    @FXML
    private ListView<Competition> competition_list;
    @FXML
    private Label competition_label;
    @FXML
    private Button btn_ajouter;
    @FXML
    private Button btn_modifier;
    @FXML
    private Button btn_supprimer;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        ServiceCompetition se = new ServiceCompetition();
        /* List<Competition> competitions = se.getAll();
        List<String> names = new ArrayList<>();

        competitions.stream().map((e) -> e.getNom()).forEachOrdered((name) -> {
            names.add(name);
        });*/
        competition_list.setItems(FXCollections.observableArrayList(se.getAll()));
    }

    @FXML
    private void ajouterCompetition(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("FXMLAjouterCompetition.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void modifierCompetition(ActionEvent event) {
        int id = competition_list.getSelectionModel().getSelectedItem().getId_competition();
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
                FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLModifierCompetition.fxml"));
                Parent root = loader.load();
                FXMLModifierCompetitionController controller = loader.getController();
                controller.setId(id);
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
    private void supprimerCompetition(ActionEvent event) {
        int id = competition_list.getSelectionModel().getSelectedItem().getId_competition();
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Confirmation Suppression");
        dialog.setHeaderText("Voulez-vous vraiment supprimer la competition N°" + id + "?");

        ButtonType buttonTypeYes = new ButtonType("Oui", ButtonBar.ButtonData.YES);
        ButtonType buttonTypeNo = new ButtonType("Non", ButtonBar.ButtonData.NO);

        dialog.getDialogPane().getButtonTypes().addAll(buttonTypeYes, buttonTypeNo);

        Optional<ButtonType> result = dialog.showAndWait();

        if (result.isPresent() && result.get() == buttonTypeYes) {
            ServiceCompetition sc = new ServiceCompetition();
            sc.delete(id);
            System.out.println(id);
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Competition deleted", ButtonType.OK);
            alert.show();
        } else {
            dialog.close();
        }

    }

}
