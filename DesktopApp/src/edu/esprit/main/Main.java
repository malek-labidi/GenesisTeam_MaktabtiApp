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
import edu.esprit.entities.Panier;
import edu.esprit.entities.Reclamation;
import edu.esprit.entities.Role;
import edu.esprit.entities.Status;
import edu.esprit.entities.Utilisateur;
import edu.esprit.services.ServiceCommande;
import edu.esprit.services.ServiceCompetition;

import edu.esprit.services.ServiceLivre;




import edu.esprit.services.ServiceFidelite;

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
        
        ServiceCompetition sc = new ServiceCompetition();
        Competition c =new Competition(1, "cadeau3", "p", "gsdhdghdghdg", "a9rani2", Date.valueOf("2023-04-14"), Date.valueOf("2023-04-23"));
        Competition c1 =new Competition(1,1, "cadeau1", "p1", "gsdhdghdghdg", "a9rani", Date.valueOf("2023-03-25"), Date.valueOf("2023-04-01"));
        
        
        //sc.ajouter(c);
        //sc.modifier(c1);
        //sc.delete(3);
        System.out.println(sc.getOneById(1));
        System.out.println(sc.getAll());
        
        
        ServicePanier sp=new ServicePanier();
        Panier p1=new Panier(1, 3, 25);
        Panier p2=new Panier(1, 1, 2, 50);

        //sp.ajouter(p1);
        //sp.modifier(p2);
        //sp.delete(2);
        
        System.out.println(sp.getAll());
        System.out.println(sp.getOneById(2));
        
        //User
        ServiceUtilisateur su=new ServiceUtilisateur();
       // Utilisateur u2 = new Utilisateur("malek", "laabidi", "maleklaabidi@gmail.com", "laabidi", 63647551,Role.Client);
        //Utilisateur u1 = new Utilisateur("wassim", "hachani", "wassimhach16@gmail.com", "wassim", 54100060,Role.Administrateur);
        //su.ajouter(u1);
        //su.ajouter(u2);
        
        System.out.println(su.getAll()); 
        System.out.println(su.getOneById(2));
        
        ServiceCommande cu = new ServiceCommande();
       // Commande c4= new Commande (2,1,Status.paye,Mode.carte_bancaire,Etat.encours);
        //Commande c5= new Commande (2,1,Status.non_paye,Mode.carte_bancaire,Etat.encours);
        
        //cu.ajouter(c5);
        //cu.ajouter(c4);
        
        System.out.println(cu.getAll());
        System.out.println(cu.getOneById(2));
        Livre l=new Livre(1, 1, "solo", Date.valueOf("1998-10-02"), "fr", 123, 222, "sssss", 20);
                Livre l1=new Livre(1,1, 1, "virus", Date.valueOf("2005-10-02"), "fr", 123, 222, "sssss", 20);

        ServiceLivre sl=new ServiceLivre();
        //sl.ajouter(l);
        sl.modifier(l);
        System.out.println(sl.getAll());
        System.out.println(sl.getOneById(1));

         ServiceFidelite sf = new ServiceFidelite();
                System.out.println(sf.getAll());
                 System.out.println(sf.getOneById(2));
                /* sf.delete(1);*/
                
                
        Reclamation r5 = new Reclamation("FSGFSF", "SDFDSF");
        Reclamation r6 = new Reclamation(2,"FSGFSF", "SDFDSF");
        ServiceReclamation sr = new ServiceReclamation();
        sr.modifier(r6);
        System.out.println(sr.getAll());
        System.out.println(sr.getOneById(2));
         
        
    }
}
