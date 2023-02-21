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
public class Commentaire {
    private int id_commentaire;
    private int id_client;
    private int id_evenement;
    String commentaire;

    public Commentaire() {
    }

    public Commentaire(int id_client, int id_evenement, String commentaire) {
        this.id_client = id_client;
        this.id_evenement = id_evenement;
        this.commentaire = commentaire;
    }

    public Commentaire(int id_commentaire, int id_client, int id_evenement, String commentaire) {
        this.id_commentaire = id_commentaire;
        this.id_client = id_client;
        this.id_evenement = id_evenement;
        this.commentaire = commentaire;
    }

    public int getId_commentaire() {
        return id_commentaire;
    }

    

    public int getId_client() {
        return id_client;
    }

    public void setId_client(int id_client) {
        this.id_client = id_client;
    }

    public int getId_evenement() {
        return id_evenement;
    }

    public void setId_evenement(int id_evenement) {
        this.id_evenement = id_evenement;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    @Override
    public String toString() {
        return "Commentaire{" + "id_client=" + id_client + ", id_evenement=" + id_evenement + ", commentaire=" + commentaire + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + this.id_commentaire;
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
        final Commentaire other = (Commentaire) obj;
        if (this.id_commentaire != other.id_commentaire) {
            return false;
        }
        return true;
    }
    public static boolean verifString(String s ){
        
    return s.isEmpty();
    }
    
    
}
