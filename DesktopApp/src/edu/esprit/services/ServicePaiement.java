/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.services;

import edu.esprit.entities.Paiement;
import edu.esprit.util.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import edu.esprit.entities.Mode;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author abdelazizlahmar
 */
public class ServicePaiement implements IService <Paiement> {
    
    Connection cnx = DataSource.getInstance().getCnx();

    @Override
    public void ajouter(Paiement t) {
        try {
            String req = "INSERT INTO `paiement`(`id_paiement`, `id_client`, `montant`, `mode`) VALUES (?,?,?,?)";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, t.getId_paiement());
            ps.setInt(2, t.getId_client());
            ps.setInt(3, t.getMontant());
            ps.setString(4, t.getMode().toString());
            ps.executeUpdate();
            System.out.println("Paiement created ");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void modifier(Paiement t) {
        try {
            String req = "UPDATE `paiement` SET `id_paiement`='?,`id_client`=?,`montant`=?,`mode`=? WHERE id_paiement=?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, t.getId_paiement());
            ps.setInt(2, t.getId_client());
            ps.setInt(3, t.getMontant());
            ps.setString(4, t.getMode().toString());
            ps.executeUpdate();
            System.out.println("Paiement modified ");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

        }
    }

    @Override
    public void delete(int id) {
        try {
            String req = "DELETE FROM `paiement` WHERE id_paiement=?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Paiement deleted");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<Paiement> getAll() {
                List<Paiement> result = new ArrayList<>();

        try {
            String req = "SELECT * FROM `paiement`";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                int id_paiement = rs.getInt(1);
                int id_client = rs.getInt(2);
                int montant = rs.getInt(3);
                Mode mode = Mode.valueOf(rs.getObject(4).toString());
                
                Paiement p = new Paiement(id_paiement, id_client, montant , mode);

                result.add(p);
                
            }   } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return result;
        
    }
         
            

    @Override
    public Paiement getOneById(int id) {
        Paiement result = null;
        try {
            String req = "SELECT * FROM `paiement` WHERE id_paiement=" + id;
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {

                result = new Paiement(rs.getInt(1), rs.getInt(2), rs.getInt(3), Mode.valueOf(rs.getObject(4).toString()));

            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return result;
    }
    }
    
    
    
    
    

