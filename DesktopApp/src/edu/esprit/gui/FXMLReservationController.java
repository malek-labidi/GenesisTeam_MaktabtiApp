/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.gui;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import edu.esprit.api.MailReservation;
import edu.esprit.entities.Evenement;
import edu.esprit.services.ServiceEvenement;
import edu.esprit.services.ServiceLivre;
import edu.esprit.services.ServiceUtilisateur;
import java.awt.Desktop;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author SADOK
 */
public class FXMLReservationController implements Initializable {

    @FXML
    private Button reserver_btn;
    private List<Evenement> e1 = new ArrayList<>();
    @FXML
    private Spinner<Integer> num_ticket;
    private int id;
    private ImageView pdf;
    private Scene billetScene;
    @FXML
    private Button but_pdf;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        ServiceEvenement se = new ServiceEvenement();

        this.e1 = se.getAll();

        SpinnerValueFactory<Integer> value = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 200);
        value.setValue(0);
        num_ticket.setValueFactory(value);
        but_pdf.setDisable(true);
        
    }

    @FXML

    private void reserver(ActionEvent event) throws WriterException, DocumentException, IOException {
        //Evenement selectedEvent = event_view.getSelectionModel().getSelectedItem();
        //if (selectedEvent == null) {
        // No event selected, do nothing
        // return;
        //}
        ServiceEvenement se = new ServiceEvenement();
        Evenement e = se.getOneById(id);

        if (e.getNb_ticket() > 0) {
            // There are tickets available, decrement the nb_ticket field and display a success message
            int numTickets = num_ticket.getValueFactory().getValue(); // Get the number of tickets from a TextField or other input control
            if (numTickets > e.getNb_ticket()) {

                Alert alert = new Alert(AlertType.ERROR, "Not enough tickets available.", ButtonType.OK);
                alert.showAndWait();
                return;
            }
            e.setNb_ticket(e.getNb_ticket() - numTickets);

            se.modifier(e); // Update the event in the database
            MailReservation.sendEmail();
            String message = "Reservation completed successfully!";
            Alert alert = new Alert(AlertType.INFORMATION, message, ButtonType.OK);
            alert.showAndWait();
            but_pdf.setDisable(false);

        } else {
            // No tickets available, display a message to the user
            String message = "No tickets available. Please check again later.";
            Alert alert = new Alert(AlertType.INFORMATION, message, ButtonType.OK);
            alert.showAndWait();
        }
    }

    public void getEvenement(int id) {
        this.id = id;

    }

    @FXML
    private void back(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLEvenement.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

    }

    @FXML
    private void downloadpdf(ActionEvent event) {
        ServiceUtilisateur su = new ServiceUtilisateur();
        ServiceEvenement se = new ServiceEvenement();
        Evenement e = se.getOneById(id);

        try {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXMLTicket.fxml"));
            Parent root = fxmlLoader.load();
            billetScene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(billetScene);
            stage.show();
            FXMLTicketController controller = fxmlLoader.getController();
            controller.QR(su.getOneById(e.getId_auteur()), e);

        } catch (IOException ex) {

            ex.printStackTrace();
        }

        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null) {
            boolean success = job.showPrintDialog(null);
            if (success) {
                job.printPage(billetScene.getRoot());
                job.endJob();
            }
            if (billetScene != null) {
                billetScene.getWindow().hide();
            }
        }
    }

}
