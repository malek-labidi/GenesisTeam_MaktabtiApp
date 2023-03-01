/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.entities;

import java.util.Objects;

/**
 *
 * @author admin
 */
public class Question {
    private int id_question;
    private int id_quiz;
    private String question;
    private String choix1;
    private String choix2;
    private String choix3;
    private String reponse_correct;

    public Question() {
    }

    public Question(int id_quiz, String question, String choix1, String choix2, String choix3, String reponse_correct) {
        this.id_quiz = id_quiz;
        this.question = question;
        this.choix1 = choix1;
        this.choix2 = choix2;
        this.choix3 = choix3;
        this.reponse_correct = reponse_correct;
    }

    public Question(int id_question, int id_quiz, String question, String choix1, String choix2, String choix3, String reponse_correct) {
        this.id_question = id_question;
        this.id_quiz = id_quiz;
        this.question = question;
        this.choix1 = choix1;
        this.choix2 = choix2;
        this.choix3 = choix3;
        this.reponse_correct = reponse_correct;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getId_question() {
        return id_question;
    }

    public int getId_quiz() {
        return id_quiz;
    }

    public void setId_quiz(int id_quiz) {
        this.id_quiz = id_quiz;
    }

    public String getChoix1() {
        return choix1;
    }

    public void setChoix1(String choix1) {
        this.choix1 = choix1;
    }

    public String getChoix2() {
        return choix2;
    }

    public void setChoix2(String choix2) {
        this.choix2 = choix2;
    }

    public String getChoix3() {
        return choix3;
    }

    public void setChoix3(String choix3) {
        this.choix3 = choix3;
    }

    public String getReponse_correct() {
        return reponse_correct;
    }

    public void setReponse_correct(String reponse_correct) {
        this.reponse_correct = reponse_correct;
    }

    @Override
    public String toString() {
        return "Question{" + "question=" + question + ", choix1=" + choix1 + ", choix2=" + choix2 + ", choix3=" + choix3 + ", reponse_correct=" + reponse_correct + '}';
    }

  

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + this.id_question;
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
        final Question other = (Question) obj;
        if (this.id_question != other.id_question) {
            return false;
        }
        if (!Objects.equals(this.choix1, other.choix1)) {
            return false;
        }
        if (!Objects.equals(this.choix2, other.choix2)) {
            return false;
        }
        if (!Objects.equals(this.choix3, other.choix3)) {
            return false;
        }
        if (!Objects.equals(this.reponse_correct, other.reponse_correct)) {
            return false;
        }
        return true;
    }
     public static boolean verifString(String s ){
        
    return s.isEmpty();
    }
}
