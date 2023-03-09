/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.gui;

import edu.esprit.entities.Commande;
import edu.esprit.entities.Livre;
import edu.esprit.entities.PanierLivre;
import edu.esprit.services.ServiceCommande;
import edu.esprit.services.ServiceLivre;
import edu.esprit.services.ServiceLivrePanier;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 */
public class FXMLPanierLivreController implements Initializable {

    @FXML
    private ListView<PanierLivre> Lview;
    @FXML
    private Button buttonC;
    @FXML
    private Label prixTotalLabel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ServiceLivrePanier pl = new ServiceLivrePanier();
        prixTotalLabel.setText(String.valueOf(pl.calculTotalPrix(1)));
        affiche();

    }

    public void affiche() {
        ServiceLivrePanier sp = new ServiceLivrePanier();
        Lview.setItems(FXCollections.observableArrayList(sp.getAll()));

        // Définir des cellules personnalisée pour afficher les informations sur la compétition
        Lview.setCellFactory(list -> new PanierLivreListCell());
    }

    //@FXML
    /*private void ValiderCommande(ActionEvent event) {
        ServiceLivrePanier sp = new ServiceLivrePanier();
        List<PanierLivre> panierLivres = sp.getAll();
        ServiceCommande sc = new ServiceCommande();

        for (PanierLivre panierLivre : panierLivres) {
            Livre livre = new ServiceLivre().getOneById(panierLivre.getId_livre());
            Commande commande = new Commande();

        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println("Error loading ListeCommandes view");
        }

    }
    
*/
    
    
    @FXML
private void ValiderCommande(ActionEvent event) {
    ServiceLivrePanier sp = new ServiceLivrePanier();
    List<PanierLivre> panierLivres = sp.getAll();
    
    ServiceCommande sc = new ServiceCommande();

    for (PanierLivre panierLivre : panierLivres) {
      //  Livre livre = new ServiceLivre().getOneById(panierLivre.getId_livre());
        
   
        // Ajouter la commande à la base de données
        Commande c = sc.validerCommande(panierLivre.getId_client());
        
        //sc.ajouter(c);
        
System.out.println(c);
System.out.println(panierLivre.getId_client());
        // Retirer le livre du panier
        //sp.supprimerLivrePanier(panierLivre.getId_livre());
    }

    // Afficher une notification de confirmation
    Image image = new Image("/edu/esprit/gui/images/check.png");
    ImageView imageView = new ImageView(image);
    imageView.setFitWidth(50);
    imageView.setFitHeight(50);
    Notifications notif = Notifications.create();
    notif.graphic(imageView);
    notif.text("Commande validée avec succès !");
    notif.title("Message de succès");
    notif.hideAfter(Duration.seconds(5));
    notif.show();

   
}

    

    // Définir un ListCell personnalisé pour afficher les informations sur le livre dans le panier
    private class PanierLivreListCell extends javafx.scene.control.ListCell<PanierLivre> {

        @Override
        public void updateItem(PanierLivre panierLivre, boolean empty) {
            super.updateItem(panierLivre, empty);
            ServiceLivre serviceLivre = new ServiceLivre();
            ServiceLivrePanier pl = new ServiceLivrePanier();
            


            if (empty || panierLivre == null) {
                setText(null);
                setGraphic(null);
            } else {
                Livre livre = serviceLivre.getOneById(panierLivre.getId_livre());
                Label titleLabel = new Label(livre.getTitre());
                titleLabel.setStyle("-fx-font-weight: bold;");

                Label priceLabel = new Label("Prix : " + livre.getPrix() + " DT");

                SpinnerValueFactory<Integer> value = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 200, panierLivre.getQuantite());
                Spinner spinner = new Spinner();
                spinner.setValueFactory(value);
                spinner.valueProperty().addListener((obs, oldValue, newValue) -> {
                    // Mettre à jour la quantité du livre dans le panier
                    panierLivre.setQuantite((int) newValue);
                    ServiceLivrePanier serviceLivrePanier = new ServiceLivrePanier();
                    System.out.println(panierLivre);
                    serviceLivrePanier.afficherLivresDansPanier(panierLivre.getId_panier());
                    serviceLivrePanier.ajouterLivrePanier(panierLivre.getId_livre(), panierLivre.getId_client());

                    Image image = new Image("/edu/esprit/gui/images/check.png");
                    ImageView imageView = new ImageView(image);
                    imageView.setFitWidth(50);
                    imageView.setFitHeight(50);
                    Notifications notif = Notifications.create();
                    notif.graphic(imageView);
                    notif.text("Votre panier est mis a jour avec succés !");
                    notif.title("Message de succés");
                    serviceLivrePanier.updatePanierLivre(panierLivre.getId_livre(), panierLivre.getId_client(), (int) newValue);

                    notif.hideAfter(Duration.seconds(5));
                    notif.show();

                });
                VBox vbox = new VBox(titleLabel, priceLabel, spinner);
                vbox.setSpacing(5);

                setGraphic(vbox);

            }
        }
    }
}
