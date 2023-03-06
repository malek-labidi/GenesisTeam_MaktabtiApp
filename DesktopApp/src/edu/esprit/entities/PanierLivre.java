/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.entities;

/**
 *
 * @author abdelazizlahmar
 */
public class PanierLivre {
    private  int id_livre;
    private  int id_panier ;
    private int id_client;
    private int quantite;

    public PanierLivre(int id_livre, int id_panier, int quantite) {
        this.id_livre = id_livre;
        this.id_panier = id_panier;
        this.quantite=quantite;
    }

    public int getQuantite() {
        return quantite;
    }

    @Override
    public String toString() {
        return "PanierLivre{" + "id_livre=" + id_livre + ", id_panier=" + id_panier + ", quantite=" + quantite + '}';
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
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

    public int getId_panier() {
        return id_panier;
    }

    public void setId_livre(int id_livre) {
        this.id_livre = id_livre;
    }

    public void setId_panier(int id_panier) {
        this.id_panier = id_panier;
    }
    
}
