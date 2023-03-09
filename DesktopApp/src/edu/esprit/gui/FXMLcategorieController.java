/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.gui;

import edu.esprit.entities.Categorie;
import edu.esprit.entities.Evenement;
import edu.esprit.services.ServiceCategorie;
import edu.esprit.services.ServiceEvenement;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import static javafx.collections.FXCollections.observableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.collections.ObservableList;
/**
 * FXML Controller class
 *
 * @author Saleh
 */
public class FXMLcategorieController implements Initializable {

    @FXML
    private Button ajout_btn;
    @FXML
    private Button modif_btn;
    @FXML
    private Button supp_btn;
     private List<Categorie> e1 = new ArrayList<>();
    @FXML
    private ListView<Categorie> list_categorie;
    @FXML
    private TextField add_categorie;
    @FXML
    private TextField modifier_categorie;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        ServiceCategorie sc=new ServiceCategorie();
        this.e1=sc.getAll();
        affiche();
        
        
    }    

    @FXML
    private void ajouter(ActionEvent event) {
     String nom = add_categorie.getText();
    if (!nom.isEmpty()) {
        Categorie categorie = new Categorie(nom);
        ServiceCategorie serviceCategorie = new ServiceCategorie();
        serviceCategorie.ajouter(categorie);
      ServiceCategorie sc=new ServiceCategorie();
            // Set the updated list as the new data source for the list view
            ObservableList<Categorie> observableCategories = FXCollections.observableArrayList(sc.getAll());
            list_categorie.setItems(observableCategories);

    }
}

    

    @FXML
    private void modifier(ActionEvent event) {
        int id = list_categorie.getSelectionModel().getSelectedItem().getId_categorie();
        Categorie cs = list_categorie.getSelectionModel().getSelectedItem();
        //System.out.println(id);
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Confirmation Modification");
        dialog.setHeaderText("Voulez-vous vraiment modifier l'evenement N°" + id + "?");

        ButtonType buttonTypeYes = new ButtonType("Oui", ButtonBar.ButtonData.YES);
        ButtonType buttonTypeNo = new ButtonType("Non", ButtonBar.ButtonData.NO);

        dialog.getDialogPane().getButtonTypes().addAll(buttonTypeYes, buttonTypeNo);
        Optional<ButtonType> result = dialog.showAndWait();

        if (result.isPresent() && result.get() == buttonTypeYes) {
            try {
                ServiceCategorie sc = new ServiceCategorie();
                Categorie c = new Categorie(list_categorie.getSelectionModel().getSelectedItem().getId_categorie(),modifier_categorie.getText());
                sc.modifier(c);
                 // update the list after modifying the category
            e1 = sc.getAll();
            list_categorie.getItems().setAll(e1);
            affiche();
                
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        } else {
            dialog.close();
        }}

    @FXML
    private void supprimer(ActionEvent event) {
        ServiceCategorie sc=new ServiceCategorie();
        Categorie selectedCategorie = list_categorie.getSelectionModel().getSelectedItem();
        sc.delete(selectedCategorie.getId_categorie());
         list_categorie.getItems().remove(selectedCategorie);
    }
    public void affiche() {

        ServiceEvenement se = new ServiceEvenement();
        list_categorie.setItems(FXCollections.observableArrayList(this.e1));
    }

    @FXML
    private void mod(MouseEvent event) {
        modifier_categorie.setText(list_categorie.getSelectionModel().getSelectedItem().getNom());
    }
    
}
