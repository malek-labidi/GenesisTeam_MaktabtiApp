/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.entities;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author abdelazizlahmar
 */
public class Panier {
     private int id_panier;
     private int id_livre;
     private int id_client ;
     private int quantite;
     private float totalPrix;
     
     //attributs de type énumérés 
    private Mode mode ;   

    private List<PanierLivre> panierLivres;    
     private Iterable<Livre> livres;
     public Panier(int id_panier, int id_client) {
        this.id_panier = id_panier;
        this.panierLivres = new ArrayList<PanierLivre>();
        this.id_client = id_client;
     }
     
     
    public Panier() {
    }

    public Panier(int id_livre,int id_client, int quantite, float totalPrix) {
        this.id_livre = id_livre;
        this.id_client=id_client;
        this.quantite = quantite;
        this.totalPrix = 0;
        
        
    }

    public Panier(int id_panier, int id_livre,int id_client, int quantite, float totalPrix) {
        this.id_panier = id_panier;
        this.id_livre = id_livre;
        this.id_client=id_client;
        this.quantite = quantite;
        this.totalPrix = 0;        
    }


    public int getId_panier() {
        return id_panier;
    }

    public int getId_client() {
        return id_client;
    }

    public void setId_client(int id_client) {
        this.id_client = id_client;
    }

   

    public int getId_livre() {
        return id_livre;
    }

    public void setId_livre(int id_livre) {
        this.id_livre = id_livre;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public float getTotalPrix() {
        return totalPrix;
    }

    public void setTotalPrix(float totalPrix) {
        this.totalPrix = totalPrix;
    }

    @Override
    public String toString() {
        return "Panier{" + " quantite=" + quantite + ", totalPrix=" + totalPrix + '}';
    }

    

   
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + this.id_panier;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Panier other = (Panier) obj;
        if (this.id_panier != other.id_panier) {
            return false;
        }
        return true;
    }
     
     private Livre trouverLivreDansPanier(PanierLivre panierLivre) {
        for (Livre livre : livres ) {
            if (livre.getId_livre() == panierLivre.getId_livre()) {
                return livre;
            }
        }
        return null;
    }
}
     
    



