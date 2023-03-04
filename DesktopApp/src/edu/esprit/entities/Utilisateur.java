/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.esprit.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author wassi
 */
public abstract class Utilisateur {
    private int id;
    private String nom,prenom,email,mot_de_passe;
    private int num_telephone;
    private String role;
    private List<Utilisateur> listeutilisateurs;

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

    public Utilisateur(int num_telephone) {
        this.num_telephone = num_telephone;
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
    
    
    //Controle de saisie sur les cases vides
        public static boolean verifString(String s ){
        return s.isEmpty();
    }
    //Controle de saisie sur l'email
    public static boolean verifemail(String s ){
        String regex="[^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$]+";
        return s.matches(regex);
    }
    //Controle de saisie sur le mot de passe
    public static boolean verifpassword(String s ){
        String regex="[a-zA-Z_0-9]+";
        return s.matches(regex);
    }
    
    

        public List<Utilisateur> getUtilisateurs() {

        return listeutilisateurs;

    }

    public void supprimerQuestion(Question p) {
        listeutilisateurs.remove(p);
    }

    public Stream<Utilisateur> trierUtilisateurParId() {

        return listeutilisateurs.stream().sorted((t1, t2) -> t1.getId()- t2.getId());
    }
    

    
    
    public void afficherUtilisateurs() {
        listeutilisateurs.stream().forEach(e -> System.out.println(e));

    }
    
    public void afficherClients() {
    List<Utilisateur> clients = listeutilisateurs.stream()
            .filter(v -> !v.getRole().equals("Client")).collect(Collectors.toList());
    }
    public void afficherAdministrateurs() {
    List<Utilisateur> Administrateurs = listeutilisateurs.stream()
            .filter(v -> !v.getRole().equals("Administrateur")).collect(Collectors.toList());
    }
    public void afficherAuteurs() {
    List<Utilisateur> Auteurs = listeutilisateurs.stream()
            .filter(v -> !v.getRole().equals("Auteur")).collect(Collectors.toList());
    }
    
    
}