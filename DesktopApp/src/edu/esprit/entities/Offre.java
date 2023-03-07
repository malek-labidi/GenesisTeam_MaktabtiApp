/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.entities;

/**
 *
 * @author Gaaloul
 */
public class Offre {
     private int id_offre;
    private int id_livre;
    private String pourcentage_solde;
    private float prix_soldé;
    public Offre(){
        
    }

    public Offre(int id_livre, String pourcentage_solde, float prix_soldé) {
        this.id_livre = id_livre;
        this.pourcentage_solde = pourcentage_solde;
        this.prix_soldé = prix_soldé;
    }

    public Offre(int id_offre, int id_livre, String pourcentage_solde, float prix_soldé) {
        this.id_offre = id_offre;
        this.id_livre = id_livre;
        this.pourcentage_solde = pourcentage_solde;
        this.prix_soldé = prix_soldé;
    }

    public int getId_offre() {
        return id_offre;
    }

    public int getId_livre() {
        return id_livre;
    }

    public String getPourcentage_solde() {
        return pourcentage_solde;
    }

    public float getPrix_soldé() {
        return prix_soldé;
    }

    public void setId_offre(int id_offre) {
        this.id_offre = id_offre;
    }

    public void setId_livre(int id_livre) {
        this.id_livre = id_livre;
    }

    public void setPourcentage_solde(String pourcentage_solde) {
        this.pourcentage_solde = pourcentage_solde;
    }

    public void setPrix_soldé(float prix_soldé) {
        this.prix_soldé = prix_soldé;
    }

    @Override
    public String toString() {
        return "Offre{" + "id_offre=" + id_offre + ", id_livre=" + id_livre + ", pourcentage_solde=" + pourcentage_solde + ", prix_sold\u00e9=" + prix_soldé + '}';
    }
    public static boolean verifString(String s){
        return s.isEmpty();
    }
  public static boolean verif_pourcentage_solde(String s){
      
      if (s.matches("\\d+%")){
      String ss =s.substring(0, s.indexOf("%"));
      
      //System.out.println(ss);
      int nombre =Integer.parseInt(ss);
      //System.out.println(ss);
       
      if (nombre <= 99 && nombre >= 1){
          return true;
      }else{
          return false;
      }
      
  }else{
         return false;

      }
}
}
