/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.gui;

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
import java.text.SimpleDateFormat;
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
public class FXMLModifierEvenementController implements Initializable {

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
    private DatePicker startDatePicker;
    @FXML
    private TextField nomField;
    @FXML
    private TextField heureField;
    @FXML
    private TextField lieuField;
    @FXML
    private TextField descriptionField;
    @FXML
    private ListView<String> auteurView;
    @FXML
    private ListView<String> livreView;
    @FXML
    private Button reset;
    @FXML
    private TextField ticketField;
    @FXML
    private Button modifevent;
    
    private int id ;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    


    @FXML
    private void reset(ActionEvent event) {
    }

    @FXML
    private void modifevent(ActionEvent event) {
        
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
            ServiceEvenement sp= new ServiceEvenement();
            ServiceLivre sl = new ServiceLivre();
        List<Livre> livres = sl.getAll();
        int id2 = -1;
        for (Livre l : livres) {
            if (l.getTitre().equals(livreView.getSelectionModel().getSelectedItem())) {
                id2 = l.getId_livre();
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
            Evenement p = new Evenement(id,nomField.getText(), Date.valueOf(startDatePicker.getValue()), Time.valueOf(heureField.getText()), id1, id2, lieuField.getText(), descriptionField.getText(), Integer.parseInt(ticketField.getText()));
            sp.modifier(p);
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Evenement modified", ButtonType.OK);
            alert.show();
        }
    }
    
    public void setData(Evenement e){
        startDatePicker.setValue(e.getDate().toLocalDate());
        this.id=e.getId_evenement();
        System.out.println(id);
        lieuField.setText(e.getLieu());
        descriptionField.setText(e.getDescription());
        ticketField.setText(Integer.toString(e.getNb_ticket()));
        heureField.setText(new SimpleDateFormat("HH:mm:ss").format(e.getHeure()));
        nomField.setText(e.getNom());
       
        ServiceLivre sl = new ServiceLivre();
        List<Livre> livres = sl.getAll();
        List<String> names = new ArrayList<>();

        livres.forEach((l) -> {
            names.add(l.getTitre());
        });
        livreView.setItems(FXCollections.observableArrayList(names));
        livreView.getSelectionModel().select(sl.getOneById(e.getId_livre()).getTitre());

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
        auteurView.getSelectionModel().select(su.getOneById(e.getId_auteur()).getNom()+" "+su.getOneById(e.getId_auteur()).getPrenom());
        
        
        
        
        
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
