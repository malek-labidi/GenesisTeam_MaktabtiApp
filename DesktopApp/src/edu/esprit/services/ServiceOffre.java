/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.services;

import edu.esprit.entities.Livre;
import edu.esprit.entities.Offre;
import static edu.esprit.entities.Offre.verif_pourcentage_solde;
import edu.esprit.util.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Gaaloul
 */
public class ServiceOffre implements IService<Offre>{
            Connection cnx = DataSource.getInstance().getCnx();

    @Override
    public void delete(int id) {
         try {
            String req = "DELETE FROM `offre` WHERE id_offre=?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("offre Deleted !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public void deletebyIdlivre(int id) {
         try {
            String req = "DELETE FROM `offre` WHERE id_livre=?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("offre Deleted !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    
    @Override
    public List<Offre> getAll() {
                List<Offre> list = new ArrayList<>();
        try {
            String req = "SELECT * FROM `offre`";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {

           Offre o = new Offre(rs.getInt(1), rs.getInt(2), rs.getString(3),rs.getInt(4));
                list.add(o);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return list;
    }
    @Override
     public Offre getOneById(int id) {
        Offre result = null;
        try {
            String req = "SELECT * FROM `offre` WHERE id_offre = " + id;
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {

                result = new Offre(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getInt(4));

            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return result;
    }

    @Override
    public void ajouter(Offre o) {
        if (verif_pourcentage_solde(o.getPourcentage_solde())==true  && getOneByIdlivre(o.getId_livre())==null){
          try {
            String req = "INSERT INTO `offre`(`id_offre`, `id_livre`, `pourcentage_solde`, `prix_soldé` ) VALUES (?,?,?,?)";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, o.getId_offre());
            ps.setInt(2,o.getId_livre());
            ps.setString(3, o.getPourcentage_solde());
            ps.setFloat(4, o.getPrix_soldé());
           
                ps.executeUpdate();
            System.out.println("offre Added !");
        
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        }else if (verif_pourcentage_solde(o.getPourcentage_solde())==false){
            System.out.println("veuillez saisir une pourcentage sous forme de nn% ");
            
        }    else if(getOneByIdlivre(o.getId_livre())!=null) {
                        System.out.println(" verifiez si ce livre est déja en solde ");

        }
    }

    @Override
    public void modifier(Offre o) {
        if ( verif_pourcentage_solde(o.getPourcentage_solde())==true ){
             try {
                 String req = "UPDATE `offre` SET `id_livre`=?,`pourcentage_solde`=?,`prix_soldé`=? WHERE id_offre= ?";
                  PreparedStatement ps = cnx.prepareStatement(req);
                  ps.setInt(1, o.getId_livre());
                  ps.setString(2,o.getPourcentage_solde());
                 ps.setFloat(3, o.getPrix_soldé());
                 ps.setInt(4,o.getId_offre());
                 ps.executeUpdate();
                     System.out.println("offre Updated !");
             } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
          
        }}
    
     public Offre getOneByIdlivre(int id) {
        Offre result = null;
        try {
            String req = "SELECT * FROM `offre` WHERE id_livre = " + id;
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {

                result = new Offre(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getInt(4));

            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return result;
    }
    
         
    public Livre getOneLivreById(int id) {
        Livre result = null;
        try {
            String req = "SELECT * FROM `livre` WHERE id_livre = " + id;
            Statement s = cnx.createStatement();
            ResultSet rs = s.executeQuery(req);
            while (rs.next()) {
                result = new Livre(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4), rs.getDate(5), rs.getString(6), rs.getInt(7), rs.getInt(8), rs.getString(9), rs.getInt(10),null);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return result;
    }
    
    public float calculprixsoldé(float prix_init,String pour){
        float prix_soldé=0;
         String ss =pour.substring(0, pour.indexOf("%"));
      
      //System.out.println(ss);
      float nombre =Float.parseFloat(ss);
      float p=(float) (nombre / 100.0);
        //System.out.println(p);
      prix_soldé=prix_init-(prix_init*p);
        
        return prix_soldé;
        
    }
}