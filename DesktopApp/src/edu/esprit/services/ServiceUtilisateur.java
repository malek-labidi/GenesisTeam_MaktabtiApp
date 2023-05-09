/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.esprit.services;

import com.jfoenix.controls.JFXRadioButton;
import edu.esprit.entities.Administrateur;
import edu.esprit.entities.Auteur;
import edu.esprit.entities.Client;
import edu.esprit.entities.Evenement;
import edu.esprit.entities.Role;
import edu.esprit.entities.Utilisateur;
import edu.esprit.gui.AuthentificationController;
import edu.esprit.main.Main;
import edu.esprit.util.DataSource;
import java.io.File;
import java.io.IOException;
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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.InnerShadow;
import javafx.scene.paint.Color;
import org.ini4j.Wini;
import org.mindrot.jbcrypt.BCrypt;



/**
 *
 * @author wassi
 */
public class ServiceUtilisateur implements IService<Utilisateur> {

        Connection cnx = DataSource.getInstance().getCnx();
        public String n, m;
        public String passwordF;
        
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
    public void resiter(Utilisateur u) {
        try {
            String role="Client";
            if (u instanceof Client){
                role="client";
            }else if (u instanceof Administrateur){
            role="Administrateur";
            }else if (role=="Administrateur") {
                System.out.println("Utilisateur created !");
            }
            else{
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
            ste.setString(4, BCrypt.hashpw(u.getMot_de_passe(), BCrypt.gensalt() ));
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
        
        
        //Afficher la liste des clients
 public List<Utilisateur> getclients() {
        List<Utilisateur> list = new ArrayList<>();
        try {
            String req = "Select * from utilisateur WHERE role='Client' ";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                Utilisateur u = new Utilisateur(rs.getInt(1), rs.getString("nom"), rs.getString(3), rs.getString(4), rs.getString("Mot_de_passe"), rs.getInt("num_telephone") ,rs.getString("role") ) {};
                list.add(u);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return list;
    }
    //Afficher la liste des administrateurs
        public List<Utilisateur> getAdmins() {
        List<Utilisateur> list = new ArrayList<>();
        try {
            String req = "Select * from `utilisateur` WHERE role='Administrateur' ";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                Utilisateur u = new Utilisateur(rs.getInt(1), rs.getString("nom"), rs.getString(3), rs.getString(4), rs.getString("Mot_de_passe"), rs.getInt("num_telephone") ,rs.getString("role") ) {};
                list.add(u);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return list;
    }
    //Afficher la liste des auteurs
            public List<Utilisateur> getAuteurs() {
        List<Utilisateur> list = new ArrayList<>();
        try {
            String req = "Select * from `utilisateur` WHERE role='Auteur' ";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                Utilisateur u = new Utilisateur(rs.getInt(1), rs.getString("nom"), rs.getString(3), rs.getString(4), rs.getString("Mot_de_passe"), rs.getInt("num_telephone") ,rs.getString("role") ) {};
                list.add(u);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return list;
    }
        
    //Suppression d'un utilisateurs
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

    /***********************************************Login Void**********************************************************************/
    
    public Utilisateur getuserbyemailandpass(String email, String password) {
    Utilisateur user = null;
    try {
        String req = "SELECT * FROM utilisateur WHERE email = ?";
        PreparedStatement psmt = cnx.prepareStatement(req);
        psmt.setString(1, email);
        ResultSet rs = psmt.executeQuery();
        if (rs.next()) {
            String hashedPassword = rs.getString("mot_de_passe");
            System.out.println(hashedPassword);
            System.out.println(password);
           boolean isMatched = false;
            
            // Check if password matches hashed password with algorithm 2y
            if (hashedPassword.startsWith("$2a$")) {
                isMatched = BCrypt.checkpw(password, hashedPassword);
            }
            
            // Check if password matches hashed password with algorithm 2a
            if (!isMatched && hashedPassword.startsWith("$2y$")) {
                isMatched = BCrypt.checkpw(password, hashedPassword.replace("$2y$", "$2a$"));
            }
            if (isMatched) {
                user = new Utilisateur() {} ;
                user.setId(rs.getInt("id_utilisateur"));
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
    
    /***********************************Create login files****************************************/
        public void createiniFile(String path, String user, String pass) {
        try {
            File file = new File(path);
            if (!file.exists()) {
                file.createNewFile();
            }
            Wini wini = new Wini(new File(path));
            wini.put("Login data", "Email", user);
            wini.put("Login data", "Password", pass);

            wini.store();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
        
            public void readinifile(String path, TextField userid, PasswordField passid, JFXRadioButton remember_me) {
        File file = new File(path);
        if (file.exists()) {
            try {
                Wini wini = new Wini(new File(path));
                String username = wini.get("Login data", "Email");
                String password = wini.get("Login data", "Password");
                if ((username != null && !username.equals("")) && (password != null && !password.equals(""))) {
                    userid.setText(username);
                    passid.setText(password);
                    remember_me.setSelected(true);
                }
            } catch (IOException ex) {
                Logger.getLogger(ServiceUtilisateur.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
        
        /****************************************************************************************************************/
    
    public String sendMail(String mail) throws SQLException {

        String req = "SELECT email FROM utilisateur WHERE email = ?";
        PreparedStatement pst = cnx.prepareStatement(req);
        pst.setString(1, mail);
        ResultSet rs;
        rs = pst.executeQuery();
        while (rs.next()) {
            mail = rs.getString("email");
            //password = rs.getString("password");
        }
        return mail;

    }
         
    //
        public Integer GetuserBytel(String email) {
        Utilisateur user = null;
        int number = 0;
        try {
            String req = "SELECT num_telephone FROM utilisateur WHERE email = ?";
        PreparedStatement pst = cnx.prepareStatement(req);
            pst.setString(1, email);
            ResultSet rs;
            rs = pst.executeQuery();
            if (rs.next()) {
                number = rs.getInt("num_telephone");
                        user = new Utilisateur(
                        rs.getInt("num_telephone")){};
            } 
        } catch (SQLException ex) {
            Logger.getLogger(ServiceUtilisateur.class.getName()).log(Level.SEVERE, null, ex);
        }
        return number;
    }
        
        
            public String sendInfo(String mail) throws SQLException {
        String requete = "SELECT email,mot_de_passe FROM utilisateur WHERE email=? ";
        PreparedStatement pst = cnx.prepareStatement(requete);
        pst.setString(1, sendMail(mail));
        ResultSet rs;
        rs = pst.executeQuery();
        System.out.println(m + "" + n + "hehhe");
        while (rs.next()) {
            mail = rs.getString("email");
            passwordF = rs.getString("mot_de_passe");

        }
        BCrypt.checkpw(mail, passwordF);
        System.out.println(passwordF);
        System.out.println("qqqq");
        return passwordF;

    }
            
        public void readinifile(String path, TextField userid, PasswordField passid) throws IOException {
        File file = new File(path);
        if (file.exists()) {
                Wini wini = new Wini(new File(path));
                String username = wini.get("Login data", "Email");
                String password = wini.get("Login data", "Password");
                if ((username != null && !username.equals("")) && (password != null && !password.equals(""))) {
                    userid.setText(username);
                    passid.setText(password);
                }
        }
    }

        
            public void Deleteinfo(String path, String user, String pass) {
        try {
            File file = new File(path);
            if (!file.exists()) {
                file.createNewFile();
            }
            Wini wini = new Wini(new File(path));
            wini.remove("Login data", "Email");
            wini.remove("Login data", "Password");
            wini.store();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

 
       


}



