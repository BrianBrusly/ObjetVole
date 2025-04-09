package persistance;

import classesMetiers.DeclarationVol;
import classesMetiers.Item;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BDconnexionDeclarationVol {
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

    // Méthode pour initialiser la table DeclarationVol
    public static void initialiserDatabase() {
        String sql = "CREATE TABLE IF NOT EXISTS DeclarationVol (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "numeroSerie VARCHAR(255) NOT NULL, " +
                "dateDeclaration DATE NOT NULL, " +
                "lieuVol TEXT NOT NULL, " +
                "description TEXT, " +
                "FOREIGN KEY (numeroSerie) REFERENCES Items(numeroSerie)" +
                ")";
        try (Connection conn = getConnexion();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table DeclarationVol initialisée avec succès !");
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'initialisation de la table DeclarationVol : " + e.getMessage());
        }
    }

    // Méthode pour ajouter une déclaration de vol
    public static void ajouterDeclarationVol(DeclarationVol declaration) {
        String sql = "INSERT INTO DeclarationVol (numeroSerie, dateDeclaration, lieuVol, description) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, declaration.getObjet().getNumeroSerie());
            pstmt.setDate(2, Date.valueOf(declaration.getDateDeclaration()));
            pstmt.setString(3, declaration.getLieuVol());
            pstmt.setString(4, declaration.getDescription());
            pstmt.executeUpdate();
            System.out.println("Déclaration de vol ajoutée avec succès !");
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout de la déclaration de vol : " + e.getMessage());
        }
    }

    // Méthode pour mettre à jour une déclaration de vol
    public static boolean mettreAjourDeclarationVol(DeclarationVol declaration) {
        String sql = "UPDATE DeclarationVol SET dateDeclaration = ?, lieuVol = ?, description = ? WHERE id = ?";
        try (Connection conn = getConnexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDate(1, Date.valueOf(declaration.getDateDeclaration()));
            pstmt.setString(2, declaration.getLieuVol());
            pstmt.setString(3, declaration.getDescription());
            pstmt.setLong(4, declaration.getId());
            pstmt.executeUpdate();
            System.out.println("Déclaration de vol mise à jour avec succès !");
            return true;
        } catch (SQLException e) {
            System.err.println("Erreur lors de la mise à jour de la déclaration de vol : " + e.getMessage());
            return false;
        }
    }

    // Méthode pour rechercher une déclaration par ID
    public static DeclarationVol rechercherDeclarationParId(int id) {
        String sql = "SELECT * FROM DeclarationVol WHERE id = ?";
        try (Connection conn = getConnexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Item item = BDconnexionItem.rechercherItemParNumeroSerie(rs.getString("numeroSerie"));
                return new DeclarationVol(
                        rs.getLong("id"),
                        item,
                        rs.getDate("dateDeclaration").toLocalDate(),
                        rs.getString("lieuVol"),
                        rs.getString("description")
                );
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche de la déclaration de vol : " + e.getMessage());
        }
        return null; // Si aucune déclaration n'est trouvée
    }

    // Méthode pour afficher toutes les déclarations de vol
    public static List<DeclarationVol> getToutesDeclarationsVol() {
        List<DeclarationVol> declarations = new ArrayList<>();
        String sql = "SELECT * FROM DeclarationVol";
        try (Connection conn = getConnexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Item item = BDconnexionItem.rechercherItemParNumeroSerie(rs.getString("numeroSerie"));
                DeclarationVol declaration = new DeclarationVol(
                        rs.getLong("id"),
                        item,
                        rs.getDate("dateDeclaration").toLocalDate(),
                        rs.getString("lieuVol"),
                        rs.getString("description")
                );
                declarations.add(declaration);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération de toutes les déclarations de vol : " + e.getMessage());
        }
        return declarations;
    }

    // Méthode pour supprimer une déclaration par ID
    public static void supprimerDeclarationParId(int id) {
        String sql = "DELETE FROM DeclarationVol WHERE id = ?";
        try (Connection conn = getConnexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("Déclaration de vol supprimée avec succès !");
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression de la déclaration de vol : " + e.getMessage());
        }
    }
}
