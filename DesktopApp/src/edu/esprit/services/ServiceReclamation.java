/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.services;

import edu.esprit.entities.Reclamation;
import edu.esprit.util.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ilef
 */
public class ServiceReclamation implements IService<Reclamation> {

    Connection cnx = DataSource.getInstance().getCnx();

    @Override
    public void ajouter(Reclamation r) {
        try {
            String req = "INSERT INTO `reclamation` (`message`, `feedback`) VALUES ('" + r.getMessage() + "', '" + r.getFeedback() + "')";
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("Reclamation created !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void ajouter2(Reclamation r) {
        try {
            String req = "INSERT INTO `reclamation` (`message`, `feedback`) VALUES (?,?)";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(2, r.getMessage());
            ps.setString(1, r.getFeedback());
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void delete(int id_reclamation) {
        try {
            String req = "DELETE FROM `reclamation` WHERE id_reclamation = " + id_reclamation;
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("Reclamation deleted !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void modifier(Reclamation t){
          try {
            String req = "UPDATE `reclamation` SET `message`=?,`feedback`=? WHERE id_reclamation=?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, t.getMessage());
            ps.setString(2,t.getFeedback());
            ps.setInt(3, t.getId_reclamation());
            
            ps.executeUpdate();
            System.out.println("reclamation Updated !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
  
}

    @Override
    public List<Reclamation> getAll() {
        List<Reclamation> list = new ArrayList<>();
        try {
            String req = "Select * from reclamation";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                Reclamation r = new Reclamation(rs.getInt(1), rs.getString("message"), rs.getString(3));
                list.add(r);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return list;
    }

    @Override
    public Reclamation getOneById(int id_reclamation) {
        Reclamation r = null;
        try {
            String req = "Select * from reclamation";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                r = new Reclamation(rs.getInt(1), rs.getString("message"), rs.getString(3));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return r;
    }

   

   

}
