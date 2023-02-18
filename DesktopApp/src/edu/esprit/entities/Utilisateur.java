/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.esprit.entities;

/**
 *
 * @author wassi
 */
public abstract class Utilisateur {
    private int id;
    private String nom,prenom,email,mot_de_passe;
    private int num_telephone;
    private String role;

    public Utilisateur() {
    }

    public Utilisateur(String nom, String prenom, String email, String mot_de_passe, int num_telephone, String role) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.mot_de_passe = mot_de_passe;
        this.num_telephone = num_telephone;
        this.role = role;
    }

    public Utilisateur(int id, String nom, String prenom, String email, String mot_de_passe, int num_telephone, String role) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.mot_de_passe = mot_de_passe;
        this.num_telephone = num_telephone;
        this.role = role;
    }

  

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }


    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMot_de_passe() {
        return mot_de_passe;
    }

    public void setMot_de_passe(String mot_de_passe) {
        this.mot_de_passe = mot_de_passe;
    }

    public int getnum_telephone() {
        return num_telephone;
    }

    public void setnum_telephone(int num_telephone) {
        this.num_telephone = num_telephone;
    }

    @Override
    public String toString() {
        return "Utilisateur{" + "nom=" + nom + ", prenom=" + prenom + ", email=" + email + ", mot_de_passe=" + mot_de_passe + ", num_telephone=" + num_telephone + ", role=" + role + '}';
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


    
    

    
}