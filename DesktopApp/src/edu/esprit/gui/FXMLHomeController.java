/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.gui;

import edu.esprit.entities.Livre;
import edu.esprit.entities.login;
import edu.esprit.services.ServiceLivre;
import edu.esprit.services.ServiceLivrePanier;
import edu.esprit.services.ServiceUtilisateur;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author SADOK
 */
public class FXMLHomeController implements Initializable {

    @FXML
    private GridPane list_livre;
    private login Log_in = login.getInstance();
    private List<Livre> e1 = new ArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        affiche();

    }

    public void affiche() {

        ServiceLivre se = new ServiceLivre();
        List<Livre> livres = se.getAll();
        int col = 0;
        int row = 0;
        int maxCols = 3;

        list_livre.setPadding(new Insets(10));
        list_livre.setHgap(10);
        list_livre.setVgap(10);
        for (Livre livre : livres) {
            // Créez une carte de livre pour chaque livre
            Image image = new Image(livre.getImage());
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(150); // définit la largeur de l'image à 50 pixels
            imageView.setFitHeight(200); // définit la hauteur de l'image à 50 pixels
            Label titleLabel = new Label(livre.getTitre());
            Label prixLabel = new Label(String.valueOf(livre.getPrix()));

            Button ajouter = new Button();
            Image image2 = new Image("/edu/esprit/gui/images/panier.jpg");

            ImageView imageView2 = new ImageView(image2);
            imageView2.setFitWidth(20); // définit la largeur de l'image à 50 pixels
            imageView2.setFitHeight(20); // définit la hauteur de l'image à 50 pixels
            ajouter.setGraphic(imageView2);

            ajouter.setStyle(" -fx-background-color: #f8a375;-fx-text-fill: white;-fx-font-size: 16px; -fx-background-radius: 8px; -fx-font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif;");

            if (Log_in.getRole().equals("Administrateur") || Log_in.getRole().equals("Auteur")) {
                ajouter.setVisible(false);
            }

            ajouter.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {

                    Dialog<ButtonType> dialog = new Dialog<>();
                    dialog.setTitle("confirmer ajouter livre");
                    dialog.setHeaderText("Voulez-vous vraiment ajouter ce livre dans votre panier ?");

                    ButtonType buttonTypeYes = new ButtonType("Oui", ButtonBar.ButtonData.YES);
                    ButtonType buttonTypeNo = new ButtonType("Non", ButtonBar.ButtonData.NO);

                    dialog.getDialogPane().getButtonTypes().addAll(buttonTypeYes, buttonTypeNo);

                    Optional<ButtonType> result = dialog.showAndWait();

                    if (result.isPresent() && result.get() == buttonTypeYes) {
                        ServiceLivrePanier slp = new ServiceLivrePanier();
                        slp.ajouterLivrePanier(livre.getId_livre(), Log_in.getId());

                        Image image = new Image("/edu/esprit/gui/images/check.png");

                        ImageView imageView = new ImageView(image);
                        imageView.setFitWidth(50);
                        imageView.setFitHeight(50);
                        Notifications notif = Notifications.create();
                        notif.graphic(imageView);
                        notif.text("livre ajouter au panier avec succés!");
                        notif.title("Message de succés");
                        notif.hideAfter(Duration.seconds(5));
                        notif.show();

                    } else {
                        dialog.close();
                    }
                }
            });
            VBox cardBox = new VBox();
            cardBox.getChildren().addAll(imageView, titleLabel, prixLabel, ajouter);
            cardBox.setSpacing(10);
            cardBox.setPadding(new Insets(10));
            DropShadow shadow = new DropShadow();
            shadow.setOffsetX(2);
            shadow.setOffsetY(2);
            shadow.setColor(Color.rgb(50, 50, 50, 0.5));
            cardBox.setStyle("-fx-background-color: white;-fx-border-radius: 20px;-fx-background-radius: 20px;");

// application de l'effet d'ombre à la carte
            cardBox.setEffect(shadow);
            // Ajoutez la carte de livre à la grille
            list_livre.add(cardBox, col, row);
            col++;
            if (col >= maxCols) {
                col = 0;
                row++;
            }
        }
    }

}
