package application;

import interfaceUser.Notification;
import classesMetiers.Item;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import persistance.BDconnexionItem;

public class NotificationPage {

    private final Stage stage;

    public NotificationPage(Stage stage) {
        this.stage = stage;
    }

    public void afficher() {
        VBox layout = new VBox(15);
        layout.setStyle("-fx-padding: 20; -fx-alignment: center;");

        Label numeroSerieLabel = new Label("Numéro de série de l'objet :");
        TextField numeroSerieField = new TextField();
        Button envoyerButton = new Button("Envoyer Notification");

        envoyerButton.setOnAction(e -> {
            String numeroSerie = numeroSerieField.getText();
            Item item = BDconnexionItem.rechercherItemParNumeroSerie(numeroSerie);
            if (item != null) {
                new Notification().notifierProprietaire(item, "Localisation inconnue");
                afficherMessageSucces("Notification envoyée", "Le propriétaire a été informé.");
            } else {
                afficherMessageErreur("Objet introuvable ou non déclaré comme volé.");
            }
        });

        layout.getChildren().addAll(numeroSerieLabel, numeroSerieField, envoyerButton);

        Scene scene = new Scene(layout, 400, 200);
        try {
            scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        } catch (Exception ex) {
            System.err.println("Erreur lors du chargement du style CSS : " + ex.getMessage());
        }
        stage.setScene(scene);
        stage.setTitle("Notification");
        stage.show();
    }

    private void afficherMessageSucces(String header, String contenu) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setHeaderText(header);
        alert.setContentText(contenu);
        alert.showAndWait();
    }

    private void afficherMessageErreur(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText("Échec de la notification");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
