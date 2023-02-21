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

public class Ticket {
   private TypeTicket type;
   private int id_ticket;
   
   private float prix;

    public Ticket() {
    }

    public Ticket(TypeTicket type, float prix) {
        this.type = type;
        this.prix = prix;
    }

    public Ticket(TypeTicket type, int id_ticket, float prix) {
        this.type = type;
        this.id_ticket = id_ticket;
        this.prix = prix;
    }

    public TypeTicket getType() {
        return type;
    }

    public void setType(TypeTicket type) {
        this.type = type;
    }

    public int getId_ticket() {
        return id_ticket;
    }

    

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    @Override
    public String toString() {
        return "Ticket{" + "type=" + type + ", prix=" + prix + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + this.id_ticket;
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
        final Ticket other = (Ticket) obj;
        if (this.id_ticket != other.id_ticket) {
            return false;
        }
        return true;
    }
   
    
    
}
