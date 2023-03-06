/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.main;

import edu.esprit.entities.Commande;
import edu.esprit.entities.Competition;
import edu.esprit.entities.Etat;
import edu.esprit.entities.Livre;
import edu.esprit.entities.Mode;
import edu.esprit.entities.Offre;
import edu.esprit.entities.Panier;
import edu.esprit.entities.Reclamation;
import edu.esprit.entities.Role;
import edu.esprit.entities.Status;
import edu.esprit.entities.Utilisateur;
import edu.esprit.services.ServiceCommande;
import edu.esprit.services.ServiceCompetition;

import edu.esprit.services.ServiceLivre;




import edu.esprit.services.ServiceFidelite;
import edu.esprit.services.ServiceLivrePanier;
import edu.esprit.services.ServiceOffre;

import edu.esprit.services.ServicePanier;
import edu.esprit.services.ServiceReclamation;
import edu.esprit.services.ServiceUtilisateur;
import java.sql.Date;
import java.time.LocalDate;

/**
 *
 * @author admin
 */
public class Main {
    public static void main(String[] args) {
        
        //ServiceCompetition sc = new ServiceCompetition();
        //Competition c =new Competition(1, "cadeau3", "p", "gsdhdghdghdg", "a9rani2", Date.valueOf("2023-04-14"), Date.valueOf("2023-04-23"));
        //Competition c1 =new Competition(1,1, "cadeau1", "p1", "gsdhdghdghdg", "a9rani", Date.valueOf("2023-03-25"), Date.valueOf("2023-04-01"));
        
        
        //sc.ajouter(c);
        //sc.modifier(c1);
        //sc.delete(3);
        //System.out.println(sc.getOneById(1));
        //System.out.println(sc.getAll());
        
        
       // ServiceLivrePanier pn = new ServiceLivrePanier();
        //ServicePanier sp=new ServicePanier();
       // Panier p1=new Panier(2, 3, 25,250);
       // sp.ajouterLivreAuPanier(3, 3, 2);
       //pn.ajouterProduitAuPanier(3,3,1);
       //pn.ajouterProduitAuPanier(3,3,1);
         //pn.ajouterProduitAuPanier(3, 1, 1);
         //pn.ajouterLivrePanier(2, 3);
         //pn.ajouterLivrePanier(3, 3);
         //pn.supprimerLivrePanier(2);
        //pn.getAll();
        //System.out.println(pn.afficherLivresDansPanier(3));
       // pn.delete(3);
        
       
       //pn.ajouterProduitAuPanier(3,2);
        //Panier p2=new Panier(1, 1, 2, 50);
        
        
        
        //sp.ajouter(p1);
        //sp.modifier(p2);
        //sp.delete(2);
        
        //System.out.println(sp.getOneById(2));
        
        //User
       // ServiceUtilisateur su=new ServiceUtilisateur();
       // Utilisateur u1 = new Utilisateur("wassim", "hachani", "wassimhach16@gmail.com", "wassim", 54100060,"Administrateur") {};
        //System.out.println(su.getAll());
       // Utilisateur u3 = new Utilisateur("gg", "gg", "wassimhach16@gmaill.com", "wassim1", 54100060,"Administrateur") {};
       // su.ajouter(u3);
        
       
       
        //ServiceCommande cu = new ServiceCommande();
        //Commande c4= new Commande (2,1,Status.paye,Mode.carte_bancaire,Etat.livre,400);
        //Commande c5= new Commande (1,3,1,Status.non_paye,Mode.carte_bancaire,Etat.annuler,200);
        
        
        
        
        //cu.ajouter(c5);
        //cu.ajouter(c4);
        //cu.getOneById(1);
          //c4.toString();
          //cu.delete(2);
        //cu.modifier(c5);
        //System.out.println(cu.getAll());
        //System.out.println(cu.getOneById(1));
        //Livre l=new Livre(1, 1, "solo", Date.valueOf("1998-10-02"), "fr", 123, 222, "sssss", 20);
        //Livre l1=new Livre(1,1, 1, "virus", Date.valueOf("2005-10-02"), "fr", 123, 222, "sssss", 20);

        //ServiceLivre sl=new ServiceLivre();
        //sl.ajouter(l);
        //sl.modifier(l);
        //System.out.println(sl.getAll());
        //System.out.println(sl.getOneById(1));

         //ServiceFidelite sf = new ServiceFidelite();
                //System.out.println(sf.getAll());
                // System.out.println(sf.getOneById(2));
                /* sf.delete(1);*/
                // ServiceOffre o=new ServiceOffre();

        //Offre oo = new Offre(2, "2%", 560);
        //Offre oo1 = new Offre(90,3,"80%", 440);
        // o.ajouter(oo1);

    }
}

