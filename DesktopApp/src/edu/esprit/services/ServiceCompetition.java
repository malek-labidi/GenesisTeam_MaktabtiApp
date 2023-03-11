/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.services;

import edu.esprit.entities.Competition;
import edu.esprit.entities.Question;
import edu.esprit.util.DataSource;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 *
 * @author admin
 */
public class ServiceCompetition implements IService<Competition> {

    Connection cnx = DataSource.getInstance().getCnx();

    @Override
    public void ajouter(Competition t) throws SQLException {
        if (!Competition.verifString(t.getLien_competition()) && !Competition.verifString(t.getNom()) && !Competition.verifString(t.getRecompense())) {

            String req = "INSERT INTO `competition`(`id_livre`, `recompense`, `liste_paticipants`, `lien_competition`, `nom`, `date_debut`, `date_fin`) VALUES (?,?,?,?,?,?,?)";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, t.getId_livre());
            ps.setString(2, t.getRecompense());
            ps.setString(3, t.getListe_participants());
            ps.setString(4, t.getLien_competition());
            ps.setString(5, t.getNom());
            ps.setDate(6, t.getDate_debut());
            ps.setDate(7, t.getDate_fin());
            ps.executeUpdate();
            System.out.println("competition Added !");
        } else {
            System.out.println("Error for add ");
        }
    }

    @Override
    public void modifier(Competition t) {
        if (!Competition.verifString(t.getLien_competition()) && !Competition.verifString(t.getNom()) && !Competition.verifString(t.getRecompense())) {

            try {
                String req = "UPDATE `competition` SET `id_livre`=?,`recompense`=?,`lien_competition`=?,`nom`=?,`date_debut`=?,`date_fin`=? WHERE id_competition= ?";
                PreparedStatement ps = cnx.prepareStatement(req);
                ps.setInt(1, t.getId_livre());
                ps.setString(2, t.getRecompense());
                // ps.setString(3, t.getListe_participants());
                ps.setString(3, t.getLien_competition());
                ps.setString(4, t.getNom());
                ps.setDate(5, t.getDate_debut());
                ps.setDate(6, t.getDate_fin());
                ps.setInt(7, t.getId_competition());
                ps.executeUpdate();
                System.out.println("competition Updated !");
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    @Override
    public void delete(int id) {
        try {
            String req = "DELETE FROM `competition` WHERE id_competition=?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("competition Deleted !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<Competition> getAll() {
        List<Competition> result = new ArrayList<>();
        try {
            String req = "SELECT * FROM `competition`";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);

            while (rs.next()) {
                int id = rs.getInt(1);
                int id_livre = rs.getInt(2);
                String recomp = rs.getString(3);
                String liste_p = rs.getString(4);
                String lien_comp = rs.getString(5);
                String nom = rs.getString(6);
                Date date_debut = rs.getDate(7);
                Date date_fin = rs.getDate(8);

                Competition c = new Competition(id, id_livre, recomp, liste_p, lien_comp, nom, date_debut, date_fin);

                result.add(c);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return result;
    }

    @Override
    public Competition getOneById(int id) {
        Competition result = null;
        try {
            String req = "SELECT * FROM `competition` WHERE id_competition = " + id;
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {

                result = new Competition(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getDate(7), rs.getDate(8));

            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return result;
    }

    public static List<Competition> filterByName(List<Competition> competitions, String nom) {
        List<Competition> filteredList = new ArrayList<>();
        try {
            filteredList = competitions.stream()
                    .filter(e -> e.getNom().toLowerCase().startsWith(nom.toLowerCase()))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            // handle the exception
            System.out.println("Une erreur s'est produite lors du filtrage des competitions par nom : " + e.getMessage());
        }
        return filteredList;
    }

    public List<Question> getListQuestion(int id) {
        List<Question> lq = new ArrayList<>();
        try {

            String req = "SELECT q.id_quiz, q.id_question, q.question, q.choix1, q.choix2, q.choix3, q.reponse_correct FROM question q INNER JOIN quiz z ON q.id_quiz = z.id_quiz INNER JOIN competition c ON z.id_competition = c.id_competition WHERE c.id_competition = " + id;
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {

                Question result = new Question(rs.getInt(2),rs.getInt(1), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7));
                lq.add(result);

            }
        } catch (SQLException ex) {
            Logger.getLogger(ServiceCompetition.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lq;

    }

    public void ajouterParticipant(int id, int idcomp) {
        List<String> l = new ArrayList();
        l.add(String.valueOf(id));
        try {
            String req = "UPDATE `competition` SET `liste_paticipants` = CONCAT(liste_paticipants,?)  WHERE id_competition= ?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, l.toString());
            ps.setInt(2, idcomp);

            ps.executeUpdate();
            System.out.println("list participants Updated !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public List<String> getParticipants(int id) {
        List<String> participantsList = new ArrayList<>();
        try {
            String req = "SELECT liste_paticipants FROM competition WHERE id_competition = ?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String participantsString = rs.getString("liste_paticipants");
                participantsList = Arrays.asList(participantsString.split(","));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return participantsList;
    }
    
        public void deletebyLivre(int id) {
        try {
            String req = "DELETE FROM `competition` WHERE id_livre=?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("competition Deleted !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
