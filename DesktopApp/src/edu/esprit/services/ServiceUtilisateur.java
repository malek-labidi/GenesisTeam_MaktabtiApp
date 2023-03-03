/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.esprit.services;

import edu.esprit.entities.Administrateur;
import edu.esprit.entities.Auteur;
import edu.esprit.entities.Client;
import edu.esprit.entities.Evenement;
import edu.esprit.entities.Role;
import edu.esprit.entities.Utilisateur;
import edu.esprit.gui.AuthentificationController;
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
import java.util.stream.Collectors;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.effect.InnerShadow;
import javafx.scene.paint.Color;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author wassi
 */
public class ServiceUtilisateur implements IService<Utilisateur> {

        Connection cnx = DataSource.getInstance().getCnx();

        
        //Fonction d'ajout d'un utilisateur
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
            ps.setString(4, BCrypt.hashpw(u.getMot_de_passe(), BCrypt.gensalt() ));
            ps.setInt(5, u.getnum_telephone());
            ps.setString(6, u.getRole().toString());
            ps.executeUpdate();
            System.out.println("Utilisateur created !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
 }
    
    //Registration code
     public boolean register(Utilisateur u) {
        Connection cnx = DataSource.getInstance().getCnx();
        if (!Utilisateur.verifString(u.getNom()) && !Utilisateur.verifString(u.getPrenom())&& !Utilisateur.verifString(u.getEmail() ) && !Utilisateur.verifString(u.getMot_de_passe()) && !Utilisateur.verifemail(u.getEmail()) && !Utilisateur.verifpassword(u.getMot_de_passe())&& getOneByemailutilisateur(u.getEmail()) == null ) {
        try {
            String req = "INSERT INTO `utilisateur`( `nom`, `prenom`, `email`, `mot_de_passe`, `num_telephone` , `role`)  VALUES (?,?,?,?,?,?)";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, u.getNom());
            ps.setString(2, u.getPrenom());
            ps.setString(3, u.getEmail());
            ps.setString(4, BCrypt.hashpw(u.getMot_de_passe(), BCrypt.gensalt() ));
            ps.setInt(5, u.getnum_telephone());
            ps.setString(6, u.getRole().toString());



            if (ps.executeUpdate() > 0) {
                System.out.println("You have registered successfully!");
                return true;
            } else {
                System.out.println("Something went wrong!");
                return false;
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }else{ 
            System.out.println("Columns should not be empty!");
     }      return false;
}

  
        //Fonction de supression d'un utilisateur

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
    
    //Fonction de modification d'un utilisateur

    @Override
    public void modifier(Utilisateur u) {
        try {
            String req = "UPDATE `utilisateur` SET nom = '" + u.getNom() + "', prenom=" + u.getPrenom() + "', email = '" + u.getEmail()+ "', `mot_de_passe` = '" + u.getMot_de_passe()+ "', `num_telephone` = '" + u.getnum_telephone()+ "', `role` = '" + u.getRole()+  "' WHERE id_utilisateur=" + u.getId();
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("Utilisateur updated !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }
    public void modifier2(int id,Utilisateur u) {
    String sql = "update utilisateur set nom=?,prenom=?,email=?,mot_de_passe=?,num_telephone=?,role=?   where id_utilisateur="+id;
        try {
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.setString(1, u.getNom());
            ste.setString(2, u.getPrenom());
            ste.setString(3, u.getEmail());
            ste.setString(4, u.getMot_de_passe());
            ste.setInt(5, u.getnum_telephone());
            ste.setString(6, u.getRole());



            ste.executeUpdate();
            System.out.println("********************** MODIFIED ****************************************");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    
        public void modifier3(Utilisateur u) {
    String sql = "update utilisateur set nom=?,prenom=?,email=?,mot_de_passe=?,num_telephone=?,role=?   where id_utilisateur="+u.getId();
        try {
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.setString(1, u.getNom());
            ste.setString(2, u.getPrenom());
            ste.setString(3, u.getEmail());
            ste.setString(4, u.getMot_de_passe());
            ste.setInt(5, u.getnum_telephone());
            ste.setString(6, u.getRole());



            ste.executeUpdate();
            System.out.println("********************** MODIFIED ****************************************");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    
    
    
          public void modifierutilisateur(String name,Utilisateur u) {
    String sql = "update users set phone=?,email=?  where username=? ";
        try {
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.setString(1, u.getNom());
            ste.setString(2, u.getPrenom());
            ste.setString(3,u.getEmail());
            ste.setString(4,u.getMot_de_passe());
            ste.setInt(5,u.getnum_telephone());
            ste.setString(6,u.getRole());

            ste.executeUpdate();
            System.out.println("********************** MODIFIED ****************************************");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    

        //Fonction d'affichage de tous les utilisateurs
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

    //Let email be unique
    public Utilisateur getOneByemailutilisateur(String email) {
        Utilisateur result = null;
        try {
            String req = "SELECT * FROM utilisateur WHERE email = " + email;
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {

                result = new Utilisateur(rs.getInt(1), rs.getString("nom"), rs.getString(3), rs.getString(4), rs.getString("Mot_de_passe"), rs.getInt("num_telephone"),rs.getString("role")) {};

            }

        } catch (SQLException ex) {
            System.out.println("Les adresses emails ne doivent etre en doublons");
        }
        return result;
    }

    //Filter BY name of user
        public static List<Utilisateur> filterByName(List<Utilisateur> evenements, String nom) {
    List<Utilisateur> filteredList = new ArrayList<>();
    try {
        filteredList = evenements.stream()
                .filter(e -> e.getNom().toLowerCase().startsWith(nom.toLowerCase()))
                .collect(Collectors.toList());
    } catch (Exception e) {
        // handle the exception
        System.out.println("An error occurred while filtering events by location: " + e.getMessage());
    }
    return filteredList;
}
    //Get one by id
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
    
    
    // Avoir un utilisateur selon son id
      public Utilisateur GetUserByid(int id) {
        Utilisateur user = null;
        String pass = "";
        try {
            String requete = "Select id,role from utilisateur where id = ?";
            PreparedStatement pst = DataSource.getInstance().getCnx().prepareStatement(requete);

            pst.setInt(1, id);
            ResultSet rs;
            rs = pst.executeQuery();


            //decrypt pass :
            //pass = decrypt(pass);

            requete = "SELECT id_utilisateur, nom, prenom, email, mot_de_passe, num_telephone, role FROM utilisateur WHERE id_utilisateur=?";
            pst = DataSource.getInstance().getCnx().prepareStatement(requete);
            pst.setInt(1, id);
            rs = pst.executeQuery();

            while (rs.next()) {

                user = new Utilisateur(
                        id,
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getInt("telephone"),
                        rs.getString("role")
                ){};
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
        return user;
    }
    
      // Avoir un utilisateur selon son role
        public Utilisateur getOneByrole(String role) {
        Utilisateur u = null;
        try {
            String req = "Select * from `utilisateur` where  role ="+role;
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
        
    public void supprimerUtilisateur(Utilisateur u) {
            String req = "DELETE FROM `utilisateur` WHERE id_utilisateur  = ?"  ;
        try {
            PreparedStatement ste = cnx.prepareStatement(req);
            ste.setInt(1, u.getId());
            ste.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
        
        
/*public Utilisateur getuserbyemailandpass(String email, String password) {
    Utilisateur user = null;
    String pass = "";
    
    try {
        String req = "SELECT * FROM utilisateur WHERE email = ? AND mot_de_passe = ?";
        PreparedStatement psmt = cnx.prepareStatement(req);
        psmt.setString(1, email);
        psmt.setString(2, password);
        ResultSet rs = psmt.executeQuery();
        if (rs.next()) {
            user = new Utilisateur() {} ;
            user.setNom(rs.getString("nom"));
            user.setPrenom(rs.getString("prenom"));
            user.setEmail(rs.getString("email"));
            user.setMot_de_passe(rs.getString("mot_de_passe"));
            user.setnum_telephone(rs.getInt("num_telephone"));
            user.setRole(rs.getString("role"));
        }  
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return user;
}*/

    public Utilisateur getuserbyemailandpass(String email, String password) {
    Utilisateur user = null;
    try {
        String req = "SELECT * FROM utilisateur WHERE email = ?";
        PreparedStatement psmt = cnx.prepareStatement(req);
        psmt.setString(1, email);
        ResultSet rs = psmt.executeQuery();
        if (rs.next()) {
            String hashedPassword = rs.getString("mot_de_passe");
            if (BCrypt.checkpw(password, hashedPassword)) {
                user = new Utilisateur() {} ;
                user.setNom(rs.getString("nom"));
                user.setPrenom(rs.getString("prenom"));
                user.setEmail(rs.getString("email"));
                user.setMot_de_passe(hashedPassword);
                user.setnum_telephone(rs.getInt("num_telephone"));
                user.setRole(rs.getString("role"));
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return user;
}
         
 
       


}



