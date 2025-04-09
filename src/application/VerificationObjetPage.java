package application;

import classesMetiers.Item;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import persistance.BDconnexionItem;

public class VerificationObjetPage {

    private final Stage stage;

    public VerificationObjetPage(Stage stage) {
        this.stage = stage;
    }

    public void afficher() {
        VBox layout = new VBox(15);
        layout.setStyle("-fx-padding: 20; -fx-alignment: center;");

        Label instructionLabel = new Label("Entrez le numéro de série de l'objet :");
        TextField numeroSerieField = new TextField();
        Button verifierButton = new Button("Vérifier");

        verifierButton.setOnAction(e -> verifierObjet(numeroSerieField.getText()));

        layout.getChildren().addAll(instructionLabel, numeroSerieField, verifierButton);

        Scene scene = new Scene(layout, 400, 200);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        stage.setScene(scene);
    }

    private void verifierObjet(String numeroSerie) {
        Item item = BDconnexionItem.rechercherItemParNumeroSerie(numeroSerie);
        if (item != null) {
            System.out.println("Objet trouvé : " + (item.isEstVole() ? "Volé" : "Non volé"));
        } else {
            System.out.println("Aucun objet trouvé avec ce numéro de série.");
        }
    }
}
