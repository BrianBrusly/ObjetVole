package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Layout principal
        VBox layout = new VBox(15);
        layout.setStyle("-fx-padding: 20; -fx-alignment: center;");

        // Boutons pour naviguer
        Button inscriptionButton = new Button("Inscription");
        Button connexionButton = new Button("Connexion");

        inscriptionButton.setOnAction(e -> ouvrirInscription(primaryStage));
        connexionButton.setOnAction(e -> ouvrirConnexion(primaryStage));

        layout.getChildren().addAll(inscriptionButton, connexionButton);

        // Configuration de la scène
        Scene scene = new Scene(layout, 400, 300);
        try {
            scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        } catch (Exception e) {
            System.err.println("Erreur lors du chargement de la feuille de style : " + e.getMessage());
        }

        // Configurer la fenêtre principale
        primaryStage.setScene(scene);
        primaryStage.setTitle("Page Principale");
        primaryStage.show();
    }

    private void ouvrirInscription(Stage stage) {
        InscriptionPage inscriptionPage = new InscriptionPage(stage);
        inscriptionPage.afficher();
    }

    private void ouvrirConnexion(Stage stage) {
        ConnexionPage connexionPage = new ConnexionPage(stage);
        connexionPage.afficher();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
