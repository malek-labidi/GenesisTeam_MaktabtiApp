/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.gui;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import edu.esprit.entities.Competition;
import edu.esprit.entities.login;
import edu.esprit.services.ServiceCompetition;
import edu.esprit.services.ServiceLivre;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author admin
 */
public class FXMLCompetitionController implements Initializable {

    @FXML
    private ListView<Competition> competition_list;
    @FXML
    private Label competition_label;
    @FXML
    private Button btn_ajouter;
    @FXML
    private Button btn_modifier;
    @FXML
    private Button btn_supprimer;
    @FXML
    private ImageView pdf;
    @FXML
    private TextField search;
    private List<Competition> e1 = new ArrayList<>();
    private login Log_in = login.getInstance();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        ServiceCompetition sc = new ServiceCompetition();

        this.e1 = sc.getAll();
        affiche();

        if (Log_in.getRole().equals("Client") || Log_in.getRole().equals("Auteur")) {
            btn_ajouter.setVisible(false);
            btn_modifier.setVisible(false);
            btn_supprimer.setVisible(false);
            pdf.setVisible(false);
        }

    }

    @FXML
    private void ajouterCompetition(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("FXMLAjouterCompetition.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void modifierCompetition(ActionEvent event) {
        int id = competition_list.getSelectionModel().getSelectedItem().getId_competition();
        System.out.println(id);
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Confirmation Modification");
        dialog.setHeaderText("Voulez-vous vraiment modifier la competition N°" + id + "?");

        ButtonType buttonTypeYes = new ButtonType("Oui", ButtonBar.ButtonData.YES);
        ButtonType buttonTypeNo = new ButtonType("Non", ButtonBar.ButtonData.NO);

        dialog.getDialogPane().getButtonTypes().addAll(buttonTypeYes, buttonTypeNo);

        Optional<ButtonType> result = dialog.showAndWait();

        if (result.isPresent() && result.get() == buttonTypeYes) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLModifierCompetition.fxml"));
                Parent root = loader.load();
                FXMLModifierCompetitionController controller = loader.getController();
                controller.getCompetition(id);
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        } else {
            dialog.close();
        }

    }

    @FXML
    private void supprimerCompetition(ActionEvent event) {
        int id = competition_list.getSelectionModel().getSelectedItem().getId_competition();
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Confirmation Suppression");
        dialog.setHeaderText("Voulez-vous vraiment supprimer la competition N°" + id + "?");

        ButtonType buttonTypeYes = new ButtonType("Oui", ButtonBar.ButtonData.YES);
        ButtonType buttonTypeNo = new ButtonType("Non", ButtonBar.ButtonData.NO);

        dialog.getDialogPane().getButtonTypes().addAll(buttonTypeYes, buttonTypeNo);

        Optional<ButtonType> result = dialog.showAndWait();

        if (result.isPresent() && result.get() == buttonTypeYes) {
            ServiceCompetition sc = new ServiceCompetition();
            sc.delete(id);
            System.out.println(id);
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Competition deleted", ButtonType.OK);
            alert.show();
            affiche();
        } else {
            dialog.close();
        }

    }

    public void affiche() {
        ServiceCompetition se = new ServiceCompetition();
        competition_list.setItems(FXCollections.observableArrayList(this.e1));

        // Définir des cellules personnalisée pour afficher les informations sur la compétition
        competition_list.setCellFactory(list -> new CompetitionListCell());

    }

    @FXML
    private void TelechargerPDF(MouseEvent event) {

        ServiceCompetition sc = new ServiceCompetition();
        List<String[]> a = new ArrayList<>();
        ServiceLivre sl = new ServiceLivre();
        ArrayList<String[]> data = new ArrayList<>();
        for (Competition competition : sc.getAll()) {
            String[] row = new String[7];
            row[0] = competition.getNom();
            row[1] = sl.getOneById(competition.getId_livre()).getTitre();
            row[2] = competition.getRecompense();
            row[3] = competition.getLien_competition();
            row[4] = competition.getListe_participants();
            row[5] = competition.getDate_debut().toString();
            row[6] = competition.getDate_fin().toString();

            data.add(row);
        }
        try {
            // generate the PDF file
            Document document = new Document();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, baos);
            document.open();

            // create an Image object
            // Image logo = Image.getInstance("logo.png");
            // set the position of the image in the document
            // logo.setAbsolutePosition(100, 700);
            // add the image to the document
            //document.add(logo);
            PdfPTable table = new PdfPTable(7);
            table.addCell("Nom");
            table.addCell("Livre");
            table.addCell("Rcompense");
            table.addCell("Lien");
            table.addCell("Liste participants");
            table.addCell("Date Début");
            table.addCell("Date fin");

            for (String[] row : data) {
                for (String cell : row) {
                    table.addCell(cell);
                }
            }
            document.add(table);

            document.addTitle("personne");
            document.addCreationDate();
            document.close();

            // prompt the user to save the file
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save PDF File");
            fileChooser.setInitialFileName("Maktabti-competitions.pdf");
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
            File file = fileChooser.showSaveDialog(null);
            if (file != null) {
                FileOutputStream fos = new FileOutputStream(file);
                baos.writeTo(fos);
                fos.close();
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "PDF Telecherger avec succés", ButtonType.OK);
            alert.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void chercherCompetition(KeyEvent event) {

        filter();
        affiche();
    }

    // Définir un ListCell personnalisé pour afficher les informations sur la compétition
    private class CompetitionListCell extends javafx.scene.control.ListCell<Competition> {

        @Override
        public void updateItem(Competition competition, boolean empty) {
            super.updateItem(competition, empty);

            if (empty || competition == null) {
                setText(null);
                setGraphic(null);
            } else {
                Label nameLabel = new Label(competition.getNom());
                nameLabel.setStyle("-fx-font-weight: bold;");
                ServiceLivre sl = new ServiceLivre();

                Label idLabel = new Label("Livre: " + sl.getOneById(competition.getId_livre()).getTitre());
                Label rewardLabel = new Label("Récompense: " + competition.getRecompense());
                Label participantsLabel = new Label("Participants: " + competition.getListe_participants());
                Label linkLabel = new Label("Lien: " + competition.getLien_competition());
                Label startDateLabel = new Label("Date de début: " + competition.getDate_debut());
                Label endDateLabel = new Label("Date de fin: " + competition.getDate_fin());
                Button participer = new Button("participer");

                participer.setStyle(" -fx-background-color: #f8a375;-fx-text-fill: white;-fx-font-size: 16px; -fx-background-radius: 8px; -fx-font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif;");

                if (competition.getDate_fin().before(Date.valueOf(LocalDate.now()))) {
                    participer.setDisable(true);
                    participer.setStyle("-fx-background-color: #C0C0C0;-fx-text-fill: white;-fx-font-size: 16px; -fx-background-radius: 8px; -fx-font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif;");

                } else {
                    participer.setDisable(false);
                }
                if (Log_in.getRole().equals("Administrateur")) {
                    participer.setVisible(false);
                }

                participer.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        //  int id_u = 3;

                        System.out.println("hello");
                        int id = competition.getId_competition();
                        Dialog<ButtonType> dialog = new Dialog<>();
                        dialog.setTitle("confirmer participation");
                        dialog.setHeaderText("Voulez-vous vraiment participer à la competition " + competition.getNom() + " ?");
                        dialog.setContentText("dans cette competition vous allez repondre a quelques questions concernant le livre " + sl.getOneById(competition.getId_livre()).getTitre());

                        ButtonType buttonTypeYes = new ButtonType("Oui", ButtonBar.ButtonData.YES);
                        ButtonType buttonTypeNo = new ButtonType("Non", ButtonBar.ButtonData.NO);

                        dialog.getDialogPane().getButtonTypes().addAll(buttonTypeYes, buttonTypeNo);

                        Optional<ButtonType> result = dialog.showAndWait();

                        if (result.isPresent() && result.get() == buttonTypeYes) {
                            try {
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLQuestion.fxml"));
                                Parent root = loader.load();
                                FXMLQuestionController controller = loader.getController();
                                controller.getQuestion(id);
                                Scene scene = new Scene(root);
                                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                                stage.setScene(scene);
                                stage.show();
                            } catch (IOException ex) {
                                System.out.println(ex.getMessage());
                            }
                        } else {
                            dialog.close();
                        }
                    }
                });

                VBox vbox = new VBox(nameLabel, idLabel, rewardLabel, participantsLabel, linkLabel, startDateLabel, endDateLabel, participer);
                vbox.setSpacing(5);

                setGraphic(vbox);
            }
        }
    }

    public void filter() {

        String s = search.getCharacters().toString();
        ServiceCompetition e = new ServiceCompetition();
        this.e1 = e.getAll();

        if (!s.isEmpty()) {
            this.e1 = ServiceCompetition.filterByName(this.e1, s);

        }
    }

}
