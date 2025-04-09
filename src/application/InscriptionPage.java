package application;

import classesMetiers.Utilisateur;
import logiqueMetier.GestionUsers;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class InscriptionPage {
    private final Stage stage;

    public InscriptionPage(Stage stage) {
        this.stage = stage;
    }

    public void afficher() {
        // Layout pour le formulaire d'inscription
        GridPane layout = new GridPane();
        layout.setHgap(10);
        layout.setVgap(15);
        layout.setStyle("-fx-padding: 20; -fx-alignment: center;");

        // Champs du formulaire
        Label nomLabel = new Label("Nom :");
        TextField nomField = new TextField();
        Label prenomLabel = new Label("Prénom :");
        TextField prenomField = new TextField();
        Label emailLabel = new Label("Email :");
        TextField emailField = new TextField();
        Label telephoneLabel = new Label("Téléphone :");
        TextField telephoneField = new TextField();
        Label motDePasseLabel = new Label("Mot de Passe :");
        TextField motDePasseField = new TextField();
        Button inscrireButton = new Button("S'inscrire");

        // Action du bouton d'inscription
        inscrireButton.setOnAction(e -> {
            String nom = nomField.getText();
            String prenom = prenomField.getText();
            String email = emailField.getText();
            String telephone = telephoneField.getText();
            String motDePasse = motDePasseField.getText();

            // Validation basique
            if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || telephone.isEmpty() || motDePasse.isEmpty()) {
                afficherMessage("Erreur", "Champs incomplets", "Tous les champs doivent être remplis.");
                return;
            }

            // Ajout de l'utilisateur via la logique métier
            GestionUsers gestionUsers = new GestionUsers();
            Utilisateur utilisateur = new Utilisateur(0, nom, prenom, email, telephone, motDePasse, null);
            String resultat = gestionUsers.ajouterUtilisateur(utilisateur);

            // Redirection ou affichage du message selon le résultat
            if (resultat.contains("succès")) {
                afficherMessage("Succès", "Inscription réussie", "Votre compte a été créé avec succès !");
                PageUtilisateur pageUtilisateur = new PageUtilisateur(stage, utilisateur);
                pageUtilisateur.afficher();
            } else {
                afficherMessage("Erreur", "Échec d'inscription", resultat);
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
        layout.add(inscrireButton, 1, 5);

        // Création de la scène
        Scene scene = new Scene(layout, 500, 400);
        stage.setScene(scene);
        stage.setTitle("Inscription");
        stage.show();
    }

    private void afficherMessage(String titre, String header, String contenu) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(titre);
        alert.setHeaderText(header);
        alert.setContentText(contenu);
        alert.showAndWait(); // Afficher le message dans une fenêtre d'alerte
    }

    private void afficherConfirmation() {
        // Fenêtre de confirmation après inscription
        GridPane layout = new GridPane();
        layout.setStyle("-fx-padding: 20; -fx-alignment: center;");
        Label confirmationLabel = new Label("Inscription réussie !");
        Button continuerButton = new Button("Continuer");

        continuerButton.setOnAction(e -> {
            Utilisateur utilisateur = null;
            PageUtilisateur pageUtilisateur = new PageUtilisateur(stage, utilisateur);
            pageUtilisateur.afficher(); // Redirection vers une nouvelle page utilisateur
        });

        layout.add(confirmationLabel, 0, 0);
        layout.add(continuerButton, 0, 1);

        Scene scene = new Scene(layout, 300, 200);
        stage.setScene(scene);
        stage.setTitle("Confirmation");
        stage.show();
    }
}
