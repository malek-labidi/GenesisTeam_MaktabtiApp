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
import edu.esprit.services.ServiceCompetition;
import edu.esprit.services.ServiceLivre;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
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
import javafx.scene.image.ImageView;
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        affiche();
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
        competition_list.setItems(FXCollections.observableArrayList(se.getAll()));

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

                VBox vbox = new VBox(nameLabel, idLabel, rewardLabel, participantsLabel, linkLabel, startDateLabel, endDateLabel);
                vbox.setSpacing(5);

                setGraphic(vbox);
            }
        }
    }

}
