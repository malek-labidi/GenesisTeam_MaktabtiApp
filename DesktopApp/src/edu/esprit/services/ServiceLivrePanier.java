/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.services;

import edu.esprit.entities.Livre;
import edu.esprit.entities.Panier;
import edu.esprit.entities.PanierLivre;
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
 * @author abdelazizlahmar
 */
public class ServiceLivrePanier implements IService <PanierLivre> {

    Connection cnx = DataSource.getInstance().getCnx();


    @Override
    public void ajouter(PanierLivre t) throws SQLException {


        try {
            String req = "INSERT INTO `panierlivre`( `id_livre`, `id_panier`, `id_client`) VALUES (?,?,?)";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, t.getId_livre());
            ps.setInt(2, t.getId_client());
            ps.executeUpdate();
            System.out.println("Commande created ");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void modifier(PanierLivre t) {
        
    }

    @Override
    public void delete(int id) {
        try {
            String req = "DELETE FROM `panierlivre` WHERE id_livre= ?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("livre deleted");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

        }
    }

    @Override
    public List<PanierLivre> getAll() {
    List<PanierLivre> result = new ArrayList<>();

    try {
        String req = "SELECT * FROM `panierlivre`";
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(req);
        while (rs.next()) {
            int id_livre = rs.getInt(1);
            int id_panier = rs.getInt(2);
            int quantite = rs.getInt(3);

            PanierLivre c = new PanierLivre(id_livre, id_panier, quantite);

            result.add(c);
        }

        // loop through the result list and print each element
        for (PanierLivre c : result) {
            System.out.println(c);
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    return result;
}

    @Override
    public PanierLivre getOneById(int id) {

        PanierLivre result = null;
        try {
            String req = "SELECT * FROM `panier-livre` WHERE id_=" + id;
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {

                result = new PanierLivre(rs.getInt(1), rs.getInt(2), rs.getInt(3));

            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return result;
    }

   /* public void ajouterProduitAuPanier(int idPanier, int idLivre, int quantite)  {
        
        
        try {
        PreparedStatement pstmt = cnx.prepareStatement("SELECT quantite FROM panierlivre WHERE id_panier = ? AND id_livre = ?");
        pstmt.setInt(1, idPanier);
        pstmt.setInt(2, idLivre);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            int quantiteActuelle = rs.getInt("quantite");
            quantite += quantiteActuelle;
            PreparedStatement pstmtUpdate = cnx.prepareStatement("UPDATE panierlivre SET quantite = ? WHERE id_panier = ? AND id_livre = ?");
            pstmtUpdate.setInt(1, quantite);
            pstmtUpdate.setInt(2, idPanier);
            pstmtUpdate.setInt(3, idLivre);
            pstmtUpdate.executeUpdate();
        } else {
            PreparedStatement pstmtInsert = cnx.prepareStatement("INSERT INTO panierlivre (id_panier, id_livre, quantite) VALUES (?, ?, ?)");
            pstmtInsert.setInt(1, idPanier);
            pstmtInsert.setInt(2, idLivre);
            pstmtInsert.setInt(3, quantite);
            pstmtInsert.executeUpdate();
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    }
    */

    public void ajouterLivrePanier(int id_livre,int id_client) {
    try {

        PreparedStatement ps1 = cnx.prepareStatement("SELECT * FROM panier WHERE id_client = ?");
        ps1.setInt(1, id_client);
        ResultSet rs = ps1.executeQuery();

        int id_panier;
        if (rs.next()) {
            id_panier = rs.getInt("id_panier");
        } else {
            PreparedStatement ps2 = cnx.prepareStatement("INSERT INTO panier (id_client) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
            ps2.setInt(1, id_client);
            ps2.executeUpdate();
            rs = ps2.getGeneratedKeys();
            rs.next();
            id_panier = rs.getInt(1);
        }

        PreparedStatement ps3 = cnx.prepareStatement("SELECT * FROM panierlivre WHERE id_panier = ? AND id_livre = ?");
        ps3.setInt(1, id_panier);
        ps3.setInt(2, id_livre);
        rs = ps3.executeQuery();

        if (rs.next()) {
            PreparedStatement ps4 = cnx.prepareStatement("UPDATE panierlivre SET quantite = quantite + 1 WHERE id_panier = ? AND id_livre = ?");
            ps4.setInt(1, id_panier);
            ps4.setInt(2, id_livre);
            ps4.executeUpdate();
        } else {
            PreparedStatement ps5 = cnx.prepareStatement("INSERT INTO panierlivre (id_panier, id_livre, quantite) VALUES (?, ?, ?)");
            ps5.setInt(1, id_panier);
            ps5.setInt(2, id_livre);
            ps5.setInt(3, 1);
            ps5.executeUpdate();
        }


    } catch (SQLException e) {
       System.out.println(e.getMessage());
    }
}

 public void supprimerLivrePanier(int id_livre) {
    try {
        // Récupération de la quantité actuelle du livre dans le panier
        String selectQuery = "SELECT quantite FROM panierlivre WHERE id_livre = ?";
        PreparedStatement selectStmt = cnx.prepareStatement(selectQuery);
        selectStmt.setInt(1, id_livre);
        ResultSet rs = selectStmt.executeQuery();
        int quantiteActuelle = 0;
        if (rs.next()) {
            quantiteActuelle = rs.getInt("quantite");
        }

        if (quantiteActuelle == 1) {
            // Si la quantité est égale à 1, on supprime complètement la ligne correspondante dans la table panier
            String deleteQuery = "DELETE FROM panierlivre WHERE id_livre = ?";
            PreparedStatement deleteStmt = cnx.prepareStatement(deleteQuery);
            deleteStmt.setInt(1, id_livre);
            deleteStmt.executeUpdate();
        } else if (quantiteActuelle > 1) {
            // Si la quantité est supérieure à 1, on décrémente la quantité dans la table panier
            String updateQuery = "UPDATE panierlivre SET quantite = quantite - 1 WHERE id_livre = ?";
            PreparedStatement updateStmt = cnx.prepareStatement(updateQuery);
            updateStmt.setInt(1, id_livre);
            updateStmt.executeUpdate();
        }

        System.out.println("Livre supprimé du panier avec succès !");

    } catch (SQLException ex) {
        System.out.println("Erreur lors de la suppression du livre du panier: " + ex.getMessage());
    }
}

 /*public void afficherLivresDansPanier(int id_panier) {
    try {
        PreparedStatement ps = cnx.prepareStatement("SELECT livre.titre, livre.prix FROM panierlivre JOIN livre ON panierlivre.id_livre = livre.id_livre WHERE panierlivre.id_panier = ?");
        ps.setInt(1, id_panier);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            String titre = rs.getString("titre");
            float prix = rs.getFloat("prix");
            System.out.println("Titre : " + titre + " - Prix : " + prix);
        }
    } catch (SQLException ex) {
        System.out.println("Erreur lors de la récupération des livres dans le panier : " + ex.getMessage());
    }
}
*/
public List<Livre> afficherLivresDansPanier(int id_panier) {
    List<Livre> var = new ArrayList();
    try {
        PreparedStatement ps = cnx.prepareStatement("SELECT livre.titre, livre.prix FROM panierlivre JOIN livre ON panierlivre.id_livre = livre.id_livre WHERE panierlivre.id_panier = ?");
        ps.setInt(1, id_panier);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            String titre = rs.getString("titre");
            float prix = rs.getFloat("prix");
             Livre l = new Livre(titre,prix);
             var.add(l);
        }

    } catch (SQLException ex) {
        System.out.println("Erreur lors de la récupération des livres dans le panier : " + ex.getMessage());
    }
    return var;
}
public float calculTotalPrix(int id_panier) {
        List<Panier> var = new ArrayList();
        float totalPrix = 0.0f;
        try {
            String sql = "SELECT SUM(l.prix * pl.quantite) AS prixTotal \n"
                    + "FROM panierlivre pl \n"
                    + "JOIN livre l ON pl.id_livre = l.id_livre \n"
                    + "WHERE pl.id_panier = ?";
            PreparedStatement stmt = cnx.prepareStatement(sql);
            stmt.setInt(1, id_panier);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                totalPrix += rs.getFloat("prixTotal");

            }
        } catch (SQLException e) {
            System.err.println("Erreur lors du calcul du total de prix : " + e.getMessage());
        }

        return totalPrix;

    }

public void updatePanierLivre(int id_livre, int id_client, int quantite) {
    try {

        // Préparer la requête SQL pour mettre à jour la quantité du livre dans le panier
        PreparedStatement pstmt = cnx.prepareStatement("UPDATE livrepanier SET quantite=? WHERE id_livre=? AND id_client=?");

        // Définir les paramètres de la requête
        pstmt.setInt(1, quantite);
        pstmt.setInt(2, id_livre);
        pstmt.setInt(3, id_client);

        // Exécuter la requête SQL
        pstmt.executeUpdate();

        // Fermer la connexion à la base de données
        cnx.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}



}

    

