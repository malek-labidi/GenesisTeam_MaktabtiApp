/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.gui;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import edu.esprit.api.MailLivre;
import edu.esprit.entities.Categorie;
import edu.esprit.entities.Livre;
import edu.esprit.entities.Utilisateur;
import edu.esprit.entities.login;
import edu.esprit.services.ServiceCategorie;
import edu.esprit.services.ServiceCompetition;
import edu.esprit.services.ServiceLivre;
import edu.esprit.services.ServiceQuiz;
import edu.esprit.services.ServiceUtilisateur;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author Saleh
 */
public class LivreController implements Initializable {

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
    private int id;
    @FXML
    private ImageView PDF;
    @FXML
    private TextField search;
    private List<Livre> e1 = new ArrayList();
    @FXML
    private TextField fileImage;
    @FXML
    private Button image;
    private File imageFile;
    @FXML
    private Button btn_stat;
    @FXML
    private Button pausebutton;
    @FXML
    private Button musicButton;
private login Log_in = login.getInstance();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        ServiceLivre se = new ServiceLivre();
        this.e1 = se.getAll();
        affiche1();
        ServiceCategorie sc = new ServiceCategorie();
        List<String> categories = new ArrayList<>();
        for (Categorie cat : sc.getAll()) {
            categories.add(cat.getNom());
        }
        listcategorie.setItems(FXCollections.observableArrayList(categories));
        ServiceUtilisateur su = new ServiceUtilisateur();
        List<String> auteurs = new ArrayList<>();
        su.getAll().stream().filter((u) -> ("Auteur".equals(u.getRole()))).forEachOrdered((u) -> {
            auteurs.add(u.getNom() + " " + u.getPrenom());
        });

