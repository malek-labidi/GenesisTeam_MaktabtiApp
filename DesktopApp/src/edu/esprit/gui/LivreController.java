/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.gui;

import edu.esprit.entities.Categorie;
import edu.esprit.entities.Livre;
import edu.esprit.entities.Utilisateur;
import edu.esprit.services.ServiceCategorie;
import edu.esprit.services.ServiceLivre;
import edu.esprit.services.ServiceUtilisateur;
import java.net.URL;
import java.sql.Date;
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
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author Saleh
 */
public class LivreController implements Initializable {

    @FXML
    private Button btnOverview;
    @FXML
    private Button btnCustomers;
    @FXML
    private Button btnMenus;
    @FXML
    private Button btnPackages;
    @FXML
    private Button btnPackages1;
    @FXML
    private Button btnOrders;
    @FXML
    private Button btnSettings;
    @FXML
    private Button btnSignout;
    @FXML
    private Pane pnlCustomer;
    @FXML
    private Pane pnlOrders;
    @FXML
    private Pane pnlMenus;
    @FXML
    private Pane pnlOverview;
    @FXML
    private TextField titre;
    @FXML
    private DatePicker date_pub;
    @FXML
    private TextField langue;
    @FXML
    private TextField isbn;
    @FXML
    private TextField nb_pages;
    @FXML
    private TextField resume;
    @FXML
    private TextField prix;
    @FXML
    private Button cat_add;
    @FXML
    private Button cat_update;
    @FXML
    private Button cat_del;
    private ListView<Livre> useview;
    @FXML
    private ListView<Livre> livreview;
    @FXML
    private ListView<String> listauteur;
    @FXML
    private ListView<String> listcategorie;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        affiche();
    }

    @FXML
    private void handleClicks(ActionEvent event) {
    }

    @FXML
    private void addlivre(ActionEvent event) {

        /*if (titre.getText().isEmpty() || langue.getText().isEmpty() || resume.getText().isEmpty() || isbn.getText().isEmpty() || nb_pages.getText().isEmpty() || prix.getText().isEmpty()) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Aucun champ vide n'est accepté!", ButtonType.OK);
            a.showAndWait();
        } else {*/

            ServiceCategorie sc = new ServiceCategorie();
            List<Categorie> categories = sc.getAll();
            int id = -1;
            for (Categorie l : categories) {
                if (l.getType_c().equals(listcategorie.getSelectionModel().getSelectedItem())) {
                    id = l.getId_categorie();
                    break;
                }
            }
            ServiceLivre sl = new ServiceLivre();

            ServiceUtilisateur su = new ServiceUtilisateur();
            List<Utilisateur> users = su.getAll();
            int id1 = -1;
            for (Utilisateur u : users) {
                String auteur = u.getNom() + " " + u.getPrenom();
                if (auteur.equals(listauteur.getSelectionModel().getSelectedItem())) {
                    id1 = u.getId();
                    break;
                }

            }
            Livre l = new Livre(id1, id, titre.getText(), Date.valueOf(date_pub.getValue()), langue.getText(), Integer.parseInt(isbn.getText()), Integer.parseInt(nb_pages.getText()), resume.getText(), Integer.parseInt(prix.getText()));
            sl.ajouter(l);
            affiche();
            titre.clear();
            date_pub.setValue(null);
            langue.clear();
            isbn.clear();
            nb_pages.clear();

            resume.clear();
            prix.clear();

            Alert a = new Alert(Alert.AlertType.INFORMATION, "Livre added !", ButtonType.OK);
            a.showAndWait();

        }
    

    @FXML
    private void updatelivre(ActionEvent event) {
    }

    @FXML
    private void deletelivre(ActionEvent event) {
        ServiceLivre sl = new ServiceLivre();
        Livre l = new Livre() {
        };
        l = livreview.getSelectionModel().getSelectedItem();
        sl.supprimerLivre(l);
        affiche();
    }

    public void affiche() {
        ServiceLivre su = new ServiceLivre();
        livreview.setItems(FXCollections.observableArrayList(su.getAll()));
        ServiceUtilisateur ss = new ServiceUtilisateur();
        List<Utilisateur> users = ss.getAll();
        List<String> namesU = new ArrayList<>();
        for (Utilisateur u : users) {
            if (u.getRole().equals("Auteur")) {
                String auteur = u.getNom() + " " + u.getPrenom();
                namesU.add(auteur);
            }
        }
        listauteur.setItems(FXCollections.observableArrayList(namesU));
        ServiceCategorie sc = new ServiceCategorie();
        List<Categorie> cats = sc.getAll();
        List<String> namesC = new ArrayList<>();
        for (Categorie c : cats) {

            String cat = c.getType_c();
            namesC.add(cat);
        }
        listcategorie.setItems(FXCollections.observableArrayList(namesC));

    }
}
