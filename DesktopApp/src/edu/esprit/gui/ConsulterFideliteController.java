/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package edu.esprit.gui;

import edu.esprit.entities.Commande;
import edu.esprit.entities.Fidelite;
import edu.esprit.entities.Type;
import static edu.esprit.entities.Type.bronze;
import static edu.esprit.entities.Type.gold;
import static edu.esprit.entities.Type.silver;
import edu.esprit.services.ServiceCommande;
import edu.esprit.services.ServiceFidelite;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Gaaloul
 */
public class ConsulterFideliteController implements Initializable {

    @FXML
    private TextField a;
    @FXML
    private TextField b;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
            this.verif();
        this.affiche();
    }    
    
    public void verif(){
           ServiceFidelite sf=new ServiceFidelite();

        ServiceCommande sc=new ServiceCommande();
        List <Commande> lc=sc.getAll();
        for (Commande ll:lc){
            if (sf.getOneByIdClient(ll.getId_client())==null){
                System.out.println(sf.totalacha(ll.getId_client()));
                if (sf.totalacha(ll.getId_client())<1000 && sf.totalacha(ll.getId_client()) >0 ){
                        Fidelite t =new Fidelite(ll.getId_client(), sf.totalacha(ll.getId_client()),bronze);
                        System.out.println(t);
                        sf.ajouter(t);               
                }else if (sf.totalacha(ll.getId_client())<2000 && sf.totalacha(ll.getId_client()) >=1000 ){
                    Fidelite t =new Fidelite(ll.getId_client(), sf.totalacha(ll.getId_client()),silver);
                        sf.ajouter(t); 
                }else if (sf.totalacha(ll.getId_client())>=2000){
                    Fidelite t =new Fidelite(ll.getId_client(), sf.totalacha(ll.getId_client()),gold);
                        sf.ajouter(t); 
                }
                
            }else {
                int total =sf.totalacha(ll.getId_client());
                Fidelite ff = sf.getOneByIdClient(ll.getId_client());
                int id=ff.getId_fidelite();
                //System.out.println(total);
                 //Fidelite t1 =new Fidelite(ll.getId_client(), total,bronze);
                   //    sf.modifier(t1);
                if (sf.totalacha(ll.getId_client())<1000 && sf.totalacha(ll.getId_client()) >0 ){
                 Fidelite t =new Fidelite(id,ll.getId_client(), total,bronze);
                       sf.modifier(t);

                }else if (sf.totalacha(ll.getId_client())<2000 && sf.totalacha(ll.getId_client()) >=1000 ){
                    Fidelite t =new Fidelite(id,ll.getId_client(), total,silver);
                       sf.modifier(t);

                }else if (sf.totalacha(ll.getId_client())>=2000){
                     Fidelite t =new Fidelite(id,ll.getId_client(), total,gold);
                        sf.modifier(t); 

                }
                
            }
            
        }
    }

    public void affiche(){
             ServiceFidelite sf=new ServiceFidelite();

        
       /* if(sf.getOneByIdClient(2)==null){
               
            Alert alert = new Alert(Alert.AlertType.ERROR, "Vous n'avez effectué aucun achat.vous n'avez pas de fidelité ", ButtonType.CLOSE);
                alert.show();
        }*/
       
        Fidelite f =sf.getOneByIdClient(8);
        int totalacha=f.getTotal_achat();
         if (totalacha <1000 && totalacha > 0){
            a.setText("bronze");
        }else if (totalacha < 2000 && totalacha >= 1000){
            a.setText("silver");     
        }else if (totalacha > 2000){
            a.setText("gold");     
        }
        int total=sf.totalacha(8);
        if(a.getText().equals("bronze")){
            b.setText("Pour passer au fidélité de type silver vous devez effectuer des achats de total >=1000 /n sachant que votre total achat=" + total);
        }else if(a.getText().equals("silver")){
            b.setText("Pour passer au fidélité de type gold vous devez effectuer des achats de total >=2000 /n sachant que votre total achat=" + total);

        }
    }
}
