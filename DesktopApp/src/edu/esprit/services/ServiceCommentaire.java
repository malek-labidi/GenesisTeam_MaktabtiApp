/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.services;

import edu.esprit.entities.Commentaire;
import edu.esprit.util.DataSource;
import java.awt.print.PrinterJob;
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
public class ServiceCommentaire implements IService<Commentaire> {
        Connection cnx = DataSource.getInstance().getCnx();


    @Override
    public void ajouter(Commentaire t) {
        if(!Commentaire.verifString(t.getCommentaire())){
            try {
                String req="INSERT INTO `commentaire`(`id_client`, `id_evenement`, `commentaire`) VALUES (?,?,?)";
                PreparedStatement ps=cnx.prepareStatement(req);
                ps.setInt(1, t.getId_client());
                ps.setInt(2, t.getId_evenement());
                ps.setString(3, t.getCommentaire());
                ps.executeUpdate();
                System.out.println("Commentaire added!");
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        
    }}

    @Override
    public void modifier(Commentaire t) {
        if(!Commentaire.verifString(t.getCommentaire())){
            try {
                String req="UPDATE `commentaire` SET `id_client`=?,`id_evenement`=?,`commentaire`=? WHERE id_commentaire=?";
                PreparedStatement ps=cnx.prepareStatement(req);
                ps.setInt(1, t.getId_client());
                ps.setInt(2, t.getId_evenement());
                ps.setString(3, t.getCommentaire());
                ps.executeUpdate();
                System.out.println("Commentaire modified!");
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
    }}

    @Override
    public void delete(int id) {
        
            try {
                String req="DELETE FROM `commentaire` WHERE id_commentaire=?";
                PreparedStatement ps = cnx.prepareStatement(req);
                ps.setInt(1, id);
                ps.executeUpdate();
                System.out.println("Commentaire deleted!");
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
    }

    @Override
    public List<Commentaire> getAll() {
        List<Commentaire> result=new ArrayList<>();
            try {
                
                String req="SELECT * FROM `commentaire`";
                Statement st = cnx.createStatement();
                ResultSet rs=st.executeQuery(req);
                while(rs.next()){
                    int id=rs.getInt(1);
                    int id_client=rs.getInt(2);
                    int id_evenement=rs.getInt(3);
                    String commentaire=rs.getString(4);
                    Commentaire c =new Commentaire(id_client, id_client, id_evenement, commentaire);
                    result.add(c);
                }   } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
            }
        return result;
        }

    @Override
    public Commentaire getOneById(int id) {
        Commentaire result = null;
            try {
                
                String req="SELECT * FROM `commentaire` WHERE id_commentaire="+id;
                Statement st = cnx.createStatement();
                ResultSet rs=st.executeQuery(req);
                while(rs.next()){
                    result= new Commentaire(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4));
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
            return result;
        
    }
    public List<Commentaire> getCommentairesByEvenement(int idEvenement) {
    List<Commentaire> result = new ArrayList<>();
    String req = "SELECT * FROM commentaire WHERE id_evenement = ?";
    try (PreparedStatement ps = cnx.prepareStatement(req)) {
        ps.setInt(1, idEvenement);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int idCommentaire = rs.getInt(1);
            int idClient = rs.getInt(2);
            String commentaire = rs.getString(4);
            Commentaire c = new Commentaire(idCommentaire, idClient, idEvenement, commentaire);
            result.add(c);
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    return result;
}
    private void deleteEventCommentsFromDatabase(int id) {
        try {
                String req="DELETE FROM `commentaire` WHERE id_evenement=?";
                PreparedStatement ps = cnx.prepareStatement(req);
                ps.setInt(1, id);
                ps.executeQuery();
                System.out.println("Commentaire deleted!");
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
    }
    private static final String[] BAD_WORDS = {"go to hell", "stupid", "you're a dog"};
    public static boolean containsBadWords(String commentaire) {
        String lowercaseCommentaire = commentaire.toLowerCase();
        for (String badWord : BAD_WORDS) {
            if (lowercaseCommentaire.contains(badWord)) {
                return true;
            }
        }
        return false;
    }
    
    
    
    
    
}
