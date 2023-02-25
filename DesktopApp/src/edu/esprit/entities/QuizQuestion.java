/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.entities;

/**
 *
 * @author admin
 */
public class QuizQuestion {
    private int id;
    private int id_quiz;
    private int id_question;

    public QuizQuestion() {
    }

    public QuizQuestion(int id_quiz, int id_question) {
        this.id_quiz = id_quiz;
        this.id_question = id_question;
    }

    public QuizQuestion(int id, int id_quiz, int id_question) {
        this.id = id;
        this.id_quiz = id_quiz;
        this.id_question = id_question;
    }

    public int getId() {
        return id;
    }


    public int getId_quiz() {
        return id_quiz;
    }

    public void setId_quiz(int id_quiz) {
        this.id_quiz = id_quiz;
    }

    public int getId_question() {
        return id_question;
    }

    public void setId_question(int id_question) {
        this.id_question = id_question;
    }

    @Override
    public String toString() {
        return "Quiz_Qestion{" + "id_quiz=" + id_quiz + ", id_question=" + id_question + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.id;
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
        final QuizQuestion other = (QuizQuestion) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.id_quiz != other.id_quiz) {
            return false;
        }
        if (this.id_question != other.id_question) {
            return false;
        }
        return true;
    }
    
    
    
    
}
