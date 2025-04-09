package persistance;

import classesMetiers.Utilisateur;

import java.security.MessageDigest;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BDconnexionUser {
    private static final String URL = "jdbc:mysql://localhost:3306/Items";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection getConnexion() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.err.println("Erreur lors de la connexion à la base de données : " + e.getMessage());
            return null;
        }
    }

    public static void initialiserTableUtilisateurs() {
        String sql = "CREATE TABLE IF NOT EXISTS Utilisateurs (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "nom VARCHAR(100) NOT NULL, " +
                "prenom VARCHAR(100) NOT NULL, " +
                "email VARCHAR(150) UNIQUE NOT NULL, " +
                "telephone VARCHAR(15) NOT NULL, " +
                "motDePasse VARCHAR(255) NOT NULL" +
                ")";
        try (Connection conn = getConnexion();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table Utilisateurs initialisée avec succès !");
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'initialisation de la table Utilisateurs : " + e.getMessage());
        }
    }

    public static boolean ajouterUtilisateur(Utilisateur utilisateur) {
        String sql = "INSERT INTO Utilisateurs (nom, prenom, email, telephone, motDePasse) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = getConnexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, utilisateur.getNom());
            pstmt.setString(2, utilisateur.getPrenom());
            pstmt.setString(3, utilisateur.getEmail());
            pstmt.setString(4, utilisateur.getTelephone());
            pstmt.setString(5, hacherMotDePasse(utilisateur.getMotDePasse()));
            pstmt.executeUpdate();
            System.out.println("Utilisateur ajouté avec succès !");
            return true;
        } catch (SQLException e) {
            if ("23000".equals(e.getSQLState())) {
                System.err.println("Erreur : L'email est déjà utilisé.");
            } else {
                System.err.println("Erreur lors de l'ajout de l'utilisateur : " + e.getMessage());
            }
            return false;
        }
    }

    public static boolean mettreAjourUtilisateur(Utilisateur utilisateur) {
        String sql = "UPDATE Utilisateurs SET nom = ?, prenom = ?, email = ?, telephone = ?, motDePasse = ? WHERE id = ?";
        try (Connection conn = getConnexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, utilisateur.getNom());
            pstmt.setString(2, utilisateur.getPrenom());
            pstmt.setString(3, utilisateur.getEmail());
            pstmt.setString(4, utilisateur.getTelephone());
            pstmt.setString(5, hacherMotDePasse(utilisateur.getMotDePasse()));
            pstmt.setInt(6, utilisateur.getId());
            pstmt.executeUpdate();
            System.out.println("Utilisateur mis à jour avec succès !");
            return true;
        } catch (SQLException e) {
            System.err.println("Erreur lors de la mise à jour de l'utilisateur : " + e.getMessage());
            return false;
        }
    }

    public static boolean supprimerUtilisateur(int id) {
        String sql = "DELETE FROM Utilisateurs WHERE id = ?";
        try (Connection conn = getConnexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("Utilisateur supprimé avec succès !");
            return true;
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression de l'utilisateur : " + e.getMessage());
            return false;
        }
    }

    public static Utilisateur getUtilisateurParEmail(String email) {
        String sql = "SELECT * FROM Utilisateurs WHERE email = ?";
        try (Connection conn = getConnexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Utilisateur(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getString("telephone"),
                        rs.getString("motDePasse"),
                        null
                );
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération de l'utilisateur : " + e.getMessage());
        }
        return null;
    }

    public static List<Utilisateur> getTousUtilisateurs() {
        List<Utilisateur> utilisateurs = new ArrayList<>();
        String sql = "SELECT * FROM Utilisateurs";
        try (Connection conn = getConnexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Utilisateur utilisateur = new Utilisateur(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getString("telephone"),
                        rs.getString("motDePasse"),
                        null
                );
                utilisateurs.add(utilisateur);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération de tous les utilisateurs : " + e.getMessage());
        }
        return utilisateurs;
    }

    public static boolean verifierIdentifiants(String email, String motDePasse) {
        String sql = "SELECT motDePasse FROM Utilisateurs WHERE email = ?";
        try (Connection conn = getConnexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String motDePasseHache = rs.getString("motDePasse");
                return hacherMotDePasse(motDePasse).equals(motDePasseHache);
            } else {
                System.err.println("Email non trouvé.");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la vérification des identifiants : " + e.getMessage());
        }
        return false;
    }

    public static String hacherMotDePasse(String motDePasse) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(motDePasse.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors du hachage du mot de passe", e);
        }
    }
}
