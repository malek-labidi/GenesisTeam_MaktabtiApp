/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.services;
import edu.esprit.entities.Messagerie;
import edu.esprit.util.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author Ilef
 */

   public class ServiceMessagerie implements IService<Messagerie> {

    Connection cnx = DataSource.getInstance().getCnx();

    @Override
public void ajouter(Messagerie m) {
    try {
        String req = "INSERT INTO `messagerie` (`id_destinataire`, `message`, `date_heure`) VALUES (?, ?, ?)";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, m.getId_destinataire());
        ps.setString(2, m.getMessage());
        ps.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
        ps.executeUpdate();
        System.out.println("Messagerie created !");
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
}


    @Override
    public void delete(int id_messagerie) {
        try {
            String req = "DELETE FROM `messagerie` WHERE id_messagerie = ?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id_messagerie);
            ps.executeUpdate();
            System.out.println("Messagerie deleted !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

   @Override
public void modifier(Messagerie m){
    try {
        String req = "UPDATE `messagerie` SET `id_destinataire`=?,`message`=?,`date_heure`=? WHERE id_messagerie=?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, m.getId_destinataire());
        ps.setString(2, m.getMessage());
        ps.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
        ps.setInt(4, m.getId_messagerie());
        ps.executeUpdate();
        System.out.println("Messagerie Updated !");
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
}

    @Override
    public List<Messagerie> getAll() {
        List<Messagerie> list = new ArrayList<>();
        try {
            String req = "SELECT * FROM `messagerie`";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                Messagerie m = new Messagerie(
                        rs.getInt("id_messagerie"), 
                        rs.getInt("id_destinataire"), 
                        rs.getString("message"), 
                        rs.getTimestamp("date_heure").toLocalDateTime()
                        //StatutMessage.valueOf(rs.getString("statut_message"))
                );
                list.add(m);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return list;
    }

    @Override
    public Messagerie getOneById(int id_messagerie) {
        Messagerie m = null;
        try {
            String req = "SELECT * FROM `messagerie` WHERE id_messagerie=?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id_messagerie);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                m = new Messagerie(rs.getInt("id_messagerie"),rs.getInt("id_destinataire"),rs.getString("message"),rs.getTimestamp("date_heure").toLocalDateTime());
}
} catch (SQLException ex) {
System.out.println(ex.getMessage());
}
return m;
}
}