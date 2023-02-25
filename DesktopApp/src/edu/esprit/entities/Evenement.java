/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.entities;

/**
 *
 * @author SADOK
 */
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;

public class Evenement {
    
    private int id_evenement;
    private String nom;
    private Date date;
    private Time heure;
    private int id_auteur;
    private int id_livre;
    private String lieu;
    private String description;
    private int nb_ticket;

    // Constructor
    public Evenement() {
}

    public Evenement(String nom, Date date, Time heure, int id_auteur, int id_livre, String lieu, String description, int nb_ticket) {
        this.nom = nom;
        this.date = date;
        this.heure = heure;
        this.id_auteur = id_auteur;
        this.id_livre = id_livre;
        this.lieu = lieu;
        this.description = description;
        this.nb_ticket = nb_ticket;
    }

    public Evenement(int id_evenement, String nom, Date date, Time heure, int id_auteur, int id_livre, String lieu, String description, int nb_ticket) {
        this.id_evenement = id_evenement;
        this.nom = nom;
        this.date = date;
        this.heure = heure;
        this.id_auteur = id_auteur;
        this.id_livre = id_livre;
        this.lieu = lieu;
        this.description = description;
        this.nb_ticket = nb_ticket;
    }
    

    

    

    // Getters and setters
    public int getId_evenement() {
        return id_evenement;
    }

    public int getNb_ticket() {
        return nb_ticket;
    }

    public void setNb_ticket(int nb_ticket) {
        this.nb_ticket = nb_ticket;
    }
    

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getHeure() {
        return heure;
    }

    public void setHeure(Time heure) {
        this.heure = heure;
    }

    public int getId_auteur() {
        return id_auteur;
    }

    public void setId_auteur(int id_auteur) {
        this.id_auteur = id_auteur;
    }

    public int getId_livre() {
        return id_livre;
    }

    public void setId_livre(int id_livre) {
        this.id_livre = id_livre;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Evenement{" + "nom=" + nom + ", date=" + date + ", heure=" + heure + ", id_auteur=" + id_auteur + ", id_livre=" + id_livre + ", lieu=" + lieu + ", description=" + description + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + this.id_evenement;
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
        final Evenement other = (Evenement) obj;
        if (this.id_evenement != other.id_evenement) {
            return false;
        }
        return true;
    }
    /*public boolean isDateValid(Date date) {
    return date != null && !date.toLocalDate().isBefore(LocalDate.now());
}*/
    public boolean isHeureValid(Time heure) {
    return heure != null;
}
  public static boolean verifString(String s ){
        
    return s.isEmpty();
    }
    
}
