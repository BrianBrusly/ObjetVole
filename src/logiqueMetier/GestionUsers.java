package logiqueMetier;

import classesMetiers.Utilisateur;
import persistance.BDconnexionUser;

import java.util.List;

public class GestionUsers {

    // Ajoute un utilisateur avec validation
    public String ajouterUtilisateur(Utilisateur utilisateur) {
        if (utilisateur.getEmail() == null || utilisateur.getEmail().isEmpty()) {
            return "L'email ne peut pas être vide.";
        }

        BDconnexionUser.ajouterUtilisateur(utilisateur);
        return "Utilisateur ajouté avec succès.";
    }

    // Récupère un utilisateur par son email
    public Utilisateur rechercherParEmail(String email) {
        return BDconnexionUser.getUtilisateurParEmail(email);
    }

    // Liste tous les utilisateurs enregistrés
    public List<Utilisateur> listerTousLesUtilisateurs() {
        return BDconnexionUser.getTousUtilisateurs();
    }

    // Supprime un utilisateur par objet Utilisateur
    public boolean supprimerUtilisateur(Utilisateur utilisateur) {
        if (utilisateur == null) {
            System.out.println("Erreur : Utilisateur non valide.");
            return false;
        }

        // Appel à la base de données pour supprimer l'utilisateur
        BDconnexionUser.supprimerUtilisateur(utilisateur.getId());
        return true;
    }

    // Supprime un utilisateur par son ID
    public boolean supprimerUtilisateurParId(int id) {
        Utilisateur utilisateur = BDconnexionUser.getTousUtilisateurs()
                .stream()
                .filter(u -> u.getId() == id)
                .findFirst()
                .orElse(null);

        if (utilisateur == null) {
            System.out.println("Erreur : Utilisateur introuvable.");
            return false;
        }

        BDconnexionUser.supprimerUtilisateur(id);
        return true;
    }

    // Met à jour les informations d'un utilisateur
    public String mettreAJourUtilisateur(Utilisateur utilisateur) {
        Utilisateur existant = BDconnexionUser.getUtilisateurParEmail(utilisateur.getEmail());
        if (existant == null) {
            return "Impossible de mettre à jour : l'utilisateur n'existe pas.";
        }

        BDconnexionUser.mettreAjourUtilisateur(utilisateur);
        return "Profil utilisateur mis à jour avec succès.";
    }
}
