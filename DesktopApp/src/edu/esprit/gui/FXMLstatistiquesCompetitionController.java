/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.gui;

import edu.esprit.api.MailRecompense;
import edu.esprit.entities.Competition;
import edu.esprit.entities.ResultatQuiz;
import edu.esprit.entities.Utilisateur;
import edu.esprit.services.ServiceCompetition;
import edu.esprit.services.ServiceResultatQuiz;
import edu.esprit.services.ServiceUtilisateur;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;

import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.paint.Color;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author admin
 */
public class FXMLstatistiquesCompetitionController implements Initializable {

    @FXML
    private BarChart<?, ?> chartRsult;
    @FXML
    private ComboBox<String> competition;
    private final XYChart.Series series = new XYChart.Series<>();
    @FXML
    private Label datesemaine;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //chart();

        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        LocalDate today = LocalDate.now();
        int currentWeek = today.get(weekFields.weekOfWeekBasedYear());
        // Calcul de la date de début de la semaine en cours
        LocalDate startOfWeek = today.with(TemporalAdjusters.previousOrSame(weekFields.getFirstDayOfWeek()));
        startOfWeek = startOfWeek.with(weekFields.dayOfWeek(), 1);

        // Calcul de la date de fin de la semaine en cours
        LocalDate endOfWeek = startOfWeek.plusDays(6);
        datesemaine.setText("Semaine : " + startOfWeek + " ->" + endOfWeek);

        ServiceCompetition sc = new ServiceCompetition();
        List<Competition> lc = sc.getAll();
        for (Competition c : lc) {
            if (c.getDate_debut().toLocalDate().get(weekFields.weekOfWeekBasedYear()) == currentWeek || c.getDate_fin().toLocalDate().get(weekFields.weekOfWeekBasedYear()) == currentWeek) {
                competition.getItems().add(c.getNom());
               boolean sended = sendRecompense(c, c.getNom());
                if (sended == true) {
                    Platform.runLater(() -> {
                        Image image = new Image("/edu/esprit/gui/images/confetti.png");

                        ImageView imageView = new ImageView(image);
                        imageView.setFitWidth(50);
                        imageView.setFitHeight(50);
                        Notifications notif = Notifications.create();
                        notif.graphic(imageView);
                        notif.text("Vous avez des gagnants cette semaine, Email de félicitations est envoyée avec succés !");
                        notif.title("Féliciations");
                        notif.show();
                    });
                }

            }
        }

        competition.getSelectionModel().select(competition.getItems().get(0));
        getchart(competition.getItems().get(0));

        competition.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            getchart(competition.getSelectionModel().getSelectedItem());
        });

    }

    private boolean sendRecompense(Competition c, String nom) {
        boolean result = false;
        ServiceResultatQuiz sr = new ServiceResultatQuiz();
        List<ResultatQuiz> r = sr.getOneByCompetitionName(nom);
        ServiceUtilisateur su = new ServiceUtilisateur();
        int maxScore = 0;
        for (ResultatQuiz q : r) {
            if (q.getScore() > maxScore) {
                maxScore = q.getScore();
            }
        }
        for (ResultatQuiz q : r) {
            Utilisateur u = su.getOneById(q.getId_client());
            if (Date.valueOf(LocalDate.now().plusDays(1)).equals(c.getDate_fin()) && q.getScore() == maxScore) {
                MailRecompense.sendEmail(u, c);
                result = true;
            }
        }
        return result;

    }

    private void getchart(String nom) {

        series.getData().clear();
        chartRsult.getData().clear();
        ServiceResultatQuiz sr = new ServiceResultatQuiz();
        List<ResultatQuiz> r = sr.getOneByCompetitionName(nom);
        ServiceUtilisateur su = new ServiceUtilisateur();

        for (ResultatQuiz q : r) {
            Utilisateur u = su.getOneById(q.getId_client());
            series.getData().add(new XYChart.Data<>(u.getPrenom() + " " + u.getNom(), q.getScore()));
        }
        series.setName("Score");

        chartRsult.setLegendVisible(true);
        chartRsult.getData().addAll(series);
        chartRsult.setTitle("Résultat de la competition ");
    }

}
