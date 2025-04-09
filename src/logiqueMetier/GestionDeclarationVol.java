package logiqueMetier;

import classesMetiers.DeclarationVol;
import classesMetiers.Item;
import persistance.BDconnexionDeclarationVol;
import persistance.BDconnexionItem;

import java.time.LocalDate;
import java.util.List;

public class GestionDeclarationVol {

    // Ajoute une déclaration de vol
    public String ajouterDeclarationVol(String numeroSerie, String lieuVol, String description) {
        Item item = BDconnexionItem.rechercherItemParNumeroSerie(numeroSerie);
        if (item == null) {
            return "Impossible d'ajouter une déclaration : l'objet n'existe pas.";
        }

        if (item.isEstVole()) {
            return "Cet objet est déjà déclaré comme volé.";
        }

        DeclarationVol declaration = new DeclarationVol(null, item, LocalDate.now(), lieuVol, description);
        BDconnexionDeclarationVol.ajouterDeclarationVol(declaration);

        item.setEstVole(true);
        BDconnexionItem.mettreAjourItem(item);

        return "Déclaration de vol ajoutée avec succès.";
    }

    // Liste toutes les déclarations de vol
    public List<DeclarationVol> listerDeclarationsVol() {
        return BDconnexionDeclarationVol.getToutesDeclarationsVol();
    }

    // Supprime une déclaration par ID
    public String supprimerDeclarationVol(int id) {
        DeclarationVol declaration = BDconnexionDeclarationVol.rechercherDeclarationParId(id);
        if (declaration == null) {
            return "Déclaration introuvable.";
        }

        Item item = declaration.getObjet();
        if (item != null && item.isEstVole()) {
            item.setEstVole(false);
            BDconnexionItem.mettreAjourItem(item);
        }

        BDconnexionDeclarationVol.supprimerDeclarationParId(id);
        return "Déclaration de vol supprimée avec succès.";
    }

    // Met à jour une déclaration existante
    public String mettreAJourDeclarationVol(DeclarationVol declaration) {
        DeclarationVol existante = BDconnexionDeclarationVol.rechercherDeclarationParId(Math.toIntExact(declaration.getId()));
        if (existante == null) {
            return "Impossible de mettre à jour : la déclaration n'existe pas.";
        }

        BDconnexionDeclarationVol.mettreAjourDeclarationVol(declaration); // Appel à la méthode de persistance
        return "Déclaration de vol mise à jour avec succès.";
    }
}
