/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.gui;
import edu.esprit.entities.Utilisateur;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import edu.esprit.entities.Utilisateur;
import edu.esprit.services.ServiceUtilisateur;
import edu.esprit.util.DataSource;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
/**
 * FXML Controller class
 *
 * @author wassi
 */
public class UtilisateurrController implements Initializable {
        DataSource cnx = null;
     Statement st = null;
     ServiceUtilisateur  rcd = new ServiceUtilisateur ();

    @FXML
    private Pane pnlCustomer;
    @FXML
    private Pane pnlOrders;
    @FXML
    private Pane pnlMenus;
    @FXML
    private Pane pnlOverview;
    @FXML
    private TableView<Utilisateur> cat_list;
    @FXML
    private TableColumn<Utilisateur, String> nom;
    @FXML
    private TableColumn<Utilisateur, String> prenom;
    @FXML
    private TableColumn<Utilisateur, String> email;
    @FXML
    private TableColumn<Utilisateur, String> password;
    @FXML
    private TableColumn<Utilisateur, Integer> numtel;
    @FXML
    private TableColumn<Utilisateur, String> role;
    @FXML
    private TextField cat_name;
    @FXML
    private TextField cat_prenom;
    @FXML
    private TextField cat_email;
    @FXML
    private PasswordField cat_password;
    @FXML
    private TextField cat_tel;
    @FXML
    private TextField cat_role;
    @FXML
    private Button cat_del;
    @FXML
    private Button btnOverview;
    @FXML
    private Button btnCustomers;
    @FXML
    private Button btnMenus;
    @FXML
    private Button btnPackages;
    @FXML
    private Button btnPackages1;
    @FXML
    private Button btnOrders;
    @FXML
    private Button btnSettings;
    @FXML
    private Button btnSignout;
    @FXML
    private Button cat_add;
    @FXML
    private Button cat_update;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        affiche();
        cat_list.setOnMouseClicked(this::getuser);

    }    



    @FXML
    private void updateuser(ActionEvent event) {
            if (cat_name.getText().isEmpty() || cat_prenom.getText().isEmpty() || cat_email.getText().isEmpty() || cat_tel.getText().isEmpty() || cat_password.getText().isEmpty() || cat_role.getText().isEmpty()  ) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Aucun champ vide n'est accepté", ButtonType.OK);
            a.showAndWait();
        } else if (!Validateemail()) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Email  invalide!", ButtonType.OK);
            a.showAndWait();
        }else if (!Validatenumtel()) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Numéro de telephone  invalide!", ButtonType.OK);
            a.showAndWait();
        }
        else if (!Validatemotdepasse()) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Mot de passe  invalide!", ButtonType.OK);
            a.showAndWait();   
        }
        else {
            ServiceUtilisateur su = new ServiceUtilisateur();
            Utilisateur u = new Utilisateur(cat_name.getText(),cat_prenom.getText(),cat_email.getText(),cat_password.getText(),Integer.parseInt(cat_tel.getText()), cat_role.getText() ) {} ;
            String var1=cat_name.getText();
            u= cat_list.getSelectionModel().getSelectedItem();
            su.modifierutilisateur(cat_name.getText(),u);
            affiche();
            cat_name.clear();
            cat_prenom.clear();
            cat_email.clear();
            cat_password.clear();
            cat_tel.clear();
            cat_role.clear();

            Alert a = new Alert(Alert.AlertType.CONFIRMATION, "User Updated Successfully !", ButtonType.OK);
            a.showAndWait();
            
                  /*  ServiceUtilisateur su = new ServiceUtilisateur();

        String var1=cat_name.getText();
        String var2=cat_prenom.getText();
        String var3=cat_email.getText();
        String var4=cat_password.getText();
        String var5=cat_tel.getText();
        String var6=cat_role.getText();
      

       Utilisateur u =new Utilisateur() {} ;
        
      u.setNom(var1);
      u.setPrenom(var2);
      u.setEmail(var3);
      u.setMot_de_passe(var4);
      u.setnum_telephone(Integer.parseInt(var5));
      u.setRole(var6);
      
            u= useview.getSelectionModel().getSelectedItem();
            su.modifier2(u);
            affiche();
            cat_name.clear();
            cat_prenom.clear();
            cat_email.clear();
            cat_password.clear();
            cat_tel.clear();
            cat_role.clear(); 
        }*/

        }
    }

    @FXML
     private void adduser(ActionEvent event) {
         if (cat_name.getText().isEmpty() || cat_prenom.getText().isEmpty() || cat_email.getText().isEmpty() || cat_tel.getText().isEmpty() || cat_password.getText().isEmpty() || cat_role.getText().isEmpty()  ) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Aucun champ vide n'est accepté", ButtonType.OK);
            a.showAndWait();
        } else if (!Validateemail()) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Email  invalide!", ButtonType.OK);
            a.showAndWait();
        }else if (!Validatenumtel()) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Numéro de telephone  invalide!", ButtonType.OK);
            a.showAndWait();
        }
        else if (!Validatemotdepasse()) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Mot de passe  invalide!", ButtonType.OK);
            a.showAndWait();   
        }
        else {
             ServiceUtilisateur su = new ServiceUtilisateur();
            Utilisateur u = new Utilisateur(cat_name.getText(),cat_prenom.getText(),cat_email.getText(),cat_password.getText(),Integer.parseInt(cat_tel.getText()), cat_role.getText() ) {} ;
            su.ajouter(u);
            affiche();
            cat_name.clear();
            cat_prenom.clear();
            cat_email.clear();
            cat_password.clear();
            cat_tel.clear();
            cat_role.clear();

            Alert a = new Alert(Alert.AlertType.INFORMATION, "User added !", ButtonType.OK);
            a.showAndWait();
        }
        
    } 
    @FXML
    private void deleteuser(ActionEvent event) {
            ServiceUtilisateur su = new ServiceUtilisateur();
            Utilisateur u= new Utilisateur() {} ;   
            u= cat_list.getSelectionModel().getSelectedItem();
            su.supprimerUtilisateur(u);
            affiche();
            Alert a = new Alert(Alert.AlertType.CONFIRMATION, "User Deleted Successfully !", ButtonType.OK);
            a.showAndWait();
    }
    
