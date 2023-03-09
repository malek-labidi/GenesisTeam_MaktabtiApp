/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.services;

import edu.esprit.entities.Categorie;
import edu.esprit.entities.Competition;
import edu.esprit.entities.Livre;
import edu.esprit.entities.Utilisateur;
import edu.esprit.util.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Saleh
 */
public class ServiceLivre implements IService<Livre> {

    Connection cnx = DataSource.getInstance().getCnx();

    @Override
    public void ajouter(Livre t) {
        if (!Categorie.verifString(t.getTitre()) & !Categorie.verifString(t.getLangue()) & !Categorie.verifString(t.getResume())) {
            try {
                String req = "INSERT INTO `livre`(`id_auteur`, `id_categorie`, `titre`, `date_pub`, `langue`, `isbn`, `nb_pages`, `resume`, `prix`, `image`) VALUES (?,?,?,?,?,?,?,?,?,?)";
                PreparedStatement ps = cnx.prepareStatement(req);
                ps.setInt(1, t.getId_auteur());
                ps.setInt(2, t.getId_categorie());
                ps.setString(3, t.getTitre());
                ps.setDate(4, t.getDate_pub());
                ps.setString(5, t.getLangue());
                ps.setInt(6, t.getIsbn());
                ps.setInt(7, t.getNb_pages());
                ps.setString(8, t.getResume());
                ps.setFloat(9, t.getPrix());
                if (t.getImage() != null) {
                    ps.setBlob(10, t.getImage());
                } else {
                    ps.setNull(10, Types.BLOB);
                }

                ps.executeUpdate();
                System.out.println("Livre added");
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }

    }

    @Override
    public void modifier(Livre t) {
        if (!Categorie.verifString(t.getTitre()) & !Categorie.verifString(t.getLangue()) & !Categorie.verifString(t.getResume())) {
            try {
                String req = "UPDATE `livre` SET `id_auteur`=?,`id_categorie`=?,`titre`=?,`date_pub`=?,`langue`=?,`isbn`=?,`nb_pages`=?,`resume`=?,`prix`=? WHERE id_livre=?";
                PreparedStatement ps = cnx.prepareStatement(req);

                ps.setInt(1, t.getId_auteur());
                ps.setInt(2, t.getId_categorie());
                ps.setString(3, t.getTitre());
                ps.setDate(4, t.getDate_pub());
                ps.setString(5, t.getLangue());
                ps.setInt(6, t.getIsbn());
                ps.setInt(7, t.getNb_pages());
                ps.setString(8, t.getResume());
                ps.setFloat(9, t.getPrix());
                ps.setInt(10, t.getId_livre());
                ps.executeUpdate();
                System.out.println("Livre moodifié!");
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }

    }

    @Override
    public void delete(int id) {
        try {
            String req = "DELETE FROM `livre` WHERE id_livre=?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Livre deleted");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<Livre> getAll() {
        List<Livre> result = new ArrayList<>();
        try {

            String req = "SELECT * FROM `livre`";
            Statement s = cnx.createStatement();
            ResultSet rs = s.executeQuery(req);
            while (rs.next()) {
                int id = rs.getInt(1);
                int id_auteur = rs.getInt(2);
                int id_categorie = rs.getInt(3);
                String titre = rs.getString(4);
                Date date_pub = rs.getDate(5);
                String langue = rs.getString(6);
                int isbn = rs.getInt(7);
                int nb_pages = rs.getInt(8);
                String resume = rs.getString(9);
                int prix = rs.getInt(10);
                InputStream image= rs.getBinaryStream(11);

                Livre l = new Livre(id, id_auteur, id_categorie, titre, date_pub, langue, isbn, nb_pages, resume, prix,image);

                result.add(l);

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return result;
    }

    @Override
    public Livre getOneById(int id) {
        Livre result = null;
        try {
            String req = "SELECT * FROM `livre` WHERE id_livre = " + id;
            Statement s = cnx.createStatement();
            ResultSet rs = s.executeQuery(req);
            while (rs.next()) {
                result = new Livre(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4), rs.getDate(5), rs.getString(6), rs.getInt(7), rs.getInt(8), rs.getString(9), rs.getInt(10), rs.getBinaryStream(11));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return result;
    }

    public void supprimerLivre(Livre l) {
        String req = "DELETE FROM `livre` WHERE Id_livre  = ?";
        try {
            PreparedStatement ste = cnx.prepareStatement(req);
            ste.setInt(1, l.getId_livre());
            ste.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static List<Livre> filterByName(List<Livre> livres, String lieu) {
        List<Livre> filteredList = new ArrayList<>();
        try {
            filteredList = livres.stream()
                    .filter(e -> e.getTitre().toLowerCase().startsWith(lieu.toLowerCase()))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            // handle the exception
            System.out.println("Une erreur s'est produite lors du filtrage de Livre par Titre : " + e.getMessage());
        }
        return filteredList;
    }

    public Livre getOneByTitre(String titre) {
        Livre result = null;
        try {
            String req = "SELECT * FROM livre WHERE titre = ?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, titre);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id_livre");
                int id_auteur = rs.getInt("id_auteur");
                int id_categorie = rs.getInt("id_categorie");
                Date date_pub = rs.getDate("date_pub");
                String langue = rs.getString("langue");
                int isbn = rs.getInt("isbn");
                int nb_pages = rs.getInt("nb_pages");
                String resume = rs.getString("resume");
                int prix = rs.getInt("prix");
                InputStream image =rs.getBinaryStream("image");
                
                
                result = new Livre(id, id_auteur, id_categorie, titre, date_pub, langue, isbn, nb_pages, resume, prix,image);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return result;
    }

    public float getPrix(int id) {
        float result = 0;
        try {
            String req = "SELECT prix FROM `livre` WHERE id_livre = " + id;
            Statement s = cnx.createStatement();
            ResultSet rs = s.executeQuery(req);
            while (rs.next()) {
                result = rs.getFloat(1);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return result;
    }
    public InputStream getImageForLivre(int id) {
    InputStream result = null;
    try {
        String req = "SELECT image FROM livre WHERE id_livre = ?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            result = rs.getBinaryStream(1);
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    return result;
}

}
