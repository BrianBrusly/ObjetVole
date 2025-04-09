package interfaceUser;

import classesMetiers.Item;
import Service.NotificationService;

public class Notification {

    private final NotificationService notificationService;

    public Notification() {
        this.notificationService = new NotificationService();
    }

    public void notifierProprietaire(Item objet, String lieuTrouve) {
        if (objet != null && objet.isEstVole()) {
            String sujet = "Objet retrouvé : " + objet.getType();
            String message = "Bonjour " + objet.getProprietaire() + ",\n\n" +
                    "Nous avons retrouvé votre objet volé (Numéro de Série : " + objet.getNumeroSerie() + ").\n" +
                    "Lieu de retrouvaille : " + lieuTrouve + ".\n\nMerci de nous contacter pour récupérer votre objet.";

            boolean notificationEnvoyee = notificationService.envoyerEmail(objet.getNumeroTelProprio() + "@example.com", sujet, message);
            if (!notificationEnvoyee) {
                System.err.println("Échec lors de l'envoi de l'email au propriétaire.");
            }
        } else {
            System.err.println("Impossible de notifier le propriétaire : l'objet est invalide ou non marqué comme volé.");
        }
    }
}
