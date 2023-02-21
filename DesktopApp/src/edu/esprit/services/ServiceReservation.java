/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.services;

import edu.esprit.entities.EtatReservation;
import edu.esprit.entities.Reservation;
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
 * @author SADOK
 */
public class ServiceReservation implements IService<Reservation> {

    Connection cnx = DataSource.getInstance().getCnx();

    @Override
    public void ajouter(Reservation t) {
        try {
            String req = "INSERT INTO `reservation`(`id_ticket`, `id_evenement`, `etat`) VALUES (?,?,?)";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, t.getId_ticket());
            ps.setInt(2, t.getId_evenement());
            ps.setString(3, t.getEtat().toString());
            ps.executeUpdate();
            System.out.println("Reservation added!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void modifier(Reservation t) {
        try {
            String req = "UPDATE `reservation` SET `id_ticket`=?,`id_evenement`=?,`etat`=? WHERE id_reservation=?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, t.getId_ticket());
            ps.setInt(2, t.getId_evenement());
            ps.setString(3, t.getEtat().toString());
            ps.executeUpdate();
            System.out.println("Reservation modified!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    @Override
    public void delete(int id) {
        try {
            String req = "DELETE FROM `reservation` WHERE id_reservation=?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Reservation deleted!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<Reservation> getAll() {
        List<Reservation> result = new ArrayList<>();
        try {
            String req = "SELECT * FROM `reservation`";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                int id = rs.getInt(1);
                int id_ticket = rs.getInt(2);
                int id_evenement = rs.getInt(3);
                EtatReservation er = EtatReservation.valueOf(rs.getString(4));
                Reservation r = new Reservation(id_ticket, id_evenement, er);
                result.add(r);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return result;
    }

    @Override
    public Reservation getOneById(int id) {
        Reservation result = null;
        try {

            String req = "SELECT * FROM `reservation`WHERE id_reservation=" + id;
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                result = new Reservation(rs.getInt(1), rs.getInt(2), rs.getInt(3), EtatReservation.valueOf(rs.getString(4)));

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return result;
    }

}
