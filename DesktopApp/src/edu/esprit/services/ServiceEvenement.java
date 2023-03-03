/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.services;

import edu.esprit.entities.Evenement;
import edu.esprit.util.DataSource;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 *
 * @author SADOK
 */
public class ServiceEvenement implements IService<Evenement> {

    Connection cnx = DataSource.getInstance().getCnx();

    @Override
    public void ajouter(Evenement t) {

        try {
            String req = "INSERT INTO `evenement`(`nom`, `date`, `heure`, `id_auteur`, `id_livre`, `lieu`, `description`, `nb_ticket`) VALUES (?,?,?,?,?,?,?,?)";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, t.getNom());
            ps.setDate(2, t.getDate());
            ps.setTime(3, t.getHeure());
            ps.setInt(4, t.getId_auteur());
            ps.setInt(5, t.getId_livre());
            ps.setString(6, t.getLieu());
            ps.setString(7, t.getDescription());
            ps.setInt(8, t.getNb_ticket());
            ps.executeUpdate();
            System.out.println("Evenement added!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void modifier(Evenement t) {

        try {
            String req = "UPDATE `evenement` SET `nom`=?,`date`=?,`heure`=?,`id_auteur`=?,`id_livre`=?,`lieu`=?,`description`=?, `nb_ticket`=? WHERE id_evenement=?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(9, t.getId_evenement());

            ps.setString(1, t.getNom());
            ps.setDate(2, t.getDate());
            ps.setTime(3, t.getHeure());
            ps.setInt(4, t.getId_auteur());
            ps.setInt(5, t.getId_livre());
            ps.setString(6, t.getLieu());
            ps.setString(7, t.getDescription());
            ps.setInt(8, t.getNb_ticket());
            ps.executeUpdate();
            System.out.println("Evenement modified!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    @Override
    public void delete(int id) {
        try {
            String req = "DELETE FROM `evenement` WHERE id_evenement=?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Evenement deleted!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

        }
    }

    @Override
    public List<Evenement> getAll() {
        List<Evenement> result = new ArrayList<>();
        try {
            String req = "SELECT * FROM `evenement`";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                int id = rs.getInt(1);
                String nom = rs.getString(2);
                Date date = rs.getDate(3);
                Time heure = rs.getTime(4);
                int id_auteur = rs.getInt(5);
                int id_livre = rs.getInt(6);
                String lieu = rs.getString(7);
                String description = rs.getString(8);
                int nb_ticket = rs.getInt(9);
                Evenement e = new Evenement(id, nom, date, heure, id_auteur, id_livre, lieu, description, nb_ticket);
                result.add(e);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return result;
    }

    @Override
    public Evenement getOneById(int id) {
        Evenement result = null;
        try {
            String req = "SELECT * FROM `evenement`WHERE id_evenement=" + id;
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                result = new Evenement(rs.getInt(1), rs.getString(2), rs.getDate(3), rs.getTime(4), rs.getInt(5), rs.getInt(6), rs.getString(7), rs.getString(8), rs.getInt(9));
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return result;
    }

    public static List<Evenement> filterByLocation(List<Evenement> evenements, String lieu) {
        List<Evenement> filteredList = new ArrayList<>();
        try {
            filteredList = evenements.stream()
                    .filter(e -> e.getLieu().toLowerCase().startsWith(lieu.toLowerCase()))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            // handle the exception
            System.out.println("An error occurred while filtering events by location: " + e.getMessage());
        }
        return filteredList;
    }

    public static List<Evenement> filterByDate(List<Evenement> evenements, Date date) {
        List<Evenement> filteredList = new ArrayList<>();
        try {
            filteredList = evenements.stream()
                    .filter(e -> e.getDate().toLocalDate().equals(date.toLocalDate()))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            // handle the exception
            System.out.println("An error occurred while filtering events by date: " + e.getMessage());
        }
        return filteredList;
    }

    public void decrementNbTickets(int eventId, int nbTickets) {

        try {

            String query = "UPDATE evenement SET nb_tickets = nb_tickets - ? WHERE id_evenement = ?";

            PreparedStatement ps = cnx.prepareStatement(query);
            ps.setInt(1, nbTickets);
            ps.setInt(2, eventId);
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

}
