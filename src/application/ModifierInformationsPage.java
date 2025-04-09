package application;

import classesMetiers.Utilisateur;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import logiqueMetier.GestionUsers;

public class ModifierInformationsPage {

    private final Stage stage;
    private final Utilisateur utilisateur;

    public ModifierInformationsPage(Stage stage, Utilisateur utilisateur) {
        this.stage = stage;
        this.utilisateur = utilisateur; // L'utilisateur connecté
    }

    public void afficher() {
        // Création du layout
        GridPane layout = new GridPane();
        layout.setHgap(10);
        layout.setVgap(15);
        layout.setStyle("-fx-padding: 20; -fx-alignment: center;");

        // Champs de formulaire
        Label nomLabel = new Label("Nom :");
        TextField nomField = new TextField(utilisateur.getNom());
        Label prenomLabel = new Label("Prénom :");
        TextField prenomField = new TextField(utilisateur.getPrenom());
        Label emailLabel = new Label("Email :");
        TextField emailField = new TextField(utilisateur.getEmail());
        Label telephoneLabel = new Label("Téléphone :");
        TextField telephoneField = new TextField(utilisateur.getTelephone());
        Label motDePasseLabel = new Label("Mot de Passe :");
        TextField motDePasseField = new TextField(utilisateur.getMotDePasse());
        Button enregistrerButton = new Button("Enregistrer");

        // Action du bouton "Enregistrer"
        enregistrerButton.setOnAction(e -> {
            // Mise à jour des données utilisateur
            utilisateur.setNom(nomField.getText());
            utilisateur.setPrenom(prenomField.getText());
            utilisateur.setEmail(emailField.getText());
            utilisateur.setTelephone(telephoneField.getText());
            utilisateur.setMotDePasse(motDePasseField.getText());

            GestionUsers gestionUsers = new GestionUsers();
            String resultat = gestionUsers.mettreAJourUtilisateur(utilisateur);

            // Gestion des résultats via alertes
            if (resultat.contains("succès")) {
                afficherMessageSucces("Informations modifiées avec succès !");
                retournerPageUtilisateur();
            } else {
                afficherMessageErreur("Erreur lors de la mise à jour : " + resultat);
            }
        });

        // Ajout des champs au layout
        layout.add(nomLabel, 0, 0);
        layout.add(nomField, 1, 0);
        layout.add(prenomLabel, 0, 1);
        layout.add(prenomField, 1, 1);
        layout.add(emailLabel, 0, 2);
        layout.add(emailField, 1, 2);
        layout.add(telephoneLabel, 0, 3);
        layout.add(telephoneField, 1, 3);
        layout.add(motDePasseLabel, 0, 4);
        layout.add(motDePasseField, 1, 4);
        layout.add(enregistrerButton, 1, 5);

        // Création de la scène
        Scene scene = new Scene(layout, 500, 400);
        stage.setScene(scene);
        stage.setTitle("Modifier Mes Informations");
        stage.show();
    }

    private void retournerPageUtilisateur() {
        PageUtilisateur pageUtilisateur = new PageUtilisateur(stage, utilisateur);
        pageUtilisateur.afficher();
    }

    private void afficherMessageErreur(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText("Erreur lors de la mise à jour");
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void afficherMessageSucces(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setHeaderText("Mise à jour réussie");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
