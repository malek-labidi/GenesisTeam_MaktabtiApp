/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.gui;

import edu.esprit.util.DataSource;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author User
 */
public class RatingController implements Initializable {

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Slider slider;
    @FXML
    private Button btn_env;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void envoyerclick(ActionEvent event) {
         // Get the rating value
    int value = (int) slider.getValue();

    // Insert the rating into the database
    try (Connection conn = DataSource.getInstance().getCnx();
            PreparedStatement ps = conn.prepareStatement("INSERT INTO ratings (value) VALUES (?)")) {
        ps.setInt(1, value);
        ps.executeUpdate();
        System.out.println("Rating inserted successfully");
    } catch (SQLException ex) {
        System.out.println("Error inserting rating: " + ex.getMessage());
    }
    
    try {
            Parent root = FXMLLoader.load(getClass().getResource("FXMLMaktabti.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
}
