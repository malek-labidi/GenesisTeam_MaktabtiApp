/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.gui;

import edu.esprit.entities.Auteur;
import edu.esprit.entities.Evenement;
import edu.esprit.entities.Livre;
import edu.esprit.entities.Utilisateur;
import edu.esprit.services.ServiceEvenement;
import edu.esprit.services.ServiceLivre;
import edu.esprit.services.ServiceUtilisateur;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;

import java.sql.Time;

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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author SADOK
 */
public class FXMLAjouterController implements Initializable {

    @FXML
    private Label lbl_nom;
    @FXML
    private Label lbl_date;
    @FXML
    private Label lbl_heure;
    @FXML
    private Label lbl_lieu;
    @FXML
    private Label lbl_description;
    @FXML
    private TextField nomField;
    @FXML
    private TextField heureField;
    @FXML
    private DatePicker startDatePicker;
    @FXML
    private TextField lieuField;
    @FXML
    private TextField descriptionField;
    @FXML
    private TextField ticketField;
    @FXML
    private ListView<String> auteurView;
    @FXML
    private ListView<String> livreView;
    
    @FXML
    private Button reset;
    @FXML
    private Button ajoutevent;
    
   

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        ServiceLivre sl = new ServiceLivre();
        List<Livre> livres = sl.getAll();
        List<String> names = new ArrayList<>();

        livres.forEach((l) -> {
            names.add(l.getTitre());
        });
        livreView.setItems(FXCollections.observableArrayList(names));

        ServiceUtilisateur su = new ServiceUtilisateur();
        List<Utilisateur> users = su.getAll();
        List<String> namesU = new ArrayList<>();
        for (Utilisateur u : users) {
            if (u.getRole().equals("Auteur")) {
                String auteur = u.getNom() + " " + u.getPrenom();
                namesU.add(auteur);
            }
        }
        auteurView.setItems(FXCollections.observableArrayList(namesU));

    }

    @FXML
    private void ajoutevent(ActionEvent event) {
        /* String nom = nomField.getText();
        String heure = heureField.getText();
        String lieu = lieuField.getText();
        String description = descriptionField.getText();
        LocalDate date = startDatePicker.getValue();

        // Retrieve the selected auteur(s) from the ListView
        selectedAuteurs = (List<String>) auteurView.getSelectionModel().getSelectedItems();

        // Retrieve the selected livre from the ListView
        selectedLivre = (String) livreView.getSelectionModel().getSelectedItem();*/

        ServiceEvenement sp = new ServiceEvenement();
        ServiceLivre sl = new ServiceLivre();
        List<Livre> livres = sl.getAll();
        int id = -1;
        for (Livre l : livres) {
            if (l.getTitre().equals(livreView.getSelectionModel().getSelectedItem())) {
                id = l.getId_livre();
                break;
            }
        }
        ServiceUtilisateur su = new ServiceUtilisateur();
        List<Utilisateur> users = su.getAll();
        int id1 = -1;
        for (Utilisateur u : users) {
            String auteur = u.getNom() + " " + u.getPrenom();
            if (auteur.equals(auteurView.getSelectionModel().getSelectedItem())) {
                id1 = u.getId();
                break;
            }
        }
        System.out.println(id + " " + id1);
        String nom = nomField.getText();
        String lieu = lieuField.getText();
        String heure = heureField.getText();
        String description=descriptionField.getText();
        
        String dateFormat = "\\d{2}-\\d{2}-\\d{4}";
        String timeFormat = "\\d{2}:\\d{2}:\\d{2}";
        if ((nom.equals("")) || (lieu.equals("")) || (description.equals(""))) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "check that all the input are filled!", ButtonType.OK);
            alert.show();
        } else if (!heure.matches(timeFormat)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "heure doit être au format hh:mm:ss", ButtonType.OK);
            alert.show();
        } else {
            Evenement p = new Evenement(nomField.getText(), Date.valueOf(startDatePicker.getValue()), Time.valueOf(heureField.getText()), id1, id, lieuField.getText(), descriptionField.getText(), Integer.parseInt(ticketField.getText()));
            sp.ajouter(p);
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Evenement added", ButtonType.OK);
            alert.show();
        }

    }

    @FXML
    private void reset(ActionEvent event) {
        nomField.setText("");
        heureField.setText("");
        startDatePicker.setValue(null);
        lieuField.setText("");
        descriptionField.setText("");
        ticketField.setText("");
        
    }

    @FXML
    private void back(MouseEvent event) {
        try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLEvenement.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        
    }

    

}
