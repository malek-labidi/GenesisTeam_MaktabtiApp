/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.services;

import edu.esprit.entities.Question;
import edu.esprit.entities.Quiz;
import edu.esprit.entities.ResultatQuiz;
import edu.esprit.entities.Utilisateur;
import edu.esprit.util.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

/**
 *
 * @author admin
 */
public class ServiceResultatQuiz implements IService<ResultatQuiz> {

    Connection cnx = DataSource.getInstance().getCnx();

    @Override
    public void ajouter(ResultatQuiz t) {
        ServiceUtilisateur su = new ServiceUtilisateur();
        Utilisateur u = su.getOneById(t.getId_client());
        ServiceQuiz sq = new ServiceQuiz();
        Quiz q = sq.getOneById(t.getId_quiz());
        if (u != null && q != null) {
            try {
                String req = "INSERT INTO `resulat_quiz`(`id_client`, `id_quiz`, `score`, `reponse_client`) VALUES (?,?,?,?)";
                PreparedStatement ps = cnx.prepareStatement(req);
                ps.setInt(1, t.getId_client());
                ps.setInt(2, t.getId_quiz());
                ps.setInt(3, t.getScore());
                ps.setString(4, t.getReponse_client().toString());
                ps.executeUpdate();
                System.out.println("ResultatQuiz Added !");
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        } else if (u == null) {
            System.out.println("user dosen't exist");
        } else {
            System.out.println("quiz dosen't exist");

        }

    }

    @Override
    public void modifier(ResultatQuiz t) {
        try {
            String req = "UPDATE `resulat_quiz` SET `id_client`=?,`id_quiz`=?,`score`=?,`reponse_client`=? WHERE id_resltat=?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, t.getId_client());
            ps.setInt(2, t.getId_quiz());
            ps.setInt(3, t.getScore());
            ps.setString(4, String.join(",", t.getReponse_client()));
            ps.executeUpdate();
            System.out.println("ResultatQuiz updated!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        try {
            String req = "DELETE FROM `resulat_quiz` WHERE id_resultat=?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("resultatQuiz deleted!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<ResultatQuiz> getAll() {
        List<ResultatQuiz> resultat = new ArrayList<>();
        try {
            String req = "SELECT * FROM `resulat_quiz`";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                int id = rs.getInt(1);
                int id_client = rs.getInt(2);
                int id_quiz = rs.getInt(3);
                int score = rs.getInt(4);
                String[] reponses = rs.getString(5).split(",");
                List<String> reponseClient = Arrays.asList(reponses);
                ResultatQuiz r = new ResultatQuiz(id, id_client, id_quiz, score, reponseClient);
                resultat.add(r);

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return resultat;
    }

    @Override
    public ResultatQuiz getOneById(int id) {
        ResultatQuiz resultat = null;
        try {
            String req = "SELECT * FROM `resulat_quiz`WHERE id_resultat=" + id;
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                String[] reponses = rs.getString(3).split(",");
                List<String> reponseClient = Arrays.asList(reponses);
                resultat = new ResultatQuiz(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), reponseClient);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return resultat;

    }

    public int calculerScore(int idQuiz, int idClient) throws SQLException {
        // récupérer les réponses du client pour le quiz donné
        List<String> reponsesClient = new ArrayList<>();
        String sql1 = "SELECT reponse_client FROM resultat_quiz WHERE id_quiz = ? AND id_client = ?";
        PreparedStatement st1 = cnx.prepareStatement(sql1);
        st1.setInt(1, idQuiz);
        st1.setInt(2, idClient);
        ResultSet resultSet1 = st1.executeQuery();
        while (resultSet1.next()) {
            reponsesClient.add(resultSet1.getString("reponse_client"));
        }

        // récupérer les questions pour le quiz donné
        List<Question> questions;
        ServiceQuestion sq = new ServiceQuestion();
        questions = sq.getQuestionByIdQuiz(idQuiz);

        // calculer le score du client en utilisant l'API Stream
        int score = (int) IntStream.range(0, reponsesClient.size())
                .filter(i -> reponsesClient.get(i).equals(questions.get(i).getReponse_correct()))
                .count();

        return score;

    }
        public List<ResultatQuiz> getOneByCompetitionName(String nom) {
        List<ResultatQuiz> resultat  = new ArrayList<>();
        try {
            String req = "SELECT rq.id_resulat, rq.id_client,rq.id_quiz,rq.score,rq.reponse_client FROM resulat_quiz rq INNER JOIN quiz q"
                    + " ON rq.id_quiz = q.id_quiz INNER JOIN competition c ON q.id_competition = c.id_competition WHERE c.nom = '" +nom+"'";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
               int id = rs.getInt(1);
                int id_client = rs.getInt(2);
                int id_quiz = rs.getInt(3);
                int score = rs.getInt(4);
                String[] reponses = rs.getString(5).split(",");
                List<String> reponseClient = Arrays.asList(reponses);
                ResultatQuiz r = new ResultatQuiz(id_quiz, id_client, id_quiz, score, reponseClient);
               // System.out.println(r);
                resultat.add(r);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return resultat;

    }
    
}
