/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.services;

import edu.esprit.entities.Panier;
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
 * @author SADOK
 */
public class ServicePanier implements IService<Panier> {

    Connection cnx = DataSource.getInstance().getCnx();

    @Override
    public void ajouter(Panier t) {
        try {
            String req = "INSERT INTO `panier`(`id_livre`, `quantite`, `totalPrix`) VALUES (?,?,?)";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, t.getId_livre());
            ps.setInt(2, t.getQuantite());
            ps.setFloat(3, t.getTotalPrix());
            ps.executeUpdate();
            System.out.println("Panier created!");

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    @Override
    public void modifier(Panier t) {
        try {
            String req = "UPDATE `panier` SET`id_livre`=?,`quantite`=?,`totalPrix`=? WHERE  `id_panier`= ?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, t.getId_livre());
            ps.setInt(2, t.getQuantite());
            ps.setFloat(3, t.getTotalPrix());
            ps.setInt(4, t.getId_panier());
            ps.executeUpdate();
            
            System.out.println("panier Updated !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        try {
            String req = "DELETE FROM `panier` WHERE id_panier= ?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ps.executeUpdate();
            
            System.out.println("Panier deleted!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    @Override
    public List<Panier> getAll() {
        List<Panier> result = new ArrayList<>();
        try {
            String req = "SELECT * FROM `panier`";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                int id_panier = rs.getInt(1);
                int id_livre = rs.getInt(2);
                int quantite = rs.getInt(3);
                float prix = rs.getFloat(4);
                Panier p = new Panier(id_panier, id_livre, quantite, prix);
                result.add(p);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return result;

    }

    @Override
    public Panier getOneById(int id) {
        Panier result = null;
        try {
            String req = "SELECT * FROM `panier` WHERE id_panier = " + id;
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {

                result = new Panier(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getFloat(4));

            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return result;
    }

}
