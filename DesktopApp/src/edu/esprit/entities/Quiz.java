/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 *
 * @author admin
 */
public class Quiz {

    private int id_quiz;
    private int id_livre;
    private int id_competition;
    private List<Question> listequestions;

    public Quiz() {
    }

    public Quiz(int id_livre, int id_competition, List<Question> listequestions) {
        this.id_livre = id_livre;
        this.id_competition = id_competition;
        this.listequestions = listequestions;
    }

    public Quiz(int id_quiz, int id_livre, int id_competition, List<Question> listequestions) {
        this.id_quiz = id_quiz;
        this.id_livre = id_livre;
        this.id_competition = id_competition;
        this.listequestions = listequestions;
    }

    public int getId_quiz() {
        return id_quiz;
    }

    public int getId_livre() {
        return id_livre;
    }

    public void setId_livre(int id_livre) {
        this.id_livre = id_livre;
    }

    public int getId_competition() {
        return id_competition;
    }

    public void setId_competition(int id_competition) {
        this.id_competition = id_competition;
    }

    public void setQuestions() {
        this.listequestions = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Quiz{" + "id_quiz=" + id_quiz + ", id_livre=" + id_livre + ", id_competition=" + id_competition + ", questions=" + this.getQuestions().toString() + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + this.id_quiz;
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
        final Quiz other = (Quiz) obj;
        if (this.id_quiz != other.id_quiz) {
            return false;
        }
        if (this.id_livre != other.id_livre) {
            return false;
        }
        if (this.id_competition != other.id_competition) {
            return false;
        }
        return true;
    }

    public void ajouterQuestion(Question p) {
        listequestions.add(p);
    }

    public List<Question> getQuestions() {

        return listequestions;

    }

    public void supprimerQuestion(Question p) {
        listequestions.remove(p);
    }

    public Stream<Question> trierQuestionParId() {

        return listequestions.stream().sorted((t1, t2) -> t1.getId_question() - t2.getId_question());
    }

    public void afficherQuestions() {
        listequestions.stream().forEach(e -> System.out.println(e));

    }
    
   

}
