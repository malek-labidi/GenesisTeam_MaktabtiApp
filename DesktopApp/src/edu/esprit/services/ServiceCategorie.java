/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.services;

import edu.esprit.entities.Categorie;
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
 * @author Saleh
 */
public class ServiceCategorie implements IService<Categorie> {

    Connection cnx = DataSource.getInstance().getCnx();

    @Override
    public void ajouter(Categorie t) {
        if (!Categorie.verifString(t.getNom())) {
            try {
                String req = "INSERT INTO `categorie`(`nom`) VALUES (?)";
                PreparedStatement ps = cnx.prepareStatement(req);
                ps.setString(1, t.getNom());
                ps.executeUpdate();
                System.out.println("Categorie added");
                
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }

    }

    @Override
public void modifier(Categorie t) {
    if (!Categorie.verifString(t.getNom())) {
        try {
            String req = "UPDATE `categorie` SET `nom`=? WHERE id_categorie=?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, t.getNom());
            ps.setInt(2, t.getId_categorie());
            ps.executeUpdate();
            System.out.println("Categorie modifiée !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}


    @Override
    public void delete(int id) {
        try {
            String req = "DELETE FROM `categorie` WHERE id_categorie=?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Categorie deleted");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<Categorie> getAll() {
        List<Categorie> result = new ArrayList<>();
        try {

            String req = "SELECT *FROM `categorie`";
            Statement s = cnx.createStatement();
            ResultSet rs = s.executeQuery(req);
            while (rs.next()) {
                int id = rs.getInt(1);
                String type = rs.getString(2);
                Categorie c = new Categorie(id, type);
                result.add(c);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return result;
    }

    @Override
    public Categorie getOneById(int id) {
        Categorie result = null;
        try {

            String req = "SELECT * FROM `categorie` WHERE id_categorie = " + id;
            Statement s = cnx.createStatement();
            ResultSet rs = s.executeQuery(req);
            while (rs.next()) {
                result = new Categorie(rs.getInt(1), rs.getString(2));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return result;
    }

}
