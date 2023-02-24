/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.gui;

import edu.esprit.entities.Evenement;
import edu.esprit.services.ServiceEvenement;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author SADOK
 */
public class FXMLEvenementController implements Initializable {

    @FXML
    private Label event_label;
    @FXML
    private ListView<Evenement> event_view;

    /**
     * the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        ServiceEvenement se = new ServiceEvenement();
        /* List<Evenement> events = se.getAll();
        List<String> names = new ArrayList<>();

        events.stream().map((e) -> e.getNom()).forEachOrdered((name) -> {
            names.add(name);
        });*/
        event_view.setItems(FXCollections.observableArrayList(se.getAll()));
    }

    @FXML
    private void ajout_btn(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLAjouter.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void modif_btn(ActionEvent event) throws IOException {

    }

    @FXML
    private void supp_btn(ActionEvent event) {
        ServiceEvenement sc = new ServiceEvenement();
        sc.delete(event_view.getSelectionModel().getSelectedItem().getId_evenement());
        System.out.println(event_view.getSelectionModel().getSelectedItem().getId_evenement());
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Event deleted", ButtonType.OK);
        alert.show();
    }

}
