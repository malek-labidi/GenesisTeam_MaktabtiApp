/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.entities;

import java.sql.Date;

/**
 *
 * @author admin
 */
public class Competition {
    private int id_competition; 
    private int id_livre;
    private String recompense; 
    private String liste_participants; 
    private String lien_competition; 
    private String nom; 
    private Date date_debut; 
    private Date date_fin; 

    public Competition() {
    }

    public Competition(int id_livre, String recompense, String liste_participants, String lien_competition, String nom, Date date_debut, Date date_fin) {
        this.id_livre = id_livre;
        this.recompense = recompense;
        this.liste_participants = liste_participants;
        this.lien_competition = lien_competition;
        this.nom = nom;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
    }

    public Competition(int id_competition, int id_livre, String recompense, String liste_participants, String lien_competition, String nom, Date date_debut, Date date_fin) {
        this.id_competition = id_competition;
        this.id_livre = id_livre;
        this.recompense = recompense;
        this.liste_participants = liste_participants;
        this.lien_competition = lien_competition;
        this.nom = nom;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
    }
    
    

    public int getId_competition() {
        return id_competition;
    }

    public int getId_livre() {
        return id_livre;
    }

    public void setId_livre(int id_livre) {
        this.id_livre = id_livre;
    }

    public String getRecompense() {
        return recompense;
    }

    public void setRecompense(String recompense) {
        this.recompense = recompense;
    }

    public String getListe_participants() {
        return liste_participants;
    }

    public void setListe_participants(String liste_participants) {
        this.liste_participants = liste_participants;
    }

    public String getLien_competition() {
        return lien_competition;
    }

    public void setLien_competition(String lien_competition) {
        this.lien_competition = lien_competition;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Date getDate_debut() {
        return date_debut;
    }

    public void setDate_debut(Date date_debut) {
        this.date_debut = date_debut;
    }

    public Date getDate_fin() {
        return date_fin;
    }

    public void setDate_fin(Date date_fin) {
        this.date_fin = date_fin;
    }

    @Override
    public String toString() {
        return "Competition{" + "id_livre=" + id_livre + ", recompense=" + recompense + ", liste_participants=" + liste_participants + ", lien_competition=" + lien_competition + ", nom=" + nom + ", date_debut=" + date_debut + ", date_fin=" + date_fin + '}';
    }
    public static boolean verifString(String s ){
        
    return s.isEmpty();
    }
    
    
    
}
