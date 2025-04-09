package interfaceUser;

import classesMetiers.Item;
import logiqueMetier.GestionItems;

import java.util.Scanner;

public class DeclarationVol {

    public void declarerVol() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Entrez le numéro de série de l'objet : ");
        String numeroSerie = scanner.nextLine();

        System.out.print("Entrez le lieu du vol : ");
        String lieu = scanner.nextLine();

        System.out.print("Entrez une description du vol : ");
        String description = scanner.nextLine();

        GestionItems gestionItems = new GestionItems();

        // Utilisation de la méthode verifierObjet pour vérifier si l'objet existe
        String informationsObjet = gestionItems.verifierObjet(numeroSerie);

        if (informationsObjet.startsWith("Objet trouvé")) { // Vérifie si l'objet est validé par verifierObjet
            Item item = gestionItems.listerTousLesItems().stream()
                    .filter(objet -> objet.getNumeroSerie().equals(numeroSerie))
                    .findFirst()
                    .orElse(null);

            if (item != null) {
                boolean result = gestionItems.declarerVol(item, lieu, description);
                System.out.println(result ? "Déclaration de vol enregistrée avec succès." : "Impossible de déclarer le vol.");
            } else {
                System.out.println("Une erreur s'est produite lors de la récupération des détails de l'objet.");
            }
        } else {
            System.out.println("Aucun objet trouvé avec ce numéro de série.");
        }
    }
}
