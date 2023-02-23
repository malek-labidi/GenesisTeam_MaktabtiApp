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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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

    public void setId(int id) {
        this.id = id;
    }

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
        list_livre.setItems(FXCollections.observableArrayList(names));

        ServiceCompetition sc = new ServiceCompetition();
        System.out.println(id);
        Competition c = sc.getOneById(id);
        System.out.println(c);
        /* nom.setText(c.getNom());
        date_debut.setValue(c.getDate_debut().toLocalDate());
        date_fin.setValue(c.getDate_fin().toLocalDate());
        lien.setText(c.getLien_competition());
        recompense.setText(c.getRecompense());
        list_livre.getSelectionModel().select(sl.getOneById(c.getId_livre()).getTitre());*/

    }

    @FXML
    private void ModifierCompetition(ActionEvent event) {
    }

    @FXML
    private void Annuler(ActionEvent event) {

    }

}
