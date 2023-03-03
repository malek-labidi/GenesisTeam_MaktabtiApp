/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.gui;

import edu.esprit.api.MailReservation;
import edu.esprit.entities.Evenement;
import edu.esprit.services.ServiceEvenement;
import edu.esprit.services.ServiceLivre;
import edu.esprit.services.ServiceUtilisateur;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author SADOK
 */
public class FXMLReservationController implements Initializable {

    @FXML
    private Button reserver_btn;
    private List<Evenement> e1 = new ArrayList<>();
    @FXML
    private Spinner<Integer> num_ticket;
    private int id;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        ServiceEvenement se =new ServiceEvenement();
        
        this.e1=se.getAll();
        
        SpinnerValueFactory<Integer> value= new SpinnerValueFactory.IntegerSpinnerValueFactory(1,200);
        value.setValue(1);
        num_ticket.setValueFactory(value);
    }    

    @FXML
    
private void reserver(ActionEvent event) {
    //Evenement selectedEvent = event_view.getSelectionModel().getSelectedItem();
    //if (selectedEvent == null) {
        // No event selected, do nothing
       // return;
    //}
    ServiceEvenement se = new ServiceEvenement();
     Evenement e=se.getOneById(id);
    
    if (e.getNb_ticket() > 0) {
        // There are tickets available, decrement the nb_ticket field and display a success message
        int numTickets =num_ticket.getValueFactory().getValue(); // Get the number of tickets from a TextField or other input control
        if (numTickets > e.getNb_ticket()) {
            
            Alert alert = new Alert(AlertType.ERROR, "Not enough tickets available.", ButtonType.OK);
            alert.showAndWait();
            return;
        }
        e.setNb_ticket(e.getNb_ticket() - numTickets);
        
        se.modifier(e); // Update the event in the database
        MailReservation.sendEmail();
        String message = "Reservation completed successfully!";
        Alert alert = new Alert(AlertType.INFORMATION, message, ButtonType.OK);
        alert.showAndWait();
    } else {
        // No tickets available, display a message to the user
        String message = "No tickets available. Please check again later.";
        Alert alert = new Alert(AlertType.INFORMATION, message, ButtonType.OK);
        alert.showAndWait();
    }
}

   
    
            
            public void getEvenement(int id){
                this.id=id;
                
                
            }
}
