/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.gui;

import edu.esprit.api.MailLivreAjouterACompetition;
import edu.esprit.entities.Competition;
import edu.esprit.entities.Livre;
import edu.esprit.services.ServiceCompetition;
import edu.esprit.services.ServiceLivre;
import edu.esprit.services.ServiceUtilisateur;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
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
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author admin
 */
public class FXMLAjouterCompetitionController implements Initializable {

    @FXML
    private TextField nom;
    @FXML
    private DatePicker date_debut;
    @FXML
    private DatePicker date_fin;
    @FXML
    private Button ajouter;
    @FXML
    private Button annuler;
    @FXML
    private TextField lien;
    @FXML
    private TextField recompense;
    @FXML
    private ListView<String> livre;
    @FXML
    private ImageView retour;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        affiche();
    }

    @FXML
    private void btn_ajouter(ActionEvent event) {
        int id = -1;
        int idauteur = -1;
        if (recompense.getText().isEmpty() || lien.getText().isEmpty() || nom.getText().isEmpty() || lien.getText().isEmpty()) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Aucun champ vide n'est accepté !", ButtonType.OK);
            a.showAndWait();
        } else if (!isValidLink(lien.getText())) {
            Alert a = new Alert(Alert.AlertType.ERROR, "lien invalide !", ButtonType.OK);
            a.showAndWait();
        } else if (date_debut.getValue().isBefore(LocalDate.now()) || date_debut.getValue().isAfter(date_fin.getValue())) {
            Alert a = new Alert(Alert.AlertType.ERROR, "date debut invalide !", ButtonType.OK);
            a.showAndWait();
        } else if (date_fin.getValue().isBefore(LocalDate.now()) || date_fin.getValue().isBefore(date_debut.getValue())) {
            Alert a = new Alert(Alert.AlertType.ERROR, "date fin invalide !", ButtonType.OK);
            a.showAndWait();
        } else {
            try {
                ServiceCompetition sp = new ServiceCompetition();
                ServiceLivre sl = new ServiceLivre();
                List<Livre> livres = sl.getAll();
                System.out.println(livres);
                for (Livre l : livres) {
                    if (l.getTitre().equals(livre.getSelectionModel().getSelectedItem())) {
                        id = l.getId_livre();
                        idauteur = l.getId_auteur();
                        break;
                    }
                }
                if (id == -1) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Aucun livre correspondant n'a été trouvé !", ButtonType.CLOSE);
                    alert.show();
                }

                System.out.println(id);
                ServiceUtilisateur su = new ServiceUtilisateur();
                Competition p = new Competition(id, recompense.getText(), "", lien.getText(), nom.getText(), Date.valueOf(date_debut.getValue()), Date.valueOf(date_fin.getValue()));
                MailLivreAjouterACompetition.sendEmail(su.getOneById(idauteur), p, livre.getSelectionModel().getSelectedItem());
                sp.ajouter(p);
                affiche();
                clear();

                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Competition ajouter avec succés", ButtonType.OK);
                alert.show();
            } catch (SQLException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.CLOSE);
                alert.show();
            }
        }
    }

    //verifer si un lien valide
    public boolean isValidLink(String link) {
        String regex = "^(http|https)://[a-zA-Z0-9-.]+\\.[a-zA-Z]{2,}(\\S*)?$";
        return link.matches(regex);
    }

    @FXML
    private void btn_annuler(ActionEvent event) {
        clear();
    }

    public void clear() {
        nom.clear();
        date_debut.setValue(null);
        date_fin.setValue(null);
        lien.clear();
        recompense.clear();
        livre.getSelectionModel().clearSelection();
    }

    public void affiche() {
        ServiceLivre sl = new ServiceLivre();
        List<Livre> livres = sl.getAll();
        List<String> names = new ArrayList<>();
        livres.forEach((l) -> {
            names.add(l.getTitre());
        });
        livre.setItems(FXCollections.observableArrayList(names));
    }

    @FXML
    private void retour(MouseEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLMaktabti.fxml"));
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
