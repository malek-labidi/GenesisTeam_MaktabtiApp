package edu.esprit.gui;


import edu.esprit.util.DataSource;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


/**
 * FXML Controller class
 *
 * @author Asus
 */
public class PieChartController implements Initializable {

    @FXML
    private PieChart piechart;
    ObservableList< PieChart.Data> piechartdata;
    ArrayList< String> p = new ArrayList< String>();
    ArrayList< Integer> c = new ArrayList< Integer>();
    @FXML
    private ImageView retour;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) { 
        loadData();
       
        piechart.setData(piechartdata);
        piechart.setTitle("Statistique des Avis de Maktabti");
    }    
    
     public void loadData() {

        String query = "select COUNT(*) as count ,id_categorie from livre GROUP BY id_categorie "; //ORDER BY P asc

        piechartdata = FXCollections.observableArrayList();

        Connection cnx = DataSource.getInstance().getCnx();

        try {

            ResultSet rs = cnx.createStatement().executeQuery(query);
            while (rs.next()) {
                
                piechartdata.add(new PieChart.Data(rs.getString("id_categorie"), rs.getInt("count")));
                p.add(rs.getString("id_categorie"));
                c.add(rs.getInt("count"));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }  

    

    @FXML
    private void retun_btn(MouseEvent event) {
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

