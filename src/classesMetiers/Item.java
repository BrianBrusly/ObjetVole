package classesMetiers;

import java.time.LocalDate;

public class Item {
    private String numeroSerie;
    private String type;
    private String proprietaire;
    private String numeroTelProprio;
    private boolean estVole;
    private LocalDate dateVol;

    // Constructeur
    public Item(String numeroSerie, String type, String proprietaire, String numeroTelProprio, boolean estVole, LocalDate dateVol) {
        this.numeroSerie = numeroSerie;
        this.type = type;
        this.proprietaire = proprietaire;
        this.numeroTelProprio = numeroTelProprio;
        this.estVole = estVole;
        this.dateVol = dateVol;
    }

    // Getters et Setters

    public String getNumeroSerie() {
        return numeroSerie;
    }

    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProprietaire() {
        return proprietaire;
    }

    public void setProprietaire(String proprietaire) {
        this.proprietaire = proprietaire;
    }

    public String getNumeroTelProprio() {
        return numeroTelProprio;
    }

    public void setNumeroTelProprio(String numeroTelProprio) {
        this.numeroTelProprio = numeroTelProprio;
    }

    public boolean isEstVole() { // Getter pour estVole
        return estVole;
    }

    public void setEstVole(boolean estVole) { // Setter pour estVole
        this.estVole = estVole;
    }

    public LocalDate getDateVol() { // Getter pour dateVol
        return dateVol;
    }

    public void setDateVol(LocalDate dateVol) { // Setter pour dateVol
        this.dateVol = dateVol;
    }

    // Méthode toString pour l'affichage de l'objet
    @Override
    public String toString() {
        return "Item{" +
                "numeroSerie='" + numeroSerie + '\'' +
                ", type='" + type + '\'' +
                ", proprietaire='" + proprietaire + '\'' +
                ", numeroTelProprio='" + numeroTelProprio + '\'' +
                ", estVole=" + estVole +
                ", dateVol=" + dateVol +
                '}';
    }
}
