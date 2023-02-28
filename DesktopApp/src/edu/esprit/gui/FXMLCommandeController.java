/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.gui;

import edu.esprit.entities.Commande;
import edu.esprit.services.ServiceCommande;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author abdelazizlahmar
 */
public class FXMLCommandeController implements Initializable {

    @FXML
    private Label labelCommande;
    @FXML
    private ListView<Commande> commandeView;
    @FXML
    private Button button_modifierCommande;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ServiceCommande c = new ServiceCommande();
        commandeView.setItems(FXCollections.observableArrayList(c.getAll()));
       
        
    }    

    @FXML
    private void modfier(ActionEvent event) {
        int id = commandeView.getSelectionModel().getSelectedItem().getId_commande();
        System.out.println(id);
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Confirmation Modification");
        dialog.setHeaderText("Voulez-vous vraiment modifier la commande N°" + id + "?");

        ButtonType buttonTypeYes = new ButtonType("Oui", ButtonBar.ButtonData.YES);
        ButtonType buttonTypeNo = new ButtonType("Non", ButtonBar.ButtonData.NO);

        dialog.getDialogPane().getButtonTypes().addAll(buttonTypeYes, buttonTypeNo);

        Optional<ButtonType> result = dialog.showAndWait();

        if (result.isPresent() && result.get() == buttonTypeYes) {
            Dialog<String> dialog2 = new Dialog<>();
            dialog2.setTitle("Dialogue avec champ de texte");
            dialog2.setHeaderText("Entrez l'état que vous souhaitez modifier :");

            // Ajouter un champ de texte et les boutons OK/Annuler au dialogue
           
           ListView<String> listView = new ListView<String>(FXCollections.observableArrayList("encours", "livre", "annuler"));

            dialog2.getDialogPane().setContent(new GridPane());
            ((GridPane) dialog2.getDialogPane().getContent()).add(new Label("Texte :"), 0, 0);
            ((GridPane) dialog2.getDialogPane().getContent()).add(listView, 1, 0);
            dialog2.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

            // Récupérer le texte saisi lorsque le bouton OK est cliqué
            dialog2.setResultConverter(dialogButton -> {
                if (dialogButton == ButtonType.OK) {
                    ServiceCommande s = new ServiceCommande();
                    Object selectedItem = listView.getSelectionModel().getSelectedItem();
                    Commande commande = (Commande) selectedItem;
                  
                    s.modifier(commande);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Commande updated", ButtonType.OK);
                    alert.show();
                }
                return null;
            });

            // Afficher le dialogue
            dialog2.showAndWait().ifPresent(text -> {
                System.out.println("Le texte saisi est : " + text);
            });
       
            
        } else {
            dialog.close();
        }
    }
    
}
