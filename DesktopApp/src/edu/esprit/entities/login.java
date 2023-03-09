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
public class login {

    // Create a private static instance of Login class
    private static login instance;

    // Declare instance variables for username and password
    private String username;
    private String password;
    private String role;
    private String nom;
    private int id;
    private int numtel;
    private String prenom;
    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumtel() {
        return numtel;
    }

    public void setNumtel(int numtel) {
        this.numtel = numtel;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }



    // Create a private constructor to prevent external instantiation
    private login() {
    }

    // Create a public static method to get the single instance of Login class
    public static login getInstance() {
        if (instance == null) {
            instance = new login();
        }
        return instance;
    }

    // Setters and getters for username and password
    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return this.password;
    }

    // Method to validate login credentials
    public boolean isValidLogin() {
        // Add your login validation code here
        return false;
    }
}