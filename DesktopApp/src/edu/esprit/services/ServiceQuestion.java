/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.services;

import edu.esprit.entities.Question;
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
 * @author admin
 */
public class ServiceQuestion implements IService<Question> {

    Connection cnx = DataSource.getInstance().getCnx();

    @Override
    public void ajouter(Question t) {
        if (!Question.verifString(t.getChoix1()) && !Question.verifString(t.getChoix2()) && !Question.verifString(t.getChoix3()) && !Question.verifString(t.getReponse_correct())) {
            try {
                String req = "INSERT INTO `question`(`choix1`, `choix2`, `choix3`, `reponse_correct`) VALUES (?,?,?,?)";
                PreparedStatement ps = cnx.prepareStatement(req);
                ps.setString(1, t.getChoix1());
                ps.setString(2, t.getChoix2());
                ps.setString(3, t.getChoix3());
                ps.setString(4, t.getReponse_correct());

                ps.executeUpdate();
                System.out.println("question Added !");
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }

    }

    @Override
    public void modifier(Question t) {
        if (!Question.verifString(t.getChoix1()) && !Question.verifString(t.getChoix2()) && !Question.verifString(t.getChoix3()) && !Question.verifString(t.getReponse_correct())){
        try {
            String req = "UPDATE `question` SET `choix1`=?,`choix2`=?,`choix3`=?,`reponse_correct`=? WHERE id_question =?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, t.getChoix1());
            ps.setString(2, t.getChoix2());
            ps.setString(3, t.getChoix3());
            ps.setString(4, t.getReponse_correct());
            ps.setInt(5, t.getId_question());

            ps.executeUpdate();
            System.out.println("question Updated !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }}

    @Override
    public void delete(int id) {
        try {
            String req = "DELETE FROM `question` WHERE id_question=?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("question Deleted !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<Question> getAll() {
        List<Question> result = new ArrayList<>();
        try {
            String req = "SELECT * FROM `question`";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                int id = rs.getInt(1);
                String choix1 = rs.getString(2);
                String choix2 = rs.getString(3);
                String choix3 = rs.getString(4);
                String reponse_correct = rs.getString(5);

                Question q = new Question(id, choix1, choix2, choix3, reponse_correct);

                result.add(q);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return result;
    }

    @Override
    public Question getOneById(int id) {
        Question result = null;
        try {
            String req = "SELECT * FROM `question` WHERE id_question = " + id;
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {

                result = new Question(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));

            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return result;
    }

}
