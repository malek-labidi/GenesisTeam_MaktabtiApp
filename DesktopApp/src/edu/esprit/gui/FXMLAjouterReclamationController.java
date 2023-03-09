/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.gui;

import org.apache.commons.io.FileUtils;
import edu.esprit.entities.Reclamation;
import edu.esprit.entities.Utilisateur;
import edu.esprit.services.ServiceReclamation;
import edu.esprit.services.ServiceUtilisateur;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Ilef
 */
public class FXMLAjouterReclamationController implements Initializable {

    @FXML
    private TextField Feedback;
    @FXML
    private TextField Message;
    @FXML
    private Button Ajouter;
    @FXML
    private Button btn_retour;
    @FXML
    private Button btn_upload;
    @FXML
    private ComboBox<Integer> id_user;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ServiceUtilisateur m = new ServiceUtilisateur();
        ObservableList<Utilisateur> Utilisateurs = FXCollections.observableArrayList(m.getAll());
        List<Integer> id = Utilisateurs.stream().map(Utilisateur::getId).collect(Collectors.toList());
        ObservableList<Integer> observableIds = FXCollections.observableList(id);
        id_user.setItems(observableIds); //cle
        // TODO
    }

 @FXML
private void btn_ajouter(ActionEvent event) {
    String messageText = Message.getText();
    String feedbackText = Feedback.getText();

    // List of banned words
    List<String> bannedWords = Arrays.asList("Fait chier", "Putain", "con", "Je t’emmerde", "Casse-toi", "salope", "Barre-toi", "Va te faire enculer ", "Va te faire foutre", "Salaud");

    // Check if the message or feedback contain banned words
    boolean hasBannedWords = bannedWords.stream().anyMatch(word -> messageText.contains(word) || feedbackText.contains(word));

    if (messageText.isEmpty() || feedbackText.isEmpty() || id_user.getValue()==null) {
        Alert a = new Alert(Alert.AlertType.ERROR, "Aucun champ vide n'est accepté!", ButtonType.OK);
        a.showAndWait();
    } else if (hasBannedWords) {
        Alert a = new Alert(Alert.AlertType.ERROR, "Le message ou le feedback contient des mots interdits!", ButtonType.OK);
        a.showAndWait();
    } else {
        try {
             ServiceReclamation sr = new ServiceReclamation();
        Reclamation r = new Reclamation(messageText, feedbackText,id_user.getValue());
        sr.ajouter(r);
            Parent root = FXMLLoader.load(getClass().getResource("rating.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}



    @FXML
    private void returnbutton(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("FXMLReclamation.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

    }

    @FXML
    private void handleUploadImage(ActionEvent event) {
      FileChooser fileChooser = new FileChooser();

    // Set extension filter to allow only image files
    FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image files (*.jpg, *.jpeg, *.png)", "*.jpg", "*.jpeg", "*.png");
    fileChooser.getExtensionFilters().add(extFilter);

    // Show open file dialog to allow user to choose an image file
    File selectedFile = fileChooser.showOpenDialog(null);

    if (selectedFile != null) {
        // Copy selected image file to the specified file path
        try {
            File destFile = new File("C:\\Users\\Ilef\\Desktop\\GenesisTeam_MaktabtiApp-\\DesktopApp\\src\\edu\\esprit\\upload\\" + selectedFile.getName());
            FileUtils.copyFile(selectedFile, destFile);
            System.out.println("Image file copied successfully.");
        } catch (IOException ex) {
            System.out.println("Error copying image file: " + ex.getMessage());
        }
    } else {
        System.out.println("No image file selected.");
    }
}
}