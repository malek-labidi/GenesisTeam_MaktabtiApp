/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.entities;




/**
 *
 * @author Gaaloul
 */
public class Fidelite {
    private int id_fidelite;
    private int id_client;
    private int total_achat;
    private Type type;
    public Fidelite(){
        
    }

    public Fidelite(int id_fidelite, int id_client, int total_achat,Type type) {
        this.id_fidelite = id_fidelite;
        this.id_client = id_client;
        this.total_achat = total_achat;
        this.type=type;
    }

    public Fidelite(int id_client, int total_achat,Type type) {
        this.id_client = id_client;
        this.total_achat = total_achat;
        this.type=type;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public int getId_fidelite() {
        return id_fidelite;
    }

    public void setId_fidelite(int id_fidelite) {
        this.id_fidelite = id_fidelite;
    }

    public int getTotal_achat() {
        return total_achat;
    }

    public void setTotal_achat(int total_achat) {
        this.total_achat = total_achat;
    }

    @Override
    public String toString() {
        return "Fidelite{" + "id_client=" + id_client + ", total_achat=" + total_achat + ", type=" + type + '}';
    }

  }
