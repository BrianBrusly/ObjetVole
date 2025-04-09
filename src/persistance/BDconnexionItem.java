package persistance;

import classesMetiers.Item;

import java.sql.*;
import java.util.List;

public class BDconnexionItem {
    private static final String URL = "jdbc:mysql://localhost:3306/Items";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    // Méthode pour obtenir une connexion à la base de données
    public static Connection getConnexion() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.err.println("Erreur lors de la connexion à la base de données : " + e.getMessage());
            return null;
        }
    }

    // Méthode pour initialiser la table Items
    public static void initialiserDatabase() {
        String sql = "CREATE TABLE IF NOT EXISTS Items (" +
                "numeroSerie VARCHAR(255) PRIMARY KEY, " +
                "type TEXT NOT NULL, " +
                "proprietaire TEXT, " +
                "numeroTelProprio VARCHAR(15), " +
                "estVole BOOLEAN, " +
                "dateVol DATE" +
                ")";
        try (Connection conn = getConnexion();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table Items initialisée avec succès !");
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'initialisation de la table Items : " + e.getMessage());
        }
    }

    // Méthode pour ajouter un objet dans la base
    public static void ajouterItem(Item item) {
        String sql = "INSERT INTO Items (numeroSerie, type, proprietaire, numeroTelProprio, estVole, dateVol) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, item.getNumeroSerie());
            pstmt.setString(2, item.getType());
            pstmt.setString(3, item.getProprietaire());
            pstmt.setString(4, item.getNumeroTelProprio());
            pstmt.setBoolean(5, item.isEstVole());
            pstmt.setDate(6, item.getDateVol() != null ? Date.valueOf(item.getDateVol()) : null);
            pstmt.executeUpdate();
            System.out.println("Item ajouté avec succès !");
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout de l'objet : " + e.getMessage());
        }
    }

    // Méthode pour rechercher un objet par numéro de série
    public static Item rechercherItemParNumeroSerie(String numeroSerie) {
        String sql = "SELECT * FROM Items WHERE numeroSerie = ?";
        try (Connection conn = getConnexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, numeroSerie);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Item(
                        rs.getString("numeroSerie"),
                        rs.getString("type"),
                        rs.getString("proprietaire"),
                        rs.getString("numeroTelProprio"),
                        rs.getBoolean("estVole"),
                        rs.getDate("dateVol") != null ? rs.getDate("dateVol").toLocalDate() : null
                );
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche de l'objet : " + e.getMessage());
        }
        return null;
    }

    // Méthode pour mettre à jour un objet
    public static void mettreAjourItem(Item item) {
        String sql = "UPDATE Items SET type = ?, proprietaire = ?, numeroTelProprio = ?, estVole = ?, dateVol = ? WHERE numeroSerie = ?";
        try (Connection conn = getConnexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, item.getType());
            pstmt.setString(2, item.getProprietaire());
            pstmt.setString(3, item.getNumeroTelProprio());
            pstmt.setBoolean(4, item.isEstVole());
            pstmt.setDate(5, item.getDateVol() != null ? Date.valueOf(item.getDateVol()) : null);
            pstmt.setString(6, item.getNumeroSerie());
            pstmt.executeUpdate();
            System.out.println("Item mis à jour avec succès !");
        } catch (SQLException e) {
            System.err.println("Erreur lors de la mise à jour de l'objet : " + e.getMessage());
        }
    }

    // Méthode pour supprimer un objet par son numéro de série
    public static void supprimerItem(String numeroSerie) {
        String sql = "DELETE FROM Items WHERE numeroSerie = ?";
        try (Connection conn = getConnexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, numeroSerie);
            pstmt.executeUpdate();
            System.out.println("Item supprimé avec succès !");
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression de l'objet : " + e.getMessage());
        }
    }

    // Méthode pour afficher tous les objets
    public static List<Item> getAllItems() {
        String sql = "SELECT * FROM Items";
        try (Connection conn = getConnexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                System.out.println("Numéro de Série: " + rs.getString("numeroSerie"));
                System.out.println("Type: " + rs.getString("type"));
                System.out.println("Propriétaire: " + rs.getString("proprietaire"));
                System.out.println("Numéro Tel Propriétaire: " + rs.getString("numeroTelProprio"));
                System.out.println("Statut: " + (rs.getBoolean("estVole") ? "Volé" : "Non volé"));
                System.out.println("Date de Vol: " + rs.getDate("dateVol"));
                System.out.println("---------------------------");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'affichage des objets : " + e.getMessage());
        }
        return null;
    }
}
