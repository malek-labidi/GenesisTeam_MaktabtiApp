/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.gui;

import edu.esprit.entities.Reclamation;
import edu.esprit.services.ServiceReclamation;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;

/**
 * FXML Controller class
 *
 * @author Ilef
 */
public class FXMLmaktabtiController implements Initializable {

    @FXML
    private TextField textfeedback;
    @FXML
    private TextField textmessage;
    @FXML
    private Button button_send;
    @FXML
    private TableView<Reclamation> table_rec;
    @FXML
    private TableColumn<Reclamation, Integer> column_id;
    @FXML
    private TableColumn<Reclamation, String> column_message;
    @FXML
    private TableColumn<Reclamation, String> column_feedback;
    @FXML
    private Button button_delete;
    @FXML
    private ComboBox<Integer> combo_id_rec;
    @FXML
    private Button button_modifier;

    /**
     * Initializes the controller class.
     */
   public void UpdateTable(){
        List<Reclamation> list=new ArrayList<>();
        
        ServiceReclamation is = new ServiceReclamation();
        list=is.getAll();
        
        
                
        ObservableList<Reclamation> obs=FXCollections.observableArrayList(list);
//        List<Trajet> trajet_list = obs.stream().map(Iteneraire::getTrajet).collect(Collectors.toList());
//        List<Integer> trajet_id_list = trajet_list.stream().map(Trajet::getId).collect(Collectors.toList());
//        ObservableList<Integer> observables_ids_trajet =FXCollections.observableList(trajet_id_list);
        column_id.setCellValueFactory(new PropertyValueFactory<Reclamation ,Integer>("id_reclamation"));
       
        column_feedback.setCellValueFactory(new PropertyValueFactory<Reclamation ,String>("feedback"));
        column_message.setCellValueFactory(new PropertyValueFactory<Reclamation ,String>("message"));
        table_rec.setItems(obs);
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ServiceReclamation sr = new ServiceReclamation();
     //   TrajetService ts = new TrajetService();
        ObservableList<Reclamation> reclamations = FXCollections.observableArrayList(sr.getAll());
        List<Integer> id_r = reclamations.stream().map(Reclamation::getId_reclamation).collect(Collectors.toList());
        ObservableList<Integer> observableIds = FXCollections.observableList(id_r);
               combo_id_rec.setItems(observableIds);

        UpdateTable();
    }    

    @FXML
    private void ajouterReclamation(ActionEvent event) {
         ServiceReclamation sr = new ServiceReclamation();
        //ItineraireService is = new ItineraireService();
        Reclamation i=new Reclamation(textfeedback.getText(), 
                textmessage.getText());
        sr.ajouter(i);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("");
		alert.setHeaderText("");
		alert.setContentText("Ajout avec succés");
                alert.showAndWait();
        UpdateTable();
    
    }
    
    @FXML
    private void supprimerReclamation(ActionEvent event) {
        ServiceReclamation sr=new ServiceReclamation();
        Reclamation selected_it =  table_rec.getSelectionModel().getSelectedItem();
        sr.delete(selected_it.getId_reclamation());
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("");
		alert.setHeaderText("");
		alert.setContentText("Suppresion avec succés");
                alert.showAndWait();
        UpdateTable();
    }
    @FXML
    private void modifierreclamation(ActionEvent event) {
             List<Object> list = new ArrayList<>(Arrays.asList(textfeedback.getText(), 
                textmessage.getText(),combo_id_rec.getValue()));
        ServiceReclamation is=new ServiceReclamation();
        Reclamation selected_it =table_rec.getSelectionModel().getSelectedItem();
        is.update(list,selected_it.getId_reclamation());
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("");
		alert.setHeaderText("");
		alert.setContentText("Mise à jour avec succés");
                alert.showAndWait();
        UpdateTable();
    }

    @FXML
    private void below8(KeyEvent event) {
  
        if(textfeedback.getText().length() >= 15){
            event.consume();
            textfeedback.setStyle("-fx-border-color: red");
        }
        else{
             textfeedback.setStyle("-fx-border-color: blue");
    }
    }
}
