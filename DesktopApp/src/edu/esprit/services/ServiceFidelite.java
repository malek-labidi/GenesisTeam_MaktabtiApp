/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.services;

import edu.esprit.entities.Type;
import edu.esprit.util.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import edu.esprit.entities.Fidelite;

/**
 *
 * @author Gaaloul
 */
public class ServiceFidelite implements IService<Fidelite> {

    Connection cnx = DataSource.getInstance().getCnx();

   @Override
public List<Fidelite> getAll() {
    List<Fidelite> list = new ArrayList<>();
    try {
        String req = "SELECT * FROM `fidelite`";
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(req);
        while (rs.next()) {
            int id_fidelite = rs.getInt(1);
            int id_client = rs.getInt(2);
            int total_achat = rs.getInt(3);
            
            // Vérifier la valeur de l'attribut type
            Type type = null;
            String typeStr = rs.getString("type");
            if (typeStr != null) {
                try {
                    type = Type.valueOf(typeStr);
                } catch (IllegalArgumentException ex) {
                    System.out.println("Erreur: type inconnu " + typeStr);
                }
            }
            
            Fidelite f = new Fidelite(id_fidelite, id_client, total_achat, type);
            list.add(f);
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    return list;
}

    @Override
    public Fidelite getOneById(int id) {
        Fidelite result = null;
        try {
            String req = "SELECT * FROM `fidelite` WHERE id_fidelite = " + id;
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {

                result = new Fidelite(rs.getInt(1), rs.getInt(2), rs.getInt(3), Type.valueOf(rs.getObject("type").toString()));

            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return result;
    }

    @Override
    public void delete(int id) {
        try {
            String req = "DELETE FROM `fidelite` WHERE id_fidelite=?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("fidelite Deleted !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void ajouter(Fidelite t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void modifier(Fidelite t) {
         
             try {
                 String req = "UPDATE `Fidelite` SET `type`=? WHERE id_fidelite= ?";
                  PreparedStatement ps = cnx.prepareStatement(req);
                  ps.setString(1, t.getType().toString());
                 
                 ps.executeUpdate();
                     System.out.println("offre Updated !");
             } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public int totalacha(int id) {
    int total = 0;
    try {
        String req = "SELECT SUM(montant) as totalMontant FROM `Commande` WHERE id_client = " + id;
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(req);

        if (rs.next()) {
            total = rs.getInt("totalMontant"); // get the sum of montant as an integer
        }

        rs.close();
        st.close();
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    return total;
}
public void supprimerFidelite(Fidelite f) {
            String req = "DELETE FROM `fidelite` WHERE id_fidelite  = ?"  ;
        try {
            PreparedStatement ste = cnx.prepareStatement(req);
            ste.setInt(1, f.getId_fidelite());
            ste.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
