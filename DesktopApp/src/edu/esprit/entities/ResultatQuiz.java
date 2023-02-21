/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.entities;

import java.util.List;

/**
 *
 * @author admin
 */
public class ResultatQuiz {
    
    private int id_resulat;
    private int id_client;
    private int id_quiz;
    private int score;
    private List<String> reponse_client;

    public ResultatQuiz() {
    }

    public ResultatQuiz(int id_client, int id_quiz, int score, List<String> reponse_client) {
        this.id_client = id_client;
        this.id_quiz = id_quiz;
        this.score = score;
        this.reponse_client = reponse_client;
    }

    public ResultatQuiz(int id_resulat, int id_client, int id_quiz, int score, List<String> reponse_client) {
        this.id_resulat = id_resulat;
        this.id_client = id_client;
        this.id_quiz = id_quiz;
        this.score = score;
        this.reponse_client = reponse_client;
    }

    public int getId_resulat() {
        return id_resulat;
    }

    public int getId_client() {
        return id_client;
    }

    public void setId_client(int id_client) {
        this.id_client = id_client;
    }

    public int getId_quiz() {
        return id_quiz;
    }

    public void setId_quiz(int id_quiz) {
        this.id_quiz = id_quiz;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public List<String> getReponse_client() {
        return reponse_client;
    }

    public void setReponse_client(String reponse) {
        reponse_client.add(reponse);
    }

    @Override
    public String toString() {
        return "resultat_quiz{" + "id_client=" + id_client + ", id_quiz=" + id_quiz + ", score=" + score + ", reponse_client=" + reponse_client + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 19 * hash + this.id_resulat;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ResultatQuiz other = (ResultatQuiz) obj;
        if (this.id_resulat != other.id_resulat) {
            return false;
        }
        if (this.id_client != other.id_client) {
            return false;
        }
        if (this.id_quiz != other.id_quiz) {
            return false;
        }
        return true;
    }
    
  
    
}
