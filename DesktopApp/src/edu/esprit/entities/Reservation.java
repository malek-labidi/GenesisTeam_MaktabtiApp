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

public class Reservation { 
    private int id_reservation;
    private int id_ticket;
    private int id_evenement;
    private EtatReservation etat;

    public Reservation() {
    }

    public Reservation(int id_ticket, int id_evenement, EtatReservation etat) {
        this.id_ticket = id_ticket;
        this.id_evenement = id_evenement;
        this.etat = etat;
    }

    public Reservation(int id_reservation, int id_ticket, int id_evenement, EtatReservation etat) {
        this.id_reservation = id_reservation;
        this.id_ticket = id_ticket;
        this.id_evenement = id_evenement;
        this.etat = etat;
    }

   

    public int getId_reservation() {
        return id_reservation;
    }

  

    public int getId_ticket() {
        return id_ticket;
    }

    public void setId_ticket(int id_ticket) {
        this.id_ticket = id_ticket;
    }

    public int getId_evenement() {
        return id_evenement;
    }

    public void setId_evenement(int id_evenement) {
        this.id_evenement = id_evenement;
    }

    public EtatReservation getEtat() {
        return etat;
    }

    public void setEtat(EtatReservation etat) {
        this.etat = etat;
    }

    @Override
    public String toString() {
        return "Reservation{" + "id_ticket=" + id_ticket + ", id_evenement=" + id_evenement + ", etat=" + etat + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + this.id_reservation;
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
        final Reservation other = (Reservation) obj;
        if (this.id_reservation != other.id_reservation) {
            return false;
        }
        if (this.id_ticket != other.id_ticket) {
            return false;
        }
        return true;
    }
    
    
    
    
}
