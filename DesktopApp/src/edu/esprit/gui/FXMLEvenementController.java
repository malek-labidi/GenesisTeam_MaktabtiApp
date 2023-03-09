/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.gui;

import edu.esprit.api.MailEvenement;
import edu.esprit.entities.Commentaire;
import edu.esprit.entities.EtatReservation;
import edu.esprit.entities.Evenement;
import edu.esprit.entities.Reservation;
import edu.esprit.entities.login;
import edu.esprit.services.ServiceCommentaire;
import edu.esprit.services.ServiceEvenement;
import edu.esprit.services.ServiceLivre;
import edu.esprit.services.ServiceReservation;
import edu.esprit.services.ServiceUtilisateur;
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
import javafx.event.EventHandler;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
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
private login Log_in = login.getInstance();
    @FXML
    private Button ajouter;
    @FXML
    private Button modifier;
    @FXML
    private Button supprimer;
    /**
     * the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        ServiceEvenement se=new ServiceEvenement();
        
        this.e1=se.getAll();
        affiche();
           if (Log_in.getRole().equals("Client") || Log_in.getRole().equals("Auteur")) {
            ajouter.setVisible(false);
            modifier.setVisible(false);
            supprimer.setVisible(false);
           
        }
        
        
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
    ServiceCommentaire scom = new ServiceCommentaire();
    Evenement selectedEvent = event_view.getSelectionModel().getSelectedItem();
    
    // Delete all comments related to the selected event
    List<Commentaire> comments = scom.getCommentairesByEvenement(selectedEvent.getId_evenement());
    for (Commentaire c : comments) {
        scom.delete(c.getId_commentaire());
    }
    
    // Delete the selected event
    sc.delete(selectedEvent.getId_evenement());
    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Event deleted", ButtonType.OK);
    alert.show();

    // Update the list view by removing the deleted event from the list
    event_view.getItems().remove(selectedEvent);
    
    // Send an email
    MailEvenement.sendEmail(selectedEvent);
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
        event_view.setCellFactory(list -> new EvenementListCell());
    }
    
    
        private class EvenementListCell extends javafx.scene.control.ListCell<Evenement> {

        @Override
        public void updateItem(Evenement evenement, boolean empty) {
            super.updateItem(evenement, empty);

            if (empty || evenement == null) {
                setText(null);
                setGraphic(null);
            } else {
                Label nameLabel = new Label(evenement.getNom());
                nameLabel.setStyle("-fx-font-weight: bold;");
                ServiceLivre sl = new ServiceLivre();
                ServiceUtilisateur su = new ServiceUtilisateur();

                Label idLabel = new Label("Livre: " + sl.getOneById(evenement.getId_livre()).getTitre());
                Label lieu = new Label("lieu: " + evenement.getLieu());
                Label ticket = new Label("nb ticket: " + evenement.getNb_ticket());
                Label date = new Label("date: " + evenement.getDate().toString());
                Label Description = new Label("Description: " + evenement.getDescription());
                Label auteur = new Label("Auteur: " + su.getOneById(evenement.getId_auteur()).getNom()+" "+su.getOneById(evenement.getId_auteur()).getPrenom());
                Button reserver = new Button("Reserver");

                reserver.setStyle(" -fx-background-color: #f8a375;-fx-text-fill: white;-fx-font-size: 16px; -fx-background-radius: 8px; -fx-font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif;");
               
                Button commenter = new Button("Commenter");

                commenter.setStyle(" -fx-background-color: #f8a375;-fx-text-fill: white;-fx-font-size: 16px; -fx-background-radius: 8px; -fx-font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif;");

                if (evenement.getNb_ticket() == 0) {
                    reserver.setDisable(true);
                    reserver.setStyle("-fx-background-color: #C0C0C0;-fx-text-fill: white;-fx-font-size: 16px; -fx-background-radius: 8px; -fx-font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif;");

                } else {
                    reserver.setDisable(false);
                }
                commenter.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        int id = evenement.getId_evenement();
                        try {
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLCommentaire.fxml"));
                                Parent root = loader.load();
                                FXMLCommentaireController controller = loader.getController();
                                controller.setEvenementId(id);
                                Scene scene = new Scene(root);
                                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                                stage.setScene(scene);
                                stage.show();
                            } catch (IOException ex) {
                                System.out.println(ex.getMessage());
                            }
                        } 
                        
                    
                });
                reserver.setOnAction(new EventHandler<ActionEvent>(){
                    @Override
                    public void handle(ActionEvent event) {
                        int id = evenement.getId_evenement();
                        Dialog<ButtonType> dialog = new Dialog<>();
                        dialog.setTitle("confirmer Reservation");
                        dialog.setHeaderText("Voulez-vous vraiment Reserver une/des ticket pour l'evenement " + evenement.getNom() + " ?");
                        

                        ButtonType buttonTypeYes = new ButtonType("Oui", ButtonBar.ButtonData.YES);
                        ButtonType buttonTypeNo = new ButtonType("Non", ButtonBar.ButtonData.NO);

                        dialog.getDialogPane().getButtonTypes().addAll(buttonTypeYes, buttonTypeNo);

                        Optional<ButtonType> result = dialog.showAndWait();

                        if (result.isPresent() && result.get() == buttonTypeYes) {
                            try {
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLReservation.fxml"));
                                Parent root = loader.load();
                                FXMLReservationController controller = loader.getController();
                                controller.getEvenement(id);
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
                    
                });
                HBox hbox=new HBox(reserver,commenter);
                hbox.setSpacing(15);

                VBox vbox = new VBox(nameLabel, idLabel, lieu, ticket, date, Description, auteur,hbox);
                vbox.setSpacing(5);

                setGraphic(vbox);
            }
        }
    }

}
