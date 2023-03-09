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
import javax.swing.JOptionPane;

/**
 *
 * @author ilef
 */
public class ServiceReclamation implements IService<Reclamation> {

    Connection cnx = DataSource.getInstance().getCnx();

    @Override
public void ajouter(Reclamation r) {
    try {
        // check if user has already made 3 reclamations
        String checkReq = "SELECT COUNT(*) FROM reclamation WHERE user_id = ?";
        PreparedStatement checkStmt = cnx.prepareStatement(checkReq);
        checkStmt.setInt(1, r.getUser_id());
        ResultSet rs = checkStmt.executeQuery();
        rs.next();
        int count = rs.getInt(1);
        if (count >= 3) {
    JOptionPane.showMessageDialog(null, "User has already made 3 reclamations", "Alert", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // insert new reclamation
        String insertReq = "INSERT INTO reclamation (message, feedback, user_id) VALUES (?, ?, ?)";
        PreparedStatement insertStmt = cnx.prepareStatement(insertReq);
        insertStmt.setString(1, r.getMessage());
        insertStmt.setString(2, r.getFeedback());
        insertStmt.setInt(3, r.getUser_id());
        insertStmt.executeUpdate();
        System.out.println("Reclamation created !");
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
                Reclamation r = new Reclamation(rs.getInt(1), rs.getString("message"), rs.getString(3),rs.getInt("user_id"));
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

    public void update( List<Object> list,int id) {
            String requete="update Reclamation set message=?,feedback=?,id_reclamation=? where id_reclamation=" + id;
        try {
            PreparedStatement pst=cnx.prepareStatement(requete);
            pst.setString(1, (String) list.get(0));
            pst.setString(2, (String) list.get(1));
            pst.setInt(3,(int)list.get(2));
            
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ServiceReclamation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

   
   
    

}

