package logiqueMetier;

import classesMetiers.DeclarationVol;
import classesMetiers.Item;
import interfaceUser.Notification;
import persistance.BDconnexionDeclarationVol;
import persistance.BDconnexionItem;

import java.time.LocalDate;
import java.util.List;

public class GestionItems {

    // Vérifie le statut d'un objet (volé ou non)
    public String verifierStatutItem(String numeroSerie) {
        Item item = BDconnexionItem.rechercherItemParNumeroSerie(numeroSerie);
        if (item == null) {
            return "Aucun objet trouvé avec ce numéro de série.";
        }
        return "Statut de l'objet : " + (item.isEstVole() ? "Volé" : "Non volé");
    }

    // Vérifie si un objet existe et renvoie les informations principales
    public String verifierObjet(String numeroSerie) {
        Item item = BDconnexionItem.rechercherItemParNumeroSerie(numeroSerie);
        if (item == null) {
            return "Aucun objet trouvé avec ce numéro de série.";
        }
        return "Objet trouvé : Numéro de série : " + item.getNumeroSerie() +
                ", Type : " + item.getType() +
                ", Propriétaire : " + item.getProprietaire() +
                ", Statut : " + (item.isEstVole() ? "Volé" : "Non volé") +
                ", Date du vol : " + (item.getDateVol() != null ? item.getDateVol().toString() : "Non applicable");
    }

    // Ajoute un nouvel Item dans la base
    public String ajouterNouvelItem(Item item) {
        if (item.getNumeroSerie() == null || item.getNumeroSerie().isEmpty()) {
            return "Le numéro de série ne peut pas être vide.";
        }

        try {
            BDconnexionItem.ajouterItem(item);
            return "L'objet a été ajouté avec succès.";
        } catch (Exception e) {
            System.err.println("Erreur lors de l'ajout de l'objet : " + e.getMessage());
            return "Une erreur est survenue lors de l'ajout de l'objet.";
        }
    }

    // Liste tous les objets enregistrés
    public List<Item> listerTousLesItems() {
        try {
            return BDconnexionItem.getAllItems();
        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération des objets : " + e.getMessage());
            return null; // Retourne null en cas de problème
        }
    }

    // Met à jour un objet existant
    public String mettreAJourItem(Item item) {
        if (item == null || item.getNumeroSerie() == null || item.getNumeroSerie().isEmpty()) {
            return "Impossible de mettre à jour : les informations de l'objet sont invalides.";
        }

        Item existant = BDconnexionItem.rechercherItemParNumeroSerie(item.getNumeroSerie());
        if (existant == null) {
            return "Impossible de mettre à jour : cet objet n'existe pas.";
        }

        try {
            BDconnexionItem.mettreAjourItem(item);
            return "L'objet a été mis à jour avec succès.";
        } catch (Exception e) {
            System.err.println("Erreur lors de la mise à jour de l'objet : " + e.getMessage());
            return "Une erreur est survenue lors de la mise à jour de l'objet.";
        }
    }

    // Déclare un vol pour un objet
    public boolean declarerVol(Item objet, String lieu, String description) {
        if (objet == null) {
            System.err.println("Impossible de déclarer un vol : l'objet est invalide.");
            return false;
        }

        if (objet.isEstVole()) {
            System.err.println("Impossible de déclarer un vol : cet objet est déjà marqué comme volé.");
            return false;
        }

        // Créer une déclaration de vol
        DeclarationVol declaration = new DeclarationVol(null, objet, LocalDate.now(), lieu, description);

        try {
            BDconnexionDeclarationVol.ajouterDeclarationVol(declaration);

            // Mettre à jour le statut de l'objet
            objet.setEstVole(true);
            objet.setDateVol(LocalDate.now());
            BDconnexionItem.mettreAjourItem(objet);

            // Notification au propriétaire
            Notification notification = new Notification();
            notification.notifierProprietaire(objet, lieu);

            System.out.println("Vol déclaré avec succès.");
            return true;
        } catch (Exception e) {
            System.err.println("Erreur lors de la déclaration de vol : " + e.getMessage());
            return false;
        }
    }
}
