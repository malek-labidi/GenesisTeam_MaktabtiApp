/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.gui;

import edu.esprit.entities.Utilisateur;
import edu.esprit.services.ServiceUtilisateur;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

/**
 * FXML Controller class
 *
 * @author wassi
 */
public class RegisterController implements Initializable {

    @FXML
    private TextField email;
    @FXML
    private TextField numtel;
    @FXML
    private Button login;
    @FXML
    private Button forgotpasswd;
    @FXML
    private TextField prenom;
    @FXML
    private TextField nom;
    @FXML
    private TextField role;
    @FXML
    private PasswordField password;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    
    @FXML
     private void Registration(ActionEvent event) {
         if (nom.getText().isEmpty() || prenom.getText().isEmpty() || email.getText().isEmpty() || numtel.getText().isEmpty() || password.getText().isEmpty() || role.getText().isEmpty()  ) {
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
        else if (role.getText()=="Administrateur") {
            Alert a = new Alert(Alert.AlertType.ERROR, "You can't register as Administrator!", ButtonType.OK);
            a.showAndWait();   
         }
        else {
             ServiceUtilisateur su = new ServiceUtilisateur();
            Utilisateur u = new Utilisateur(nom.getText(),prenom.getText(),email.getText(),password.getText(),Integer.parseInt(numtel.getText()), role.getText() ) {} ;
            su.ajouter(u);
            nom.clear();
            prenom.clear();
            email.clear();
            password.clear();
            numtel.clear();
            role.clear();

            Alert a = new Alert(Alert.AlertType.INFORMATION, "You have been registred succesfully !", ButtonType.OK);
            a.showAndWait();
        }
        
    } 


    
    /*********************************************CONTROLE DE SAISIE***************************************************************************/    
     private boolean Validateemail() {
        Pattern p = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
        Matcher m = p.matcher(email.getText());
        if (((email.getText().length() > 8))&& (m.find() && m.group().equals(email.getText()))) {
            email.setEffect(null);
            return true;
        } else {
            Alert a = new Alert(Alert.AlertType.ERROR, "Email Invalide", ButtonType.OK);
            a.showAndWait();
            return false;
        }
    }
     
     

    private boolean Validatenumtel() {
        
        Pattern p = Pattern.compile("^\\d{8}$");
        Matcher m = p.matcher(numtel.getText());

        if ((numtel.getText().length() == 8)
                || (m.find() && m.group().equals(numtel.getText()))) {
            numtel.setEffect(null);
            return true;
        } else {
            Alert a = new Alert(Alert.AlertType.ERROR, "Numéro de telephone invalide", ButtonType.OK);
            a.showAndWait();
            return false;
        }
    }

    private boolean Validatemotdepasse() {
        Pattern p = Pattern.compile("[a-zA-Z_0-9]+");
        Matcher m = p.matcher(password.getText());
                if (((password.getText().length() > 7))
                && (m.find() && m.group().equals(password.getText()))) {
            password.setEffect(null);
            return true;
        } else {
            Alert a = new Alert(Alert.AlertType.ERROR, "Mot de passe invalide", ButtonType.OK);
            a.showAndWait();
            return false;
        }
        
    }


    
}
