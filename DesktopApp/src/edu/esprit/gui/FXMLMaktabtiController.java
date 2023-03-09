/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.gui;

import edu.esprit.entities.login;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author admin
 */
public class FXMLMaktabtiController implements Initializable {

    @FXML
    private Button home;
    @FXML
    private BorderPane bp;
    private int id;
    @FXML
    private Button livres;
    @FXML
    private Button commande;
    @FXML
    private Button categories;
    @FXML
    private Button reclamation;
    @FXML
    private Button messagerie;
    @FXML
    private Button competition;
    @FXML
    private Button resultat;
    @FXML
    private Button evenements;
    @FXML
    private Button offre;
    @FXML
    private Button account;
    @FXML
    private Button deletefidelite;
    @FXML
    private Button utilisateur;
    @FXML
    private Button logout;
    @FXML
    private Button quiz;
    @FXML
    private Button fidelite1;
    private login Log_in = login.getInstance();
 
    @FXML
    private Label username;
    private HBox centrepage;
    @FXML
    private ImageView panier;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        username.setText(Log_in.getPrenom()+" "+Log_in.getNom());
        String role = Log_in.getRole();
        if(role.equals("Administrateur")){
            reclamation.setVisible(false);
            fidelite1.setVisible(false);
            
            
        }else if(role.equals("Auteur")|| role.equals("Client")){
            categories.setVisible(false);
            commande.setVisible(false);
            resultat.setVisible(false);
            deletefidelite.setVisible(false);
            utilisateur.setVisible(false); 
            messagerie.setVisible(false);
            quiz.setVisible(false);
            offre.setVisible(false);
        } 
        
    }

    private void loadPage(String page) {
        Parent root = null;
        try {

            root = FXMLLoader.load(getClass().getResource(page + ".fxml"));
        } catch (IOException ex) {
            Logger.getLogger(FXMLMaktabtiController.class.getName()).log(Level.SEVERE, null, ex);
        }
       bp.setCenter(root);
    }

    @FXML
    private void Livres(ActionEvent event) {
        loadPage("Livre");
    }

    @FXML
    private void Categories(ActionEvent event) {
        loadPage("FXMLCategorie");
    }

    @FXML
    private void Commande(ActionEvent event) {
        loadPage("FXMLCommande");
    }

    @FXML
    private void Reclamation(ActionEvent event) {
        loadPage("FXMLReclamation");
    }

    @FXML
    private void Messagerie(ActionEvent event) {
        loadPage("FXMLMessagerie");
    }

    @FXML
    private void competitions(ActionEvent event) {
        loadPage("FXMLCompetition");
    }

    @FXML
    private void Resultat(ActionEvent event) {
        loadPage("FXMLstatistiquesCompetition");
    }

    @FXML
    private void events(ActionEvent event) {
        loadPage("FXMLEvenement");
    }

    @FXML
    private void addOffre(ActionEvent event) {
        loadPage("OffreAjout");
    }

    @FXML
    private void myAccount(ActionEvent event) {
        loadPage("Modifierutilisateurconnecte");
    }

    @FXML
    private void home(ActionEvent event) {
    }

    @FXML
    private void deleteFidelite(ActionEvent event) {
        loadPage("Affichage");
    }

    @FXML
    private void afficherfidelite(ActionEvent event) {
        loadPage("ConsulterFidelite");
    }

    @FXML
    private void utilisateurList(ActionEvent event) {
        loadPage("Utilisateur");

    }

    @FXML
    private void Logout(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Authentification.fxml"));
            Scene homaepageScene = new Scene(root);
            Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            appStage.setScene(homaepageScene);
            appStage.show();
        } catch (IOException ex) {
            Logger.getLogger(FXMLMaktabtiController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void afficherquiz(ActionEvent event) {
        loadPage("FXMLQuiz");
    }

    @FXML
    private void panier(MouseEvent event) {
        loadPage("FXMLPanierLivre");
    }

}
