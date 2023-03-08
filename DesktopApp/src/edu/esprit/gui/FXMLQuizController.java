/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.gui;

import edu.esprit.entities.Question;
import edu.esprit.entities.Quiz;
import edu.esprit.services.ServiceCompetition;
import edu.esprit.services.ServiceLivre;
import edu.esprit.services.ServiceQuestion;
import edu.esprit.services.ServiceQuiz;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author admin
 */
public class FXMLQuizController implements Initializable {

    @FXML
    private ListView<Quiz> listQuiz;
    @FXML
    private ListView<Question> listQuestions;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        affiche();
    }

    //recupérer les donneés et mettre dans la list view quiz
    public void affiche() {
        ServiceQuiz sq = new ServiceQuiz();
        listQuiz.setItems(FXCollections.observableArrayList(sq.getAll()));
        System.out.println(sq.getAll());
        listQuiz.setCellFactory(list -> new QuizListCell());
    }

    //recuperer et afficher les questions de la quiz
    @FXML
    private void afficheList(MouseEvent event) {
        List<Question> l = new ArrayList<>();
        ServiceQuestion q = new ServiceQuestion();
        l = q.getQuestionByIdQuiz(listQuiz.getSelectionModel().getSelectedItem().getId_quiz());
        listQuestions.setItems(FXCollections.observableArrayList(l));
        listQuestions.setCellFactory(list -> new QuestionListCell());
    }
    
    // Définir un ListCell personnalisé pour afficher les informations sur la quiz
    private class QuizListCell extends javafx.scene.control.ListCell<Quiz> {

        @Override
        public void updateItem(Quiz quiz, boolean empty) {
            super.updateItem(quiz, empty);

            if (empty || quiz == null) {
                setText(null);
                setGraphic(null);
            } else {
                ServiceCompetition sc = new ServiceCompetition();
                Label nameLabel = new Label("nom :" + sc.getOneById(quiz.getId_competition()).getNom());

                ServiceLivre sl = new ServiceLivre();

                Label livreLabel = new Label("Livre: " + sl.getOneById(quiz.getId_livre()).getTitre());

                VBox vbox = new VBox(livreLabel, nameLabel);
                vbox.setSpacing(5);

                setGraphic(vbox);
            }
        }
    }
    
    // Définir un ListCell personnalisé pour afficher les informations sur la question
    private class QuestionListCell extends javafx.scene.control.ListCell<Question> {

        @Override
        public void updateItem(Question question, boolean empty) {
            super.updateItem(question, empty);

            if (empty || question == null) {
                setText(null);
                setGraphic(null);
            } else {
                ServiceCompetition sc = new ServiceCompetition();
                Label qLabel = new Label(question.getQuestion());
                qLabel.setStyle("-fx-font-weight: bold;");
                Label ch1Label = new Label("1-" + question.getChoix1());
                Label ch2Label = new Label("2-" + question.getChoix2());
                Label ch3Label = new Label("3-" + question.getChoix3());
                Label corrLabel = new Label("Réponse correcte :" + question.getReponse_correct());
                corrLabel.setStyle("-fx-text-fill: green;");

                VBox vbox = new VBox(qLabel, ch1Label, ch2Label, ch3Label, corrLabel);
                vbox.setSpacing(5);

                setGraphic(vbox);
            }
        }
    }

}
