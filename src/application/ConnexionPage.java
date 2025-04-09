package application;

import classesMetiers.Utilisateur;
import logiqueMetier.GestionUsers;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class ConnexionPage {

    private final Stage stage;

    public ConnexionPage(Stage stage) {
        this.stage = stage;
    }

    public void afficher() {
        GridPane layout = new GridPane();
        layout.setHgap(10);
        layout.setVgap(15);
        layout.setStyle("-fx-padding: 20; -fx-alignment: center;");

        // Champs du formulaire
        Label emailLabel = new Label("Email :");
        TextField emailField = new TextField();
        Label motDePasseLabel = new Label("Mot de Passe :");
        PasswordField motDePasseField = new PasswordField();

        Button connexionButton = creerBoutonConnexion(emailField, motDePasseField);

        layout.add(emailLabel, 0, 0);
        layout.add(emailField, 1, 0);
        layout.add(motDePasseLabel, 0, 1);
        layout.add(motDePasseField, 1, 1);
        layout.add(connexionButton, 1, 2);

        Scene scene = new Scene(layout, 400, 300);
        try {
            scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        } catch (Exception ex) {
            System.err.println("Erreur lors du chargement de la feuille de style : " + ex.getMessage());
        }
        stage.setScene(scene);
        stage.setTitle("Connexion");
        stage.show();
    }

    private Button creerBoutonConnexion(TextField emailField, PasswordField motDePasseField) {
        Button connexionButton = new Button("Se connecter");

        connexionButton.setOnAction(e -> {
            String email = emailField.getText();
            String motDePasse = motDePasseField.getText();

            // Validation des champs
            if (email.isEmpty() || motDePasse.isEmpty()) {
                afficherMessageErreur("Tous les champs doivent être remplis !");
                return;
            }

            // Vérification des identifiants
            GestionUsers gestionUsers = new GestionUsers();
            Utilisateur utilisateur = gestionUsers.rechercherParEmail(email);

            if (utilisateur != null && utilisateur.getMotDePasse().equals(motDePasse)) {
                afficherMessageSucces("Connexion réussie", "Bienvenue " + utilisateur.getNom() + " !");
                ouvrirPageUtilisateur(utilisateur);
            } else {
                afficherMessageErreur("Identifiants incorrects !");
            }
        });

        return connexionButton;
    }

    private void ouvrirPageUtilisateur(Utilisateur utilisateur) {
        if (utilisateur == null) {
            afficherMessageErreur("Erreur : Impossible de charger la page utilisateur. Utilisateur non valide.");
            return;
        }

        PageUtilisateur pageUtilisateur = new PageUtilisateur(stage, utilisateur);
        pageUtilisateur.afficher();
    }

    private void afficherMessageErreur(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur de Connexion");
        alert.setHeaderText("Échec de Connexion");
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void afficherMessageSucces(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Connexion Réussie");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
