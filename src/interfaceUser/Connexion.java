package interfaceUser;

import classesMetiers.Utilisateur;
import logiqueMetier.GestionUsers;

public class Connexion {

    private final GestionUsers gestionUsers;

    public Connexion(GestionUsers gestionUsers) {
        this.gestionUsers = gestionUsers;
    }

    // Méthode pour vérifier les identifiants
    public boolean verifierIdentifiants(String email, String motDePasse) {
        Utilisateur utilisateur = gestionUsers.rechercherParEmail(email);

        // Vérifiez si l'utilisateur existe et si le mot de passe correspond
        return utilisateur != null && utilisateur.getMotDePasse().equals(motDePasse);
    }
}
