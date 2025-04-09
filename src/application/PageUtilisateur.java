package application;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import classesMetiers.Utilisateur;
import logiqueMetier.GestionUsers;

public class PageUtilisateur {

    private final Stage stage;
    private final Utilisateur utilisateur;

    public PageUtilisateur(Stage stage, Utilisateur utilisateur) {
        this.stage = stage;
        this.utilisateur = utilisateur; // Récupération des données utilisateur
    }

    public void afficher() {
        VBox layout = new VBox(15);
        layout.setStyle("-fx-padding: 20; -fx-alignment: center;");

        // Boutons pour les actions utilisateur
        Button profilButton = new Button("Voir Profil");
        Button gererItemsButton = new Button("Gérer Objets");
        Button gererInfosButton = new Button("Modifier Mes Informations");
        Button deconnexionButton = new Button("Se Déconnecter");
        Button supprimerCompteButton = new Button("Supprimer Mon Compte");

        // Actions des boutons
        profilButton.setOnAction(e -> afficherProfil());
        gererItemsButton.setOnAction(e -> gererItems());
        gererInfosButton.setOnAction(e -> modifierInformations());
        deconnexionButton.setOnAction(e -> seDeconnecter());
        supprimerCompteButton.setOnAction(e -> supprimerCompte());

        // Ajout des boutons au layout
        layout.getChildren().addAll(
                profilButton,
                gererItemsButton,
                gererInfosButton,
                deconnexionButton,
                supprimerCompteButton
        );

        Scene scene = new Scene(layout, 400, 300);

        try {
            scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        } catch (Exception e) {
            System.err.println("Erreur lors du chargement de la feuille de style : " + e.getMessage());
        }

        stage.setScene(scene);
        stage.setTitle("Espace Utilisateur");
        stage.show();
    }

    // Méthode pour afficher le profil utilisateur
    private void afficherProfil() {
        // Simulation de l'affichage des informations utilisateur
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Profil Utilisateur");
        alert.setHeaderText("Détails du Profil");
        alert.setContentText("Nom : " + utilisateur.getNom() + "\n"
                + "Prénom : " + utilisateur.getPrenom() + "\n"
                + "Email : " + utilisateur.getEmail() + "\n"
                + "Téléphone : " + utilisateur.getTelephone());
        alert.showAndWait();
    }

    // Méthode pour gérer les objets (redirection vers une autre page)
    private void gererItems() {
        VerificationObjetPage verificationObjetPage = new VerificationObjetPage(stage);
        verificationObjetPage.afficher();
    }

    // Méthode pour modifier les informations utilisateur
    private void modifierInformations() {
        ModifierInformationsPage modifierInformationsPage = new ModifierInformationsPage(stage, utilisateur);
        modifierInformationsPage.afficher();
    }

    // Méthode pour gérer la déconnexion
    private void seDeconnecter() {
        System.out.println("Déconnexion réussie.");
        ConnexionPage connexionPage = new ConnexionPage(stage);
        connexionPage.afficher(); // Redirection vers la page de connexion
    }

    // Méthode pour supprimer le compte utilisateur
    private void supprimerCompte() {
        GestionUsers gestionUsers = new GestionUsers();
        boolean suppressionReussie = gestionUsers.supprimerUtilisateur(utilisateur);

        if (suppressionReussie) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Compte Supprimé");
            alert.setHeaderText("Suppression Réussie");
            alert.setContentText("Votre compte a été supprimé avec succès.");
            alert.showAndWait();

            seDeconnecter(); // Déconnexion après suppression
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de Suppression");
            alert.setHeaderText("Échec de la Suppression");
            alert.setContentText("Une erreur est survenue lors de la suppression de votre compte.");
            alert.showAndWait();
        }
    }
}
