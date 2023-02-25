/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.main;

import edu.esprit.entities.Commande;
import edu.esprit.entities.Competition;
import edu.esprit.entities.Etat;
import edu.esprit.entities.Evenement;
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
import edu.esprit.services.ServiceEvenement;

import edu.esprit.services.ServiceLivre;

import edu.esprit.services.ServiceFidelite;
import edu.esprit.services.ServiceOffre;

import edu.esprit.services.ServicePanier;
import edu.esprit.services.ServiceReclamation;
import edu.esprit.services.ServiceUtilisateur;
import java.sql.Date;
import java.sql.Time;
import javafx.scene.chart.PieChart;

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
        //ServicePanier sp=new ServicePanier();
        //Panier p1=new Panier(1, 3, 25,250);
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
        // Commande c4= new Commande (2,1,Status.paye,Mode.carte_bancaire,Etat.encours);

      ServiceUtilisateur su=new ServiceUtilisateur();
       // Utilisateur u1 = new Utilisateur("wassim", "hachani", "wassimhach16@gmail.com", "wassim", 54100060,"Administrateur") {};
        //System.out.println(su.getAll());
       Utilisateur u4 = new Utilisateur(7,"gg", "gg", "maleklaabidi@hotmail.fr", "wassim1", 54100060,"Administrateur") {};
       Utilisateur u9 = new Utilisateur("gg", "gg", "mwassimhachanikd@hotmail.fr", "wassim1", 54100060,"Auteur") {};

       su.modifier2(32,u9);
            //ServiceCommande cu = new ServiceCommande();
       // Commande c4= new Commande (2,1,Status.paye,Mode.carte_bancaire,Etat.encours);




         //ServiceFidelite sf = new ServiceFidelite();
                //System.out.println(sf.getAll());
                // System.out.println(sf.getOneById(2));
                /* sf.delete(1);*/

                 //ServiceOffre o=new ServiceOffre();

       // Offre oo = new Offre(2, "2%", 560);
        //Offre oo1 = new Offre(90,3,"80%", 440);
        // o.ajouter(oo1);



        //se.modifier(e1);
           Reclamation r5 = new Reclamation("FSGFSF", "SDFDSF");
        Reclamation r6 = new Reclamation(2,"aaaaa", "aaaaa");
        ServiceReclamation sr = new ServiceReclamation();
        sr.ajouter(r6);
        System.out.println(sr.getAll());
        System.out.println(sr.getOneById(2));
    }
}

