/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.entities;

import java.io.InputStream;
import java.sql.Date;


/**
 *
 * @author Saleh
 */
public class Livre {

    private int id_livre;
    private int id_auteur;
    private int id_categorie;
    private String titre;
    private Date date_pub;
    private String langue;
    private int isbn;
    private int nb_pages;
    private String resume;
    private float prix;
    private InputStream image;
            

    public Livre() {
    }

    public Livre(int id_auteur, int id_categorie, String titre, Date date_pub, String langue, int isbn, int nb_pages, String resume, float prix, InputStream image) {
        this.id_auteur = id_auteur;
        this.id_categorie = id_categorie;
        this.titre = titre;
        this.date_pub = date_pub;
        this.langue = langue;
        this.isbn = isbn;
        this.nb_pages = nb_pages;
        this.resume = resume;
        this.prix = prix;
        this.image=image;
    }

    public Livre(int id_livre, int id_auteur, int id_categorie, String titre, Date date_pub, String langue, int isbn, int nb_pages, String resume, float prix,InputStream image) {
        this.id_livre = id_livre;
        this.id_auteur = id_auteur;
        this.id_categorie = id_categorie;
        this.titre = titre;
        this.date_pub = date_pub;
        this.langue = langue;
        this.isbn = isbn;
        this.nb_pages = nb_pages;
        this.resume = resume;
        this.prix = prix;
        this.image=image;
    }

    public Livre(String titre, float prix) {
        this.titre = titre;
        this.prix = prix;
    }
  

    public int getId_livre() {
        return id_livre;
    }

    public int getId_auteur() {
        return id_auteur;
    }

    public void setId_auteur(int id_auteur) {
        this.id_auteur = id_auteur;
    }

    public int getId_categorie() {
        return id_categorie;
    }

    public void setId_categorie(int id_categorie) {
        this.id_categorie = id_categorie;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public Date getDate_pub() {
        return date_pub;
    }

    public void setDate_pub(Date date_pub) {
        this.date_pub = date_pub;
    }

    public String getLangue() {
        return langue;
    }

    public void setLangue(String langue) {
        this.langue = langue;
    }

    public int getIsbn() {
        return isbn;
    }

    public void setIsbn(int isbn) {
        this.isbn = isbn;
    }

    public int getNb_pages() {
        return nb_pages;
    }

    public void setNb_pages(int nb_pages) {
        this.nb_pages = nb_pages;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public InputStream getImage() {
        return image;
    }

    public void setImage(InputStream image) {
        this.image = image;
    }
    

    @Override
    public String toString() {
        return "Livre{" + "id_auteur=" + id_auteur + ", id_categorie=" + id_categorie + ", titre=" + titre + ", date_pub=" + date_pub + ", langue=" + langue + ", isbn=" + isbn + ", nb_pages=" + nb_pages + ", resume=" + resume + ", prix=" + prix + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + this.id_livre;
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
        final Livre other = (Livre) obj;
        if (this.id_livre != other.id_livre) {
            return false;
        }
        if (this.isbn != other.isbn) {
            return false;
        }
        return true;
    }
      public static boolean verifString(String s ){
        
    return s.isEmpty();
    }

}
