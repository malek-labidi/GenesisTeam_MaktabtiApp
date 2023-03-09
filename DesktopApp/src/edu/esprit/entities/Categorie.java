/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.entities;

/**
 *
 * @author Saleh
 */
public class Categorie {
    private int id_categorie;
    private String nom ;

    public Categorie() {
    }

    public Categorie(int id_categorie, String nom) {
        this.id_categorie = id_categorie;
        this.nom = nom;
    }

    public Categorie(String nom) {
        this.nom = nom;
    }

  
   

    public int getId_categorie() {
        return id_categorie;
    }

    public String getNom() {
        return nom;
    }

    

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public String toString() {
        return "Categorie{" + "nom=" + nom + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + this.id_categorie;
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
        final Categorie other = (Categorie) obj;
        if (this.id_categorie != other.id_categorie) {
            return false;
        }
        return true;
    }
    public static boolean verifString(String s ){
        
    return s.isEmpty();
    }
    
    
    
}
