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
public class Paiement {
    
    private int id_paiement;
    private int id_client;
    private int montant;
    
    //Atrributs de type énuméres
    private Mode mode ;

    public Paiement(int id_client, int montant, Mode mode) {
        this.id_client = id_client;
        this.montant = montant;
        this.mode = mode;
    }

    public Paiement(int id_paiement, int id_client, int montant, Mode mode) {
        this.id_paiement = id_paiement;
        this.id_client = id_client;
        this.montant = montant;
        this.mode = mode;
    }

    public int getId_paiement() {
        return id_paiement;
    }

    public void setId_paiement(int id_paiement) {
        this.id_paiement = id_paiement;
    }

    public int getId_client() {
        return id_client;
    }

    public void setId_client(int id_client) {
        this.id_client = id_client;
    }

    public int getMontant() {
        return montant;
    }

    public void setMontant(int montant) {
        this.montant = montant;
    }

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 19 * hash + this.id_paiement;
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
        final Paiement other = (Paiement) obj;
        if (this.id_paiement != other.id_paiement) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Paiement{" + "id_client=" + id_client + ", montant=" + montant + ", mode=" + mode + '}';
    }
    
    
    
}
