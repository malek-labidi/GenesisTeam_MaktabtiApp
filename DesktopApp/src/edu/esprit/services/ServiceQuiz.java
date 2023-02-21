/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.services;

import edu.esprit.entities.Competition;
import edu.esprit.entities.Livre;
import edu.esprit.entities.Question;
import edu.esprit.entities.Quiz;
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
import java.util.stream.Stream;

/**
 *
 * @author admin
 */
public class ServiceQuiz implements IService<Quiz> {

    Connection cnx = DataSource.getInstance().getCnx();

    @Override
    public void ajouter(Quiz t) {
        Competition verifcomp = null;
        Livre veriflivre = null;

        ServiceCompetition sc = new ServiceCompetition();
        verifcomp = sc.getOneById(t.getId_competition());
        ServiceLivre sl = new ServiceLivre();
        veriflivre = sl.getOneById(t.getId_livre());
        if ((verifcomp != null) && (veriflivre != null)) {
            try {
                String req = "INSERT INTO `quiz`(`id_livre`, `id_competition`) VALUES (?,?)";
                PreparedStatement ps = cnx.prepareStatement(req);
                ps.setInt(1, t.getId_livre());
                ps.setInt(2, t.getId_competition());
                ps.executeUpdate();
                System.out.println("Quiz Added !");
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        } else if (verifcomp == null) {
            System.out.println("Quiz does not exist.");
        } else {
            System.out.println("Competition does not exist.");
        }

    }

    @Override
    public void modifier(Quiz t) {

        Competition verifcomp = null;
        Livre veriflivre = null;

        ServiceCompetition sc = new ServiceCompetition();
        verifcomp = sc.getOneById(t.getId_competition());
        ServiceLivre sl = new ServiceLivre();
        veriflivre = sl.getOneById(t.getId_livre());
        if ((verifcomp != null) && (veriflivre != null)) {
            try {
                String req = "UPDATE `quiz` SET `id_livre`=?,`id_competition`=? WHERE id_quiz= ?";
                PreparedStatement ps = cnx.prepareStatement(req);
                ps.setInt(1, t.getId_livre());
                ps.setInt(2, t.getId_competition());
                ps.setInt(3, t.getId_quiz());
                ps.executeUpdate();
                System.out.println("quiz Updated !");
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        } else if (verifcomp == null) {
            System.out.println("Quiz does not exist.");
        } else {
            System.out.println("Competition does not exist.");
        }
    }

    @Override
    public void delete(int id) {
        try {
            String req = "DELETE FROM `quiz` WHERE id_quiz=?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("quiz Deleted !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<Quiz> getAll() {
        List<Quiz> result = new ArrayList<>();
        try {
            String req = "SELECT quiz.id_quiz,question.id_question,question.choix1 ,question.choix2,question.choix3,question.reponse_correct  FROM `quiz`"
                    + "INNER JOIN question ON quiz.id_quiz = question.quiz_id";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);

            while (rs.next()) {
                int quizId = rs.getInt(1);
                int id_question = rs.getInt(2);
                String choix1 = rs.getString(3);
                String choix2 = rs.getString(4);
                String choix3 = rs.getString(5);
                String reponse_correct = rs.getString(6);
                Quiz quiz = result.stream().filter(q -> q.getId_quiz() == quizId).findFirst().get();
                quiz.ajouterQuestion(new Question(id_question, choix1, choix2, choix3, reponse_correct));
                if (!result.contains(quiz)) {
                    result.add(quiz);
                }
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return result;
    }

    @Override
    public Quiz getOneById(int id) {
        Quiz result = null;
        try {
            String req = "SELECT quiz.id_quiz,question.id_question,question.choix1 ,question.choix2,question.choix3,question.reponse_correct  FROM `quiz`"
                    + "INNER JOIN question ON quiz.id_quiz = " + id;
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);

            while (rs.next()) {
                int quizId = rs.getInt(1);
                int id_question = rs.getInt(2);
                String choix1 = rs.getString(3);
                String choix2 = rs.getString(4);
                String choix3 = rs.getString(5);
                String reponse_correct = rs.getString(6);
                result = new Quiz(quizId, id_question, null);
                result.ajouterQuestion(new Question(id_question, choix1, choix2, choix3, reponse_correct));
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return result;
    }

    public Stream<Question> trierQuestion(int id) {

        return this.getOneById(id).trierQuestionParId();
    }

    public void supprimerQuestion(int id, Question q) {
        try {
            String req ="DELETE FROM `quiz_question` WHERE id_quiz= ? AND id_question = ?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ps.setInt(2, q.getId_question());
            ps.executeUpdate();
            this.getOneById(id).supprimerQuestion(q);
        } catch (SQLException ex) {
            Logger.getLogger(ServiceQuiz.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void ajouterQuestion(int id, Question q) {
        ServiceQuestion sq= new ServiceQuestion();
        sq.ajouter(q);
        this.getOneById(id).ajouterQuestion(q);
    }

}
