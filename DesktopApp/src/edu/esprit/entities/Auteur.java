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
public class Auteur extends Utilisateur {
    private int num_auteur;

    public Auteur(int num_auteur) {
        this.num_auteur = num_auteur;
    }

    public Auteur(int num_auteur, String nom, String prenom, String email, String mot_de_passe, int num_telephone, String role) {
        super(nom, prenom, email, mot_de_passe, num_telephone, role);
        this.num_auteur = num_auteur;
    }

    public Auteur(int num_auteur, int id, String nom, String prenom, String email, String mot_de_passe, int num_telephone, String role) {
        super(id, nom, prenom, email, mot_de_passe, num_telephone, role);
        this.num_auteur = num_auteur;
    }

    public Auteur() {
    }

    public int getNum_auteur() {
        return num_auteur;
    }

    public void setNum_auteur(int num_auteur) {
        this.num_auteur = num_auteur;
    }

    @Override
    public String toString() {
        return "Auteur{" + "num_auteur=" + num_auteur + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
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
        final Auteur other = (Auteur) obj;
        if (this.num_auteur != other.num_auteur) {
            return false;
        }
        return true;
    }

}