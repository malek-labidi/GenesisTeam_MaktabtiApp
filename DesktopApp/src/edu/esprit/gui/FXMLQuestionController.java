/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.gui;

import edu.esprit.api.MailEnregistrerReponsesQuestion;
import edu.esprit.entities.Competition;
import edu.esprit.entities.Question;
import edu.esprit.entities.ResultatQuiz;
import edu.esprit.entities.Utilisateur;
import edu.esprit.entities.login;
import edu.esprit.services.ServiceCompetition;
import edu.esprit.services.ServiceLivre;
import edu.esprit.services.ServiceResultatQuiz;
import edu.esprit.services.ServiceUtilisateur;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author admin
 */
public class FXMLQuestionController implements Initializable {

    private int id;

    @FXML
    private Button valider;
    @FXML
    private VBox container;
    private List<ToggleGroup> toggleGroups = new ArrayList<>();
    private login Log_in = login.getInstance();


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

    }

    //afficher le formulaire de la quiz
    public void getQuestion(int id) {
        this.id = id;
        ServiceCompetition sc = new ServiceCompetition();
        List<Question> questions = sc.getListQuestion(id);
        VBox container = new VBox();

        for (Question q : questions) {
            ToggleGroup toggleGroup = new ToggleGroup();
            System.out.println(q);

            Label labelQuestion = new Label(q.getQuestion());
            RadioButton choix1 = new RadioButton(q.getChoix1());
            RadioButton choix2 = new RadioButton(q.getChoix2());
            RadioButton choix3 = new RadioButton(q.getChoix3());

            container.getChildren().addAll(labelQuestion, choix1, choix2, choix3);
            container.setSpacing(5);
            choix1.setToggleGroup(toggleGroup);
            choix2.setToggleGroup(toggleGroup);
            choix3.setToggleGroup(toggleGroup);
            toggleGroups.add(toggleGroup);

        }
        this.container.getChildren().addAll(container);
    }

    //enregister les reponses de clients + verification
    @FXML
    private void enregisterResponses(ActionEvent event) {
        ServiceCompetition sc = new ServiceCompetition();
        List<Question> questions = sc.getListQuestion(id);
        
       
       int idquiz= questions.get(1).getId_quiz();
        //List<String>
        int score = 0;
        int questionIndex = 0;
        List<String> rep = new ArrayList<>();

        int reponsesDonnees = 0;
        for (Node node : container.getChildren()) {
            if (node instanceof VBox) {
                VBox vBox = (VBox) node;

                for (Node childNode : vBox.getChildren()) {
                    if (childNode instanceof RadioButton) {
                        RadioButton radioButton = (RadioButton) childNode;

                        // Vérifie si le bouton est sélectionné
                        if (radioButton.isSelected()) {
                            Question question = questions.get(questionIndex);
                           rep.add(radioButton.getText());

                            // Vérifie si la réponse correspond à la réponse correcte
                            if (radioButton.getText().equals(question.getReponse_correct())) {
                                score++;
                            }

                            questionIndex++;
                            reponsesDonnees++;

                        }
                    }
                }

            }
        }
        if (reponsesDonnees < questions.size()) {
            Image image = new Image("/edu/esprit/gui/images/warning.png");

            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(50);
            imageView.setFitHeight(50);
            Notifications notif = Notifications.create();
            notif.graphic(imageView);
            notif.text("Veuillez répondre à toutes les questions avant de soumettre vos réponses !");
            notif.title("Message d'avertissement");
            notif.hideAfter(Duration.seconds(5));
            notif.show();
        } else {
            System.out.println("Score: " + score);
             Competition comp = sc.getOneById(id);
             ServiceUtilisateur su = new ServiceUtilisateur();
             
            MailEnregistrerReponsesQuestion.sendEmail(su.getOneById(Log_in.getId()),comp);
            ServiceResultatQuiz sr = new ServiceResultatQuiz();
            ResultatQuiz rq = new ResultatQuiz(Log_in.getId(), idquiz, score, rep);
            sc.ajouterParticipant(Log_in.getId(), comp.getId_competition());
            System.out.println(rq);
            sr.ajouter(rq);
            System.out.println(sr.getAll());
            
            Image image = new Image("/edu/esprit/gui/images/check.png");

            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(50);
            imageView.setFitHeight(50);
            Notifications notif = Notifications.create();
            notif.graphic(imageView);
            notif.text("Votre réponse a été enregistrer avec succés");
            notif.title("Message succès");
            notif.hideAfter(Duration.seconds(5));
            notif.show();
            
             try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLMaktabti.fxml"));
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
}
