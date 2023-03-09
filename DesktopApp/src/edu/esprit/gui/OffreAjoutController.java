/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package edu.esprit.gui;

import edu.esprit.api.SendOffreByMail;
import edu.esprit.api.SendSMS;
import edu.esprit.entities.Livre;
import edu.esprit.entities.Offre;
import static edu.esprit.entities.Offre.verif_pourcentage_solde;
import edu.esprit.services.ServiceLivre;
import edu.esprit.services.ServiceOffre;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Gaaloul
 */
public class OffreAjoutController implements Initializable {

    @FXML
    private TextField a;
    @FXML
    private TextField b;
    @FXML
    private ListView<String> list_livre;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        ServiceLivre sl = new ServiceLivre();
        List<Livre> livres = sl.getAll();
        List<String> names = new ArrayList<>();

        livres.forEach((l) -> {
            names.add(l.getTitre());
        });
        list_livre.setItems(FXCollections.observableArrayList(names));
    }

    @FXML
    private void ajouteroffre(ActionEvent event) {
        int id = -1;
        float prix = 0;
        ServiceLivre sl = new ServiceLivre();
        List<Livre> livres = sl.getAll();

        for (Livre l : livres) {
            if (l.getTitre().equals(list_livre.getSelectionModel().getSelectedItem())) {
                id = l.getId_livre();
                prix = l.getPrix();
                break;
            }
        }
        System.out.println(prix);
        String ppp = a.getText();
        ServiceOffre so = new ServiceOffre();
        if (verif_pourcentage_solde(ppp) == false) {

            Alert alert = new Alert(Alert.AlertType.ERROR, "veuillez saisir une pourcentage sous forme de nn% ", ButtonType.CLOSE);
            alert.show();
        } else if (so.getOneByIdlivre(id) != null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "ce livre est déja en solde .veuiller modifier l'offre", ButtonType.CLOSE);
            alert.show();

        } else {

            float prixsolde = so.calculprixsoldé(prix, ppp);
            //System.out.println(prixsolde);
            String priiiix = String.valueOf(prixsolde);
            //System.out.println(priiiix);

            //Integer.parseInt(b.getText())
            //int tt=Integer.parseInt(priiiix);
            //System.out.println(tt);
            Offre o = new Offre(id, ppp, prixsolde);

            // String titre=list_livre.getSelectionModel().getSelectedItem();
            //  System.out.println(titre);
            so.ajouter(o);

              SendSMS sm = new SendSMS();
            sm.sendSMS(o);
            b.setText(priiiix);

            a.clear();
        }
    }

    @FXML
    private void modifierOffre(ActionEvent event) {
        int id = -1;
        float prix = 0;
        ServiceLivre sl = new ServiceLivre();
        List<Livre> livres = sl.getAll();

        for (Livre l : livres) {
            if (l.getTitre().equals(list_livre.getSelectionModel().getSelectedItem())) {
                id = l.getId_livre();
                prix = l.getPrix();
                break;
            }
        }
        String ppp = a.getText();
        ServiceOffre so = new ServiceOffre();
        float prixsolde = so.calculprixsoldé(prix, ppp);
        System.out.println(prixsolde);
        String priiiix = String.valueOf(prixsolde);
        System.out.println(priiiix);
        if (so.getOneByIdlivre(id) == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Ce livre n'a pas encore d'offre.Vous devez ajouter une et vous ne pouvez pas modifier ", ButtonType.CLOSE);
            alert.show();

        } else {
            Offre oooo = new Offre();
            oooo = so.getOneByIdlivre(id);
            Offre o = new Offre(oooo.getId_offre(), id, ppp, prixsolde);
            so.modifier(o);

        }

    }

    @FXML
    private void Envoyermail(ActionEvent event) {
        SendOffreByMail.Envoyer();
        

    }

}

/* @FXML
  private void calcul_prix_soldé(KeyEvent event) {
        
             ServiceOffre O = new ServiceOffre();
             int id1;
        float prix=0;
        ServiceLivre sl = new ServiceLivre();
                List<Livre> livres = sl.getAll();
                
                for (Livre l : livres) {
                    if (l.getTitre().equals(list_livre.getSelectionModel().getSelectedItem())) {
                        id1 = l.getId_livre();
                        prix=l.getPrix();
                        break;
                    }
                }
                String ps=a.getText();
                float ooo=O.calculprixsoldé(prix,ps);
                b.setText(String.valueOf(ooo));
    }
 */
