/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.gui;

import edu.esprit.entities.EtatReservation;
import edu.esprit.entities.Evenement;
import edu.esprit.entities.Reservation;
import edu.esprit.services.ServiceEvenement;
import edu.esprit.services.ServiceReservation;
import java.io.IOException;
import java.net.URL;
import java.security.Provider;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Optional;
import java.util.ResourceBundle;
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
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
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
    @FXML
    private TextField search;
    private List<Evenement> e1 = new ArrayList<>();

    /**
     * the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        ServiceEvenement se=new ServiceEvenement();
        
        this.e1=se.getAll();
        affiche();
        
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

        int id = event_view.getSelectionModel().getSelectedItem().getId_evenement();
        Evenement e = event_view.getSelectionModel().getSelectedItem();
        System.out.println(id);
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Confirmation Modification");
        dialog.setHeaderText("Voulez-vous vraiment modifier l'evenement N°" + id + "?");

        ButtonType buttonTypeYes = new ButtonType("Oui", ButtonBar.ButtonData.YES);
        ButtonType buttonTypeNo = new ButtonType("Non", ButtonBar.ButtonData.NO);

        dialog.getDialogPane().getButtonTypes().addAll(buttonTypeYes, buttonTypeNo);
        Optional<ButtonType> result = dialog.showAndWait();

        if (result.isPresent() && result.get() == buttonTypeYes) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLModifierEvenement.fxml"));
                Parent root = loader.load();

                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                FXMLModifierEvenementController controler = loader.getController();
                controler.setData(e);
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
    private void supp_btn(ActionEvent event) {
        ServiceEvenement sc = new ServiceEvenement();
        Evenement selectedEvent = event_view.getSelectionModel().getSelectedItem();
        sc.delete(selectedEvent.getId_evenement());
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Event deleted", ButtonType.OK);
        alert.show();

        // Update the list view by removing the deleted event from the list
        event_view.getItems().remove(selectedEvent);
    }

    private void reserveBtnClicked(ActionEvent event) {
        Evenement selectedEvent = event_view.getSelectionModel().getSelectedItem();
        if (selectedEvent != null && selectedEvent.getNb_ticket() > 0) {
            ServiceEvenement sc = new ServiceEvenement();
            sc.decrementNbTickets(selectedEvent.getId_evenement(), selectedEvent.getNb_ticket());
            selectedEvent.setNb_ticket(selectedEvent.getNb_ticket() - 1);
            ServiceReservation sr = new ServiceReservation();
            sr.ajouter(new Reservation(selectedEvent.getId_evenement(), 1, EtatReservation.en_attente));
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Ticket reserved", ButtonType.OK);
            alert.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Cannot reserve ticket", ButtonType.OK);
            alert.show();
        }
    }

    @FXML
    private void search(KeyEvent event) {
        filter();
        affiche();

    }

    private void date_search(KeyEvent event) {
        filter();
        affiche();
    }

    public void filter() {
        
        String s = search.getCharacters().toString();
        ServiceEvenement e = new ServiceEvenement();
        this.e1 = e.getAll();
        
        
        if (!s.isEmpty()) {
            this.e1 = ServiceEvenement.filterByLocation(this.e1, s);

        }
    }

    public void affiche() {

        ServiceEvenement se = new ServiceEvenement();
        event_view.setItems(FXCollections.observableArrayList(this.e1));
    }

}
