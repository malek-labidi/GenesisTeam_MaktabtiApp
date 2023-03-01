/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.gui;

import edu.esprit.entities.Competition;
import edu.esprit.entities.Evenement;
import edu.esprit.entities.Livre;
import edu.esprit.entities.Utilisateur;
import edu.esprit.services.ServiceCompetition;
import edu.esprit.services.ServiceEvenement;
import edu.esprit.services.ServiceLivre;
import edu.esprit.services.ServiceUtilisateur;
import edu.esprit.util.DataSource;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author wassi
 */
public class UtilisateurController implements Initializable {
        Connection cnx = DataSource.getInstance().getCnx();

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
    private Pane pnlCustomer;
    @FXML
    private Pane pnlOrders;
    @FXML
    private Pane pnlMenus;
    @FXML
    private TextField cat_name;
    @FXML
    private TextField cat_prenom;
    @FXML
    private TextField cat_email;
    @FXML
    private TextField cat_password;
    @FXML
    private TextField cat_tel;
    @FXML
    private TextField cat_role;
    @FXML
    private Button cat_add;
    @FXML
    private Button cat_update;
    @FXML
    private Button cat_del;
    @FXML
    private ListView<Utilisateur> useview;
    @FXML
    private Pane pnlOverview;

    private List<Utilisateur> e1 = new ArrayList<>();
    private int id;
    @FXML
    private TextField searchh;
    @FXML
    private Button reset;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        affiche();
        useview.setOnMouseClicked(this::getUtilisateur);
    }    

  @FXML
    private void handleClicks(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLMaktabti.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
        
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
     public void affiche() {
        ServiceUtilisateur su = new ServiceUtilisateur();
        useview.setItems(FXCollections.observableArrayList(su.getAll()));
        // Définir des cellules personnalisée pour afficher les informations sur l'utilisateur
        useview.setCellFactory(list -> new UtilisateurListCell());
     }
    
    /* @FXML
    private void adduser(ActionEvent event){
        
        String var1=cat_name.getText();
        String var2=cat_prenom.getText();
        String var3=cat_email.getText();
        String var4=cat_password.getText();
        int var5=Integer.parseInt(cat_tel.getText());
        String var6=cat_role.getText();

        Utilisateur u =new Utilisateur() {} ;

        ServiceUtilisateur su = new ServiceUtilisateur();
        
      u.setNom(var1);
      u.setPrenom(var2);
      u.setEmail(var3);
      u.setMot_de_passe(var4);
      u.setnum_telephone(var5);
      u.setRole(var6);
      
        su.ajouter(u);

    } */

    @FXML
    private void updateuser(ActionEvent event) {
        
        int id = useview.getSelectionModel().getSelectedItem().getId();
        System.out.println(id);
        Dialog<ButtonType> dialog = new Dialog<>();
        
        
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
            Utilisateur u = new Utilisateur(cat_name.getText(),cat_prenom.getText(),cat_email.getText(),cat_password.getText(),Integer.parseInt(cat_tel.getText()), cat_role.getText() ) {} ;
            su.modifier(u);
            System.out.println(u);
            affiche();
            cat_name.clear();
            cat_prenom.clear();
            cat_email.clear();
            cat_password.clear();
            cat_tel.clear();
            cat_role.clear();

            
            Alert a = new Alert(Alert.AlertType.CONFIRMATION, "User Updated Successfully !", ButtonType.OK);
            a.showAndWait();
        }
            
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
    
        public void getUtilisateur(MouseEvent event) {
        int index = useview.getSelectionModel().getSelectedIndex();
        Utilisateur u= new Utilisateur() {} ;


        ServiceUtilisateur su = new ServiceUtilisateur();
        u= useview.getSelectionModel().getSelectedItem();
        System.out.println(u);
        cat_name.setText(u.getNom());
        cat_prenom.setText(u.getPrenom());
        cat_email.setText(u.getEmail());
        cat_password.setText(u.getMot_de_passe());
        cat_tel.setText(Integer.toString(u.getnum_telephone()));
        cat_role.setText(u.getRole());
    }

    @FXML
    private void deleteuser(ActionEvent event) {
            ServiceUtilisateur su = new ServiceUtilisateur();
            Utilisateur u= new Utilisateur() {} ;   
            u= useview.getSelectionModel().getSelectedItem();
            su.supprimerUtilisateur(u);
            affiche();
            Alert a = new Alert(Alert.AlertType.CONFIRMATION, "User Deleted Successfully !", ButtonType.OK);
            a.showAndWait();
    }


    //Filtres
        public void filter() {
        
        String s = searchh.getCharacters().toString();
        ServiceUtilisateur su = new ServiceUtilisateur();
        this.e1 = su.getAll();
        
        
        if (!s.isEmpty()) {
            //this.useview = ServiceUtilisateur.filterByName(this.useview, s);

        }
    }

    @FXML
    private void search(KeyEvent event) {
        
        filter();
        affiche();
    }

    @FXML
    private void onreset(ActionEvent event) {
        cat_name.setText(null);
        cat_prenom.setText(null);
        cat_email.setText(null);
        cat_password.setText(null);
        cat_tel.setText(null);
        cat_role.setText(null);
    }
    
    //Afficher  la ListView Bien Ordonnée
      private class UtilisateurListCell extends javafx.scene.control.ListCell<Utilisateur> {
        @Override
        public void updateItem(Utilisateur Utilisateur, boolean empty) {
            super.updateItem(Utilisateur, empty);

            if (empty || Utilisateur == null) {
                setText(null);
                setGraphic(null);
            } else {
                Label nameLabel = new Label(Utilisateur.getNom());
                nameLabel.setStyle("-fx-font-weight: bold;");
                ServiceUtilisateur sl= new ServiceUtilisateur();
                

                Label idLabel = new Label("Nom: " + sl.getOneById(Utilisateur.getId()).getNom());
                Label prenomlabel = new Label("Prenom: " + Utilisateur.getPrenom());
                Label emaillabel = new Label("Email: " + Utilisateur.getEmail());
                Label passwdlabel = new Label("Mot de passe: " + Utilisateur.getMot_de_passe());
                Label numtellabel = new Label("Numéro de téléphone: " + Utilisateur.getnum_telephone());
                Label rolelabel = new Label("Role: " + Utilisateur.getRole());

                VBox vbox = new VBox(nameLabel, idLabel,prenomlabel, emaillabel, passwdlabel, numtellabel ,rolelabel );
                vbox.setSpacing(5);

                setGraphic(vbox);
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
        public Utilisateur getOneByemailutilisateur(String email) {
        Utilisateur result = null;
        try {
            String req = "SELECT * FROM utilisateur WHERE email = "+email ;
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {

                result = new Utilisateur(rs.getInt(1), rs.getString("nom"), rs.getString(3), rs.getString(4), rs.getString("Mot_de_passe"), rs.getInt("num_telephone"),rs.getString("role")) {};

            }

        } catch (SQLException ex) {
            System.out.println("Les adresses emails ne doivent etre en doublons");
        }
        return result;
    }




    
}
