/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.main;

import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author SADOK
 */
public class ReservationFXMain1 extends Application {
    
    @Override
    public void start(Stage primaryStage) {
         try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/FXMLReservation.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
           
            primaryStage.getIcons().add(new Image("/edu/esprit/gui/images/icon-app-logo.png"));
            primaryStage.setTitle("Reservation");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
}
