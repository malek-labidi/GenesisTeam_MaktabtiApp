/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.esprit.services;

import edu.esprit.entities.Administrateur;
import edu.esprit.entities.Auteur;
import edu.esprit.entities.Client;
import edu.esprit.entities.Role;
import edu.esprit.entities.Utilisateur;
import edu.esprit.main.Main;
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

/**
 *
 * @author wassi
 */
public class ServiceUtilisateur implements IService<Utilisateur> {
    
        Connection cnx = DataSource.getInstance().getCnx();
        
         @Override
    public void ajouter(Utilisateur u) {
        try {
            String role="Client";
            if (u instanceof Client){
                role="client";
            }else if (u instanceof Administrateur){
            role="Administrateur";
            }else{
            role="Auteur";        
            }
            String req = "INSERT INTO `utilisateur`( `nom`, `prenom`, `email`, `mot_de_passe`, `num_telephone` , `role`)  VALUES (?,?,?,?,?,?)";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, u.getNom());
            ps.setString(2, u.getPrenom());
            ps.setString(3, u.getEmail());
            ps.setString(4, u.getMot_de_passe());
            ps.setInt(5, u.getnum_telephone());
            ps.setString(6, u.getRole().toString());
            ps.executeUpdate();
            System.out.println("Utilisateur created !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

  

    @Override
    public void delete(int id) {
        try {
            String req = "DELETE FROM `utilisateur` WHERE id_utilisateur  = " + id;
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("Utilisateur deleted !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void modifier(Utilisateur u) {
        try {
            String req = "UPDATE `utilisateur` SET `nom` = '" + u.getNom() + "', `prenom` = '" + u.getPrenom() + "', `email` = '" + u.getEmail()+ "', `mot_de_passe` = '" + u.getMot_de_passe()+ "', `num_telephone` = '" + u.getnum_telephone()+ "', `role` = '" + u.getRole()+  "' WHERE `personne`.`id` = " + u.getId();
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("Utilisateur updated !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<Utilisateur> getAll() {
        List<Utilisateur> list = new ArrayList<>();
        try {
            String req = "Select * from utilisateur";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                if(rs.getString("Role").equals("Client")){
                  Utilisateur u=new Client();  
                }else if(rs.getString("Role").equals("Administrateur")){
                  Utilisateur u=new Administrateur();  
                }
                else{
                  Utilisateur u=new Auteur();  
                }
                Utilisateur u = new Utilisateur(rs.getInt(1), rs.getString("nom"), rs.getString(3), rs.getString(4), rs.getString("Mot_de_passe"), rs.getInt("num_telephone") ,rs.getString("role") ) {};
                list.add(u);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return list;
    }

    @Override
    public Utilisateur getOneById(int id) {
        Utilisateur u = null;
        try {
            String req = "Select * from utilisateur where  id_utilisateur ="+id;
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                u = new Utilisateur(rs.getInt(1), rs.getString("nom"), rs.getString(3), rs.getString(4), rs.getString("Mot_de_passe"), rs.getInt("num_telephone"),rs.getString("role")) {};
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return u;
    }

}


