/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.entities;

/**
 *
 *
 */
public class Reclamation {
    private int id_reclamation;
    private String message, feedback;

    public Reclamation() {
    }
    
    public Reclamation(String message, String feedback) {
        this.message = message;
        this.feedback = feedback;
    }

    public Reclamation(int id_reclamation, String message, String feedback) {
        this.id_reclamation= id_reclamation;
        this.message = message;
        this.feedback = feedback;
    }

    public int getId_reclamation() {
        return id_reclamation;
    }

    public String getMessage() {
        return message;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setId_reclamation(int id_reclamation) {
        this.id_reclamation = id_reclamation;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    @Override
    public String toString() {
        return "reclamation{" + "message=" + message + ", feedback=" + feedback + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
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
        final Reclamation other = (Reclamation) obj;
        if (this.id_reclamation != other.id_reclamation) {
            return false;
        }
        return true;
    }
    
    
}
