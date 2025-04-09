package classesMetiers;

import java.util.List;

public class Utilisateur {
    private int id;                  // ID unique de l'utilisateur
    private String nom;              // Nom de famille
    private String prenom;           // Prénom
    private String email;            // Adresse e-mail
    private String telephone;        // Numéro de téléphone
    private String motDePasse;       // Mot de passe à hacher pour plus de sécurité
    private List<Item> items;        // Liste des objets associés au propriétaire

    // Constructeur
    public Utilisateur(int id, String nom, String prenom, String email, String telephone, String motDePasse, List<Item> items) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.telephone = telephone;
        this.motDePasse = motDePasse; // Important : à hacher avant stockage
        this.items = items;
    }


    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    // Méthode toString pour faciliter le débogage
    @Override
    public String toString() {
        return "Utilisateur{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", telephone='" + telephone + '\'' +
                ", items=" + items +
                '}';
    }
}
