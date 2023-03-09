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
public class UtilisateurController implements Initializable {
        Connection cnx = DataSource.getInstance().getCnx();

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
    @FXML
    private Button excel;
    @FXML
    private MenuButton menurole;
    @FXML
    private MenuItem Admin;
    @FXML
    private MenuItem Client;
    @FXML
    private MenuItem Auteur;

    ServiceUtilisateur su = new ServiceUtilisateur();
    private Label user;
    private Label prenom;

    /**
     * Initializes the controller class.
     */
  
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        useview.setOnMouseClicked(this::getUtilisateur);
        ServiceUtilisateur se=new ServiceUtilisateur();
        this.e1=se.getAll();
            affiche(su.getAll());
           

        
    }    

    private void handleClicks(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLMaktabti.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
        
    }


    //ajouter un utilisateur
    @FXML
     private void adduser(ActionEvent event) {
         if (cat_name.getText().isEmpty() || cat_prenom.getText().isEmpty() || cat_email.getText().isEmpty() || cat_tel.getText().isEmpty() || cat_password.getText().isEmpty() || menurole.getText().isEmpty()  ) {
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
            Utilisateur u = new Utilisateur(cat_name.getText(),cat_prenom.getText(),cat_email.getText(),cat_password.getText(),Integer.parseInt(cat_tel.getText()), menurole.getText() ) {} ;
            su.ajouter(u);
            affiche(su.getAll());
            cat_name.clear();
            cat_prenom.clear();
            cat_email.clear();
            cat_password.clear();
            cat_tel.clear();
        menurole.setText(null);

            Alert a = new Alert(Alert.AlertType.INFORMATION, "User added !", ButtonType.OK);
            a.showAndWait();
        }
        
    } 
     public void affiche(List<Utilisateur> l) {
        
        useview.setItems(FXCollections.observableArrayList(l));
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

     //Modifier un utilisateur
    @FXML
    private void updateuser(ActionEvent event) {
        
        int id = useview.getSelectionModel().getSelectedItem().getId();
        System.out.println(id);
        Dialog<ButtonType> dialog = new Dialog<>();
        
        
            if (cat_name.getText().isEmpty() || cat_prenom.getText().isEmpty() || cat_email.getText().isEmpty() || cat_tel.getText().isEmpty() || cat_password.getText().isEmpty() || menurole.getText().isEmpty()  ) {
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
            Utilisateur u = new Utilisateur(id,cat_name.getText(),cat_prenom.getText(),cat_email.getText(),cat_password.getText(),Integer.parseInt(cat_tel.getText()), menurole.getText() ) {} ;
            su.modifier3(u);
            System.out.println(u);
            affiche(su.getAll());
            cat_name.clear();
            cat_prenom.clear();
            cat_email.clear();
            cat_password.clear();
            cat_tel.clear();
        menurole.setText(null);

            
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
    
    //Fonction d'affichage d'un utilisateur
        public void getUtilisateur(MouseEvent event) {
        int index = useview.getSelectionModel().getSelectedIndex();
        Utilisateur u= new Utilisateur() {};


        ServiceUtilisateur su = new ServiceUtilisateur();
        u= useview.getSelectionModel().getSelectedItem();
        System.out.println(u);
        cat_name.setText(u.getNom());
        cat_prenom.setText(u.getPrenom());
        cat_email.setText(u.getEmail());
        cat_password.setText(u.getMot_de_passe());
        cat_tel.setText(Integer.toString(u.getnum_telephone()));
        menurole.setText(u.getRole());
    }

        //Supprimer un utilisateur
    @FXML
    private void deleteuser(ActionEvent event) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setHeaderText("Voulez-vous vraiment supprimer l'utilisateur N°" + id + "?");
        ButtonType buttonTypeYes = new ButtonType("Oui", ButtonBar.ButtonData.YES);
        ButtonType buttonTypeNo = new ButtonType("Non", ButtonBar.ButtonData.NO);
        dialog.getDialogPane().getButtonTypes().addAll(buttonTypeYes, buttonTypeNo);
        Optional<ButtonType> result = dialog.showAndWait();
         if (result.isPresent() && result.get() == buttonTypeYes) {
            ServiceUtilisateur su = new ServiceUtilisateur();
            Utilisateur u= new Utilisateur() {} ;   
            u= useview.getSelectionModel().getSelectedItem();
            su.supprimerUtilisateur(u);
            cat_name.clear();
            cat_prenom.clear();
            cat_email.clear();
            cat_password.clear();
            cat_tel.clear();
            menurole.setText(null);
            Alert a = new Alert(Alert.AlertType.CONFIRMATION, "User Deleted Successfully !", ButtonType.OK);
            a.showAndWait();
    }
            affiche(su.getAll());

    }


    //Filtres
        public void filter() {
        
        String s = searchh.getCharacters().toString();
        ServiceUtilisateur su = new ServiceUtilisateur();
        this.e1 = su.getAll();
        
        if (!s.isEmpty()) {
        this.e1 = ServiceUtilisateur.filterByName(this.e1, s);

        }
    }

        //Fonction de recherche
    @FXML
    private void search(KeyEvent event) {
        
        filter();
            affiche(this.e1);
    }

    @FXML
    private void onreset(ActionEvent event) {
        cat_name.setText(null);
        cat_prenom.setText(null);
        cat_email.setText(null);
        cat_password.setText(null);
        cat_tel.setText(null);
        menurole.setText(null);
    }

    @FXML
    private void excelfile(ActionEvent event) {
        ArrayList<Utilisateur> data = new ArrayList<Utilisateur>();
     
       
       try{  



    //creating an instance of HSSFWorkbook class  
//declare file name to be create   
    String filename = "src\\edu\\esprit\\excel\\DonnéeUtilisateurs.XLS";  
//creating an instance of HSSFWorkbook class  
    HSSFWorkbook workbook = new HSSFWorkbook();  
//invoking creatSheet() method and passing the name of the sheet to be created   
    HSSFSheet sheet = workbook.createSheet("User Details");   
//creating the 0th row using the createRow() method  
    HSSFRow rowhead = sheet.createRow((short)0);  
//creating cell by using the createCell() method and setting the values to the cell by using the setCellValue() method  
    rowhead.createCell(0).setCellValue("Nom");  
    rowhead.createCell(1).setCellValue("Prenom");  
    rowhead.createCell(2).setCellValue("Email");  
    rowhead.createCell(3).setCellValue("Mot de passe");  
    rowhead.createCell(4).setCellValue("Numéro de téléphone");  

    ObservableList<Utilisateur> userlist = FXCollections.observableArrayList(useview.getItems());
             
                int rownum = 1;
                for (Utilisateur USER : userlist) {
                HSSFRow row = sheet.createRow(rownum++);  
                HSSFRow headerRow = sheet.createRow(0);
                row.createCell(0).setCellValue(USER.getNom());
                row.createCell(1).setCellValue(USER.getPrenom());
                row.createCell(2).setCellValue(USER.getEmail());
                row.createCell(3).setCellValue(USER.getMot_de_passe());
                row.createCell(4).setCellValue(USER.getnum_telephone());
                }
            
                FileOutputStream fileOut = new FileOutputStream(filename);  
                workbook.write(fileOut);  
            
                //closing the Stream  
                fileOut.close();  
                //closing the workbook  
                workbook.close();  
                //prints the message on the console  
                Alert a = new Alert(Alert.AlertType.INFORMATION, "Excel File Has Been Generated Successfully", ButtonType.OK);
                a.showAndWait();
                }   
                catch (Exception e)   
                {  
                e.printStackTrace();  
                }                                 
        
    }

    @FXML
    private void Adminrole(ActionEvent event) {
        menurole.setText(Admin.getText());

    }

    @FXML
    private void clientrole(ActionEvent event) {
        menurole.setText(Client.getText());

    }

    @FXML
    private void Auteurrole(ActionEvent event) {
        menurole.setText(Auteur.getText());

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
       /* public Utilisateur getOneByemailutilisateur(String email) {
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
    } */




    
}
