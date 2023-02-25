/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.entities;

/**
 *
 * @author wassi
 */
public class Client extends Utilisateur {

    public Client() {
    }

    public Client(String nom, String prenom, String email, String mot_de_passe, int num_telephone, String role) {
        super(nom, prenom, email, mot_de_passe, num_telephone, role);
    }

    public Client(int id, String nom, String prenom, String email, String mot_de_passe, int num_telephone, String role) {
        super(id, nom, prenom, email, mot_de_passe, num_telephone, role);
    }

    @Override
    public String toString() {
        return "Client{" + '}';
    }

}