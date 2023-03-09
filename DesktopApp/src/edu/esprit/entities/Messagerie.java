package edu.esprit.entities;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Messagerie {
    
    private int id_messagerie;
    private int id_destinataire;
    private String message;
    private LocalDateTime date_heure;

    public Messagerie(int id_messagerie, int id_destinataire, String message, LocalDateTime date_heure) {
        this.id_messagerie = id_messagerie;
        this.id_destinataire = id_destinataire;
        this.message = message;
        this.date_heure = date_heure;
    }

    public Messagerie(String message) {
        this.message = message;
        this.date_heure = LocalDateTime.now();
    }

    public Messagerie(int id_destinataire, String message, LocalDateTime date_heure) {
        this.id_destinataire = id_destinataire;
        this.message = message;
        this.date_heure = date_heure;
    }


    
    public int getId_messagerie() {
        return id_messagerie;
    }

    public void setId_messagerie(int id_messagerie) {
        this.id_messagerie = id_messagerie;
    }

    public int getId_destinataire() {
        return id_destinataire;
    }

    public void setId_destinataire(int id_destinataire) {
        this.id_destinataire = id_destinataire;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getDate_heure() {
        return date_heure;
    }

    public void setDate_heure(LocalDateTime date_heure) {
        this.date_heure = date_heure;
    }
    
    public void setCurrentDate() {
        this.date_heure = LocalDateTime.now();
    }
    
    public String formatDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return this.date_heure.format(formatter);
    }
    
}
