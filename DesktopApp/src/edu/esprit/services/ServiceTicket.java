/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.services;

import edu.esprit.entities.Ticket;
import edu.esprit.entities.TypeTicket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import edu.esprit.util.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author SADOK
 */
public class ServiceTicket implements IService<Ticket> {

    Connection cnx = DataSource.getInstance().getCnx();

    @Override
    public void ajouter(Ticket t) {
        try {
            String req = "INSERT INTO `ticket`(`type`, `prix`) VALUES (?,?)";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, t.getType().toString());
            ps.setFloat(2, t.getPrix());
            ps.executeUpdate();
            System.out.println("Ticket added!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void modifier(Ticket t) {
        try {
            String req = "UPDATE `ticket` SET `type`=?,`prix`=? WHERE id_ticket=?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, t.getType().toString());
            ps.setFloat(2, t.getPrix());
            ps.executeUpdate();
            System.out.println("Ticket modified!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        try {
            String req = "DELETE FROM `ticket` WHERE id_ticket=?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Ticket deleted!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    @Override
    public List<Ticket> getAll() {
        List<Ticket> result = new ArrayList<>();
        try {

            String req = "SELECT * FROM `ticket`";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                int id = rs.getInt(1);
                TypeTicket type = TypeTicket.valueOf(rs.getString(2));
                float prix = rs.getFloat(3);
                Ticket t = new Ticket(type, id, prix);
                result.add(t);

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return result;
    }

    @Override
    public Ticket getOneById(int id) {
        Ticket result = null;
        try {

            String req = "SELECT * FROM `ticket`WHERE id_ticket=" + id;
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                result = new Ticket(TypeTicket.valueOf(rs.getString(1)), rs.getInt(2), rs.getInt(3));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return result;
    }

}
