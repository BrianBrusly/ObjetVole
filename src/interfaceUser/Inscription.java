package interfaceUser;

import classesMetiers.Utilisateur;
import logiqueMetier.GestionUsers;

public class Inscription {

    private final GestionUsers gestionUsers;

    public Inscription(GestionUsers gestionUsers) {
        this.gestionUsers = gestionUsers;
    }

    // Méthode pour inscrire un utilisateur (appelée depuis l'interface graphique)
    public String inscrireUtilisateur(String nom, String prenom, String email, String telephone, String motDePasse) {
        Utilisateur utilisateur = new Utilisateur(0, nom, prenom, email, telephone, motDePasse, null);
        return gestionUsers.ajouterUtilisateur(utilisateur);
    }
}
