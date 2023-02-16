/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.services;

import edu.esprit.entities.Commande;
import edu.esprit.entities.Etat;
import edu.esprit.entities.Mode;
import edu.esprit.entities.Status;
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
 * @author abdelazizlahmar
 */
public class ServiceCommande implements IService<Commande> {

    Connection cnx = DataSource.getInstance().getCnx();

    @Override
    public void ajouter(Commande t) {

        try {
            String req = "INSERT INTO `commande`( `id_livre`, `id_client`, `status`, `mode`, `etat`) VALUES (?,?,?,?,?)";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, t.getId_livre());
            ps.setInt(2, t.getId_client());
            ps.setString(3, t.getStatus().toString());
            ps.setString(4, t.getMode().toString());
            ps.setString(5, t.getEtat().toString());
            ps.executeUpdate();
            System.out.println("Commande created ");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    @Override
    public void modifier(Commande t) {
        try {
            String req = "UPDATE `commande` SET `id_livre`=?,`id_client`=?,`status`=?,`mode`=?,`etat`=? WHERE id_commande=?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, t.getId_livre());
            ps.setInt(2, t.getId_client());
            ps.setString(3, t.getStatus().toString());
            ps.setString(4, t.getMode().toString());
            ps.setString(5, t.getEtat().toString());
            ps.setInt(6, t.getId_commande());
            ps.executeUpdate();
            System.out.println("Commande updated  ");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    @Override
    public void delete(int id) {
        try {
            String req = "DELETE FROM `commande` WHERE id_commande= ?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Commande deleted");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

        }

    }

    @Override
    public List<Commande> getAll() {
        List<Commande> result = new ArrayList<>();
        try {

            String req = "SELECT * FROM `commande`";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                int id_commande = rs.getInt(1);
                int id_livre = rs.getInt(2);
                int id_client = rs.getInt(3);
                Status status = Status.valueOf(rs.getObject(4).toString());

                Mode mode = Mode.valueOf(rs.getObject(5).toString());
                Etat etat = Etat.valueOf(rs.getObject(6).toString());

                Commande c = new Commande(id_livre, id_client, id_commande, status, mode, etat);

                result.add(c);

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return result;
    }

    @Override
    public Commande getOneById(int id) {
        Commande result = null;
        try {
            String req = "SELECT * FROM `commande` WHERE id_commande=" + id;
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {

                result = new Commande(rs.getInt(1), rs.getInt(2), rs.getInt(3), Status.valueOf(rs.getObject(4).toString()), Mode.valueOf(rs.getObject(5).toString()), Etat.valueOf(rs.getObject(6).toString()));

            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return result;
    }
    

}