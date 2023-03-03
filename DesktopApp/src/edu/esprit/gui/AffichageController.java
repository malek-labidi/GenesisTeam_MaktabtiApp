/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package edu.esprit.gui;

import edu.esprit.entities.Livre;
import edu.esprit.entities.Offre;
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
import javafx.scene.control.ListView;

/**
 * FXML Controller class
 *
 * @author Gaaloul
 */
public class AffichageController implements Initializable {

    @FXML
    private ListView<String> idlist;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      this.affiche();
    }    
public void affiche() {
    ServiceOffre c = new ServiceOffre();
    ServiceLivre sl = new ServiceLivre();
      
    List<String> nomsLivres = new ArrayList<>();
    List<Livre> livres = sl.getAll();
    for (Livre livre : livres) {
        Offre offre = c.getOneByIdlivre(livre.getId_livre());
        String nomLivre = livre.getTitre();
        if (offre != null) {
            String pourcentageSolde = offre.getPourcentage_solde();
            String prixSolde = String.valueOf(offre.getPrix_soldé());
            nomLivre += " (" + pourcentageSolde + " - " + prixSolde + "dt)";
        }
        nomsLivres.add(nomLivre);
    }

    idlist.setItems(FXCollections.observableArrayList(nomsLivres));
}

    @FXML
    private void delete(ActionEvent event) {
           int selectedIndex = idlist.getSelectionModel().getSelectedIndex();
    String selectedValue = idlist.getSelectionModel().getSelectedItem();
    String[] tokens = selectedValue.split("\\("); // Séparer le nom du livre et les détails de l'offre
    String titreLivre = tokens[0].trim(); // Extraire le nom du livre
    ServiceLivre sl = new ServiceLivre();
        Livre livre = sl.getOneByTitre(titreLivre); // Récupérer l'objet Livre correspondant
    if (livre != null) {
        ServiceOffre c = new ServiceOffre();
        Offre offre = c.getOneByIdlivre(livre.getId_livre()); // Récupérer l'offre correspondante
        if (offre != null) {
            c.delete(offre.getId_offre()); // Supprimer l'offre
            this.affiche();
        }
    }
}

    }
    
