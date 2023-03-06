/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.gui;

import edu.esprit.entities.Competition;
import edu.esprit.entities.ResultatQuiz;
import edu.esprit.entities.Utilisateur;
import edu.esprit.services.ServiceCompetition;
import edu.esprit.services.ServiceResultatQuiz;
import edu.esprit.services.ServiceUtilisateur;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;

import javafx.scene.control.ComboBox;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.paint.Color;

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


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //chart();
        ServiceCompetition sc = new ServiceCompetition();
        List<Competition> lc=sc.getAll();
        for(Competition c : lc){
            competition.getItems().add(c.getNom());
        }
        competition.getSelectionModel().select(lc.get(0).getNom());
        getchart(lc.get(0).getNom());
        
         competition.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
        getchart(competition.getSelectionModel().getSelectedItem());
    });
        
    }
    
    private void getchart(String nom) {

    series.getData().clear();
    chartRsult.getData().clear();
    ServiceResultatQuiz sr = new ServiceResultatQuiz();
    List<ResultatQuiz> r = sr.getOneByCompetitionName(nom);
    ServiceUtilisateur su = new ServiceUtilisateur();
   

    for (ResultatQuiz q : r) {
        Utilisateur u = su.getOneById(q.getId_client());
        series.getData().add(new XYChart.Data<>(u.getPrenom()+" "+u.getNom(), q.getScore()));
    }
    series.setName("Score");

    chartRsult.setLegendVisible(true);
    chartRsult.getData().addAll(series);
}

}