/********************************************************************************************************************************************/


public void affiche() {
    try {
        Statement stmt = DataSource.getInstance().getCnx().createStatement();

        ResultSet rs = stmt.executeQuery("SELECT nom,prenom,email,mot_de_passe,num_telephone,role FROM utilisateur");

        ObservableList<Utilisateur> list = FXCollections.observableArrayList();

        while (rs.next()) {
            String nom = rs.getString("nom");
            String prenom = rs.getString("prenom");
            String email = rs.getString("email");
            String password = rs.getString("mot_de_passe");
            int numtel = rs.getInt("num_telephone");
            String role = rs.getString("role");


            Utilisateur user = new Utilisateur(nom,prenom,email,password, numtel ,role ) {};
            list.add(user);
        }
System.out.println("Number of rows in list: " + list.size());

        nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        password.setCellValueFactory(new PropertyValueFactory<>("mot_de_passe"));
        numtel.setCellValueFactory(new PropertyValueFactory<>("num_telephone"));
        role.setCellValueFactory(new PropertyValueFactory<>("role"));

        cat_list.setItems(list);

    } catch (SQLException e) {
        e.printStackTrace();
    }
}
    
    void getuser(MouseEvent event) {
        int index = cat_list.getSelectionModel().getSelectedIndex();
    if (index <= -1) {
        return;
    }
    
    
    cat_name.setText(nom.getCellData(index));
    cat_prenom.setText(prenom.getCellData(index));
    cat_email.setText(email.getCellData(index));
    cat_password.setText(password.getCellData(index).toString());
    cat_tel.setText(String.valueOf(numtel.getCellData(index)));
    cat_role.setText(role.getCellData(index));


    }
         
    
/*******************************************************************************************************************************************/
     private boolean Validateemail() {
        Pattern p = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
        Matcher m = p.matcher(cat_email.getText());
        if (((cat_email.getText().length() > 8))&& (m.find() && m.group().equals(cat_email.getText()))) {
            cat_email.setEffect(null);
            return true;
        } else {
            Alert a = new Alert(Alert.AlertType.ERROR, "Email Invalide", ButtonType.OK);
            a.showAndWait();
            return false;
        }
    }
     
     

    private boolean Validatenumtel() {
        
        Pattern p = Pattern.compile("^\\d{8}$");
        Matcher m = p.matcher(cat_tel.getText());

        if ((cat_tel.getText().length() == 8)
                || (m.find() && m.group().equals(cat_tel.getText()))) {
            cat_tel.setEffect(null);
            return true;
        } else {
            Alert a = new Alert(Alert.AlertType.ERROR, "Numéro de telephone invalide", ButtonType.OK);
            a.showAndWait();
            return false;
        }
    }

    private boolean Validatemotdepasse() {
        Pattern p = Pattern.compile("[a-zA-Z_0-9]+");
        Matcher m = p.matcher(cat_password.getText());
                if (((cat_password.getText().length() > 7))
                && (m.find() && m.group().equals(cat_password.getText()))) {
            cat_password.setEffect(null);
            return true;
        } else {
            Alert a = new Alert(Alert.AlertType.ERROR, "Mot de passe invalide", ButtonType.OK);
            a.showAndWait();
            return false;
        }
        
    }

    @FXML
    private void handleClicks(ActionEvent event) {
    }

    @FXML
    private void handleCategoryDetail(MouseEvent event) {
    }




}