        listauteur.setItems(FXCollections.observableArrayList(auteurs));
           if (Log_in.getRole().equals("Client")) {
            cat_add.setVisible(false);
            cat_del.setVisible(false);
            cat_update.setVisible(false);
            PDF.setVisible(false);
        }

    }

    
    
    String path = "C:\\xampp1\\htdocs\\music\\Alok & Alan Walker.mp3";
    Media media = new Media(new File(path).toURI().toString());
    MediaPlayer mediaPlayer = new MediaPlayer(media);
    @FXML
    private void addlivre(ActionEvent event) throws FileNotFoundException {

        if (titre.getText().isEmpty() || langue.getText().isEmpty() || resume.getText().isEmpty() || isbn.getText().isEmpty() || nb_pages.getText().isEmpty() || prix.getText().isEmpty()) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Aucun champ vide n'est accepté!", ButtonType.OK);
            a.showAndWait();
        } else {

            ServiceCategorie sc = new ServiceCategorie();
            List<Categorie> categories = sc.getAll();
            int id = -1;
            for (Categorie l : categories) {
                if (l.getNom().equals(listcategorie.getSelectionModel().getSelectedItem())) {
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
            Livre l = new Livre(id1, id, titre.getText(), Date.valueOf(date_pub.getValue()), langue.getText(), Integer.parseInt(isbn.getText()), Integer.parseInt(nb_pages.getText()), resume.getText(), (int) Double.parseDouble(prix.getText()), null);

            try {
                FileInputStream f = new FileInputStream(imageFile);
                l.setImage(f);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            MailLivre.sendEmail(su.getOneById(id1), l);
            //Livre l = new Livre(id1, id, titre.getText(), Date.valueOf(date_pub.getValue()), langue.getText(), Integer.parseInt(isbn.getText()), Integer.parseInt(nb_pages.getText()), resume.getText(), Integer.parseInt(prix.getText()),null);
            sl.ajouter(l);
            affiche();
            titre.clear();
            date_pub.setValue(null);
            langue.clear();
            isbn.clear();
            nb_pages.clear();

            resume.clear();
            prix.clear();

            Alert a = new Alert(Alert.AlertType.INFORMATION, "Livre ajouté !", ButtonType.OK);
            a.showAndWait();

        }
    }

    @FXML
    private void updatelivre(ActionEvent event) {
        id = livreview.getSelectionModel().getSelectedItem().getId_livre();
        System.out.println(id);
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Confirmation Modification");
        dialog.setHeaderText("Voulez-vous vraiment modifier le livre N°" + id + "?");

        ButtonType buttonTypeYes = new ButtonType("Oui", ButtonBar.ButtonData.YES);
        ButtonType buttonTypeNo = new ButtonType("Non", ButtonBar.ButtonData.NO);

        dialog.getDialogPane().getButtonTypes().addAll(buttonTypeYes, buttonTypeNo);

        Optional<ButtonType> result = dialog.showAndWait();

        if (result.isPresent() && result.get() == buttonTypeYes) {

            if (titre.getText().isEmpty() || langue.getText().isEmpty() || resume.getText().isEmpty() || isbn.getText().isEmpty() || nb_pages.getText().isEmpty() || prix.getText().isEmpty()) {
                Alert a = new Alert(Alert.AlertType.ERROR, "Aucun champ vide n'est accepté!", ButtonType.OK);
                a.showAndWait();
            } else {

                ServiceCategorie sc = new ServiceCategorie();
                List<Categorie> categories = sc.getAll();
                int id2 = -1;
                for (Categorie l : categories) {
                    if (l.getNom().equals(listcategorie.getSelectionModel().getSelectedItem())) {
                        id2 = l.getId_categorie();
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
                Livre l = new Livre(id, id1, id2, titre.getText(), Date.valueOf(date_pub.getValue()), langue.getText(), Integer.parseInt(isbn.getText()), Integer.parseInt(nb_pages.getText()), resume.getText(), Float.parseFloat(prix.getText()), null);
                sl.modifier(l);
                affiche();
                titre.clear();
                date_pub.setValue(null);
                langue.clear();
                isbn.clear();
                nb_pages.clear();

                resume.clear();
                prix.clear();

                Alert a = new Alert(Alert.AlertType.INFORMATION, "Livre modifié !", ButtonType.OK);
                a.showAndWait();

            }

        } else {
            dialog.close();
        }

    }

    @FXML
    private void deletelivre(ActionEvent event) {
        int id =livreview.getSelectionModel().getSelectedItem().getId_livre();
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Confirmation suppression");
        dialog.setHeaderText("Voulez-vous vraiment supprimer la competition N°" + id + "?");
        dialog.setContentText("Attention ! cette action va supprimer le livre ainsi que son quiz et sa compétition ");

        ButtonType buttonTypeYes = new ButtonType("Oui", ButtonBar.ButtonData.YES);
        ButtonType buttonTypeNo = new ButtonType("Non", ButtonBar.ButtonData.NO);

        dialog.getDialogPane().getButtonTypes().addAll(buttonTypeYes, buttonTypeNo);

        Optional<ButtonType> result = dialog.showAndWait();

        if (result.isPresent() && result.get() == buttonTypeYes) {

            ServiceQuiz sq = new ServiceQuiz();
            ServiceCompetition sc = new ServiceCompetition();
            ServiceLivre sl = new ServiceLivre();
            Livre l = new Livre();
            l = livreview.getSelectionModel().getSelectedItem();
            sc.deletebyLivre(id);
            sq.delete(id);
            sl.supprimerLivre(l);
            affiche();
            Alert a = new Alert(Alert.AlertType.INFORMATION, "Livre supprimé !", ButtonType.OK);
            a.showAndWait();
        }
        
        
        
        
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

            String cat = c.getNom();
            namesC.add(cat);
        }
        listcategorie.setItems(FXCollections.observableArrayList(namesC));

    }

    @FXML
    private void afficheForm(MouseEvent event) {
        id = livreview.getSelectionModel().getSelectedItem().getId_livre();

        ServiceCategorie sc = new ServiceCategorie();
        List<Categorie> cats = sc.getAll();
        List<String> names = new ArrayList<>();

        cats.forEach((l) -> {
            names.add(l.getNom());
        });
        listcategorie.setItems(FXCollections.observableArrayList(names));

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

        ServiceLivre sl = new ServiceLivre();
        Livre l = sl.getOneById(id);
        titre.setText(l.getTitre());
        resume.setText(l.getResume());
        langue.setText(l.getLangue());
        prix.setText(String.valueOf(l.getPrix()));
        nb_pages.setText(String.valueOf(l.getNb_pages()));
        date_pub.setValue(l.getDate_pub().toLocalDate());
        isbn.setText(String.valueOf(l.getIsbn()));
        listcategorie.getSelectionModel().select(sc.getOneById(l.getId_categorie()).getNom());
        String s = ss.getOneById(l.getId_auteur()).getNom() + " " + ss.getOneById(l.getId_auteur()).getPrenom();
        listauteur.getSelectionModel().select(s);

    }

    @FXML
    private void genererPDF(MouseEvent event) {

        ServiceLivre sc = new ServiceLivre();
        List<String[]> a = new ArrayList<>();
        ServiceCategorie sl = new ServiceCategorie();
        ServiceUtilisateur su = new ServiceUtilisateur();
        ArrayList<String[]> data = new ArrayList<>();
        for (Livre livre : sc.getAll()) {
            String[] row = new String[8];
            row[0] = livre.getTitre();
            row[1] = sl.getOneById(livre.getId_categorie()).getNom();
            row[2] = livre.getDate_pub().toString();
            row[3] = livre.getLangue();
            row[4] = livre.getResume();
            row[5] = String.valueOf(livre.getIsbn());
            row[6] = String.valueOf(livre.getPrix());
            row[7] = su.getOneById(livre.getId_auteur()).getNom() + " " + su.getOneById(livre.getId_auteur()).getPrenom();
            data.add(row);
        }

        System.out.println(sc.getAll());
        try {
            // generate the PDF file
            Document document = new Document();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, baos);
            document.open();

            PdfPTable table = new PdfPTable(8);
            table.addCell("Tire");
            table.addCell("Categorie");
            table.addCell("Date_pub");
            table.addCell("Langue");
            table.addCell("Resume");
            table.addCell("Isbn");
            table.addCell("Prix");
            table.addCell("Auteur");

            for (String[] row : data) {
                for (String cell : row) {
                    table.addCell(cell);
                }
            }
            document.add(table);

            document.addTitle("livre");
            document.close();

            // prompt the user to save the file
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save PDF File");
            fileChooser.setInitialFileName("Maktabti-livres.pdf");
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
    private void search_livre(KeyEvent event) {
        filter();
        affiche1();
    }

    public void filter() {

        String s = search.getCharacters().toString();
        ServiceLivre e = new ServiceLivre();
        this.e1 = e.getAll();

        if (!s.isEmpty()) {
            this.e1 = ServiceLivre.filterByName(this.e1, s);

        }
    }

    public void affiche1() {

        ServiceLivre se = new ServiceLivre();
        livreview.setItems(FXCollections.observableArrayList(this.e1));
    }

    @FXML
    private void Buttonimage(ActionEvent event) {

        FileChooser filech = new FileChooser();
        imageFile = filech.showOpenDialog(null);
        if (imageFile != null) {
            fileImage.setText(imageFile.getAbsolutePath());
        }

    }

    @FXML
    private void stat(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("PieChart.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void pause(ActionEvent event) {
        
         mediaPlayer.pause();
       // Image img = new Image("fllogo.png");
        Notifications notificationBuilder = Notifications.create()
                .title("Musique")
                .text("      Musique Arrêtée").hideAfter(Duration.seconds(5))
                .position(Pos.BOTTOM_RIGHT);
        notificationBuilder.darkStyle();
        notificationBuilder.show();
    }

    @FXML
    private void play(ActionEvent event) {
        
            mediaPlayer.play();
      //  Image img = new Image("C:\\Users\\Saleh\\Desktop\\GenesisTeam_MaktabtiApp\\DesktopApp\\src\\edu\\esprit\\gui\\images\\ticket.png");
        Notifications notificationBuilder = Notifications.create()
                .title("Musique")
                .text("      Musique Jouée").hideAfter(Duration.seconds(5))
                .position(Pos.BOTTOM_RIGHT);
        notificationBuilder.darkStyle();
        notificationBuilder.show();
    }
}
