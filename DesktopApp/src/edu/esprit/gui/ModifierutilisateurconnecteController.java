/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.gui;

import edu.esprit.entities.login;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import edu.esprit.entities.Competition;
import edu.esprit.entities.Evenement;
import edu.esprit.entities.Livre;
import edu.esprit.entities.Utilisateur;
import edu.esprit.entities.login;
import edu.esprit.services.ServiceCompetition;
import edu.esprit.services.ServiceEvenement;
import edu.esprit.services.ServiceLivre;
import edu.esprit.services.ServiceUtilisateur;
import edu.esprit.util.DataSource;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
/**
 * FXML Controller class
 *
 * @author wassi
 */
public class ModifierutilisateurconnecteController implements Initializable {

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
    private Button update_user;
    login Log_in = login.getInstance();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        afficheruser();
        }    
    
    
        public void afficheruser(){
            System.out.println(Log_in);
            cat_name.setText(Log_in.getNom());
            cat_prenom.setText(Log_in.getPrenom());
            cat_email.setText(Log_in.getUsername());
            cat_password.setText(Log_in.getPassword());
            cat_tel.setText(Integer.toString(Log_in.getNumtel()));


    }

    @FXML
    private void updateuser(ActionEvent event) {
        
    int id = Log_in.getId();
    String role = Log_in.getRole();

            Dialog<ButtonType> dialog = new Dialog<>();
         System.out.println(id);
     if (cat_name.getText().isEmpty() || cat_prenom.getText().isEmpty() || cat_email.getText().isEmpty() || cat_tel.getText().isEmpty() || cat_password.getText().isEmpty() ) {
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
       /* else if (cat_role.getText()!="Administrateur"  || cat_role.getText()!="Client"  ||cat_role.getText()!="Auteur" ) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Role invalide!", ButtonType.OK);
            a.showAndWait();   
        } */
        else {
         dialog.setHeaderText("Voulez-vous vraiment modifier l'utilisateur N°" + id + "?");
        ButtonType buttonTypeYes = new ButtonType("Oui", ButtonBar.ButtonData.YES);
        ButtonType buttonTypeNo = new ButtonType("Non", ButtonBar.ButtonData.NO);
        dialog.getDialogPane().getButtonTypes().addAll(buttonTypeYes, buttonTypeNo);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == buttonTypeYes) {
            ServiceUtilisateur su = new ServiceUtilisateur();
            Utilisateur u = new Utilisateur(id,cat_name.getText(),cat_prenom.getText(),cat_email.getText(),cat_password.getText(),Integer.parseInt(cat_tel.getText()) , role  ) {} ;
            su.modifier3(u);
            System.out.println(u);


            
            Alert a = new Alert(Alert.AlertType.CONFIRMATION, "User Updated Successfully !", ButtonType.OK);
            a.showAndWait();
        }

    }
    }
        
    /*********************************************CONTROLE DE SAISIE***************************************************************************/    
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
}
