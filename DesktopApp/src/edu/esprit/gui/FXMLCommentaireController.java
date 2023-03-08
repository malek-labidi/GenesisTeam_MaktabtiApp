package edu.esprit.gui;

import edu.esprit.entities.Commentaire;
import edu.esprit.entities.Evenement;
import edu.esprit.services.ServiceCommentaire;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author SADOK
 */
public class FXMLCommentaireController implements Initializable {

    @FXML
    private GridPane grid;
    @FXML
    private TextField comment;
    @FXML
    private Button submit;
    private int evenementId;
    private int row = 0;
    @FXML
    private ImageView back;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

    }

    public void setCommentList() {
        ServiceCommentaire sc = new ServiceCommentaire();

        List<Commentaire> lc = sc.getCommentairesByEvenement(evenementId);
        try {

            for (int i = 0; i < lc.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXMLCommentairemodel.fxml"));

                VBox box = fxmlLoader.load();
                FXMLCommentairemodelController controller = fxmlLoader.getController();
                controller.setCommentaire(lc.get(i));
                grid.add(box, 0, i);
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void submit(ActionEvent event) {
        String c = comment.getText();
        ServiceCommentaire sc = new ServiceCommentaire();
        boolean containsBadWords = sc.containsBadWords(c);

        if (containsBadWords) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "The comment contains bad words you'll be banned and no longer can comment");
            alert.showAndWait();
            comment.clear();
            submit.setDisable(true);
        } else {
            sc.ajouter(new Commentaire(1, evenementId, c));
            comment.clear();
            submit.setDisable(false);
            Image image = new Image("/edu/esprit/gui/images/check.png");

            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(50);
            imageView.setFitHeight(50);
            Notifications notif = Notifications.create();
            notif.graphic(imageView);
            notif.text("Votre commentaire a été effectué avec succès");
            notif.title("Message succès");
            notif.hideAfter(Duration.seconds(5));
            notif.show();
        }

        setCommentList();
    }

    public void setEvenementId(int id) {
        // set the event id in the controller
        this.evenementId = id;
        // you can then use this id to fetch the comments for this event
        setCommentList();
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
}
