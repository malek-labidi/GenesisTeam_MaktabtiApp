/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.gui;

import edu.esprit.entities.Competition;
import edu.esprit.entities.Livre;
import edu.esprit.services.ServiceCompetition;
import edu.esprit.services.ServiceLivre;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author admin
 */
public class FXMLModifierCompetitionController implements Initializable {

    @FXML
    private TextField nom;
    @FXML
    private DatePicker date_debut;
    @FXML
    private DatePicker date_fin;
    @FXML
    private TextField lien;
    @FXML
    private TextField recompense;
    @FXML
    private ListView<String> list_livre;
    @FXML
    private Button btn_modifier;
    @FXML
    private Button btn_annuler;
    private int id;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

    }

    @FXML
    private void ModifierCompetition(ActionEvent event) {

        int id1 = -1;
        if (recompense.getText().isEmpty() || lien.getText().isEmpty() || nom.getText().isEmpty() || lien.getText().isEmpty()) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Aucun champ vide n'est accepté!", ButtonType.OK);
            a.showAndWait();
        } else if (isValidLink(lien.getText())) {
            Alert a = new Alert(Alert.AlertType.ERROR, "lien invalide !", ButtonType.OK);
            a.showAndWait();
        } else if (date_debut.getValue().isBefore(LocalDate.now()) || date_debut.getValue().isAfter(date_fin.getValue())) {
            Alert a = new Alert(Alert.AlertType.ERROR, "date debut invalide !", ButtonType.OK);
            a.showAndWait();
        } else if (date_fin.getValue().isBefore(LocalDate.now()) || date_fin.getValue().isBefore(date_debut.getValue())) {
            Alert a = new Alert(Alert.AlertType.ERROR, "date fin invalide !", ButtonType.OK);
            a.showAndWait();
        } else {
            // try {
            ServiceCompetition sp = new ServiceCompetition();
            ServiceLivre sl = new ServiceLivre();
            List<Livre> livres = sl.getAll();

            for (Livre l : livres) {
                if (l.getTitre().equals(list_livre.getSelectionModel().getSelectedItem())) {
                    id1 = l.getId_livre();
                    break;
                }
            }
            if (id1 == -1) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Aucun livre correspondant n'a été trouvé", ButtonType.CLOSE);
                alert.show();
            }

            System.out.println(id1 + " " + id);

            Competition p = new Competition(id, id1, recompense.getText(),lien.getText(), nom.getText(), Date.valueOf(date_debut.getValue()), Date.valueOf(date_fin.getValue()));
            sp.modifier(p);
            System.out.println(p);
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "competition updated", ButtonType.OK);
            alert.show();
            /* } catch (SQLException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.CLOSE);
                alert.show();
            }*/
        }
    }

    @FXML
    private void Annuler(ActionEvent event) {

    }

    public boolean isValidLink(String link) {
        String regex = "^(http|https)://[a-zA-Z0-9-.]+\\.[a-zA-Z]{2,}(\\S*)?$";
        return link.matches(regex);
    }

    public void getCompetition(int id) {
        this.id = id;

        ServiceLivre sl = new ServiceLivre();
        List<Livre> livres = sl.getAll();
        List<String> names = new ArrayList<>();

        livres.forEach((l) -> {
            names.add(l.getTitre());
        });
        list_livre.setItems(FXCollections.observableArrayList(names));

        ServiceCompetition sc = new ServiceCompetition();
        System.out.println(id);
        Competition c = sc.getOneById(id);
        System.out.println(c);
        nom.setText(c.getNom());
        date_debut.setValue(c.getDate_debut().toLocalDate());
        date_fin.setValue(c.getDate_fin().toLocalDate());
        lien.setText(c.getLien_competition());
        recompense.setText(c.getRecompense());
        list_livre.getSelectionModel().select(sl.getOneById(c.getId_livre()).getTitre());
    }

}
