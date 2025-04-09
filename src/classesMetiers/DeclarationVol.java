package classesMetiers;

import java.time.LocalDate;

public class DeclarationVol {
    private Long id;                  // ID unique de la déclaration
    private Item objet;               // L'objet concerné par le vol
    private LocalDate dateDeclaration;// Date de la déclaration du vol
    private String lieuVol;           // Lieu où l'objet a été volé
    private String description;       // Description supplémentaire du vol

    // Constructeur
    public DeclarationVol(Long id, Item objet, LocalDate dateDeclaration, String lieuVol, String description) {
        this.id = id;
        this.objet = objet;
        this.dateDeclaration = dateDeclaration;
        this.lieuVol = lieuVol;
        this.description = description;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Item getObjet() {
        return objet;
    }

    public void setObjet(Item objet) {
        this.objet = objet;
    }

    public LocalDate getDateDeclaration() {
        return dateDeclaration;
    }

    public void setDateDeclaration(LocalDate dateDeclaration) {
        this.dateDeclaration = dateDeclaration;
    }

    public String getLieuVol() {
        return lieuVol;
    }

    public void setLieuVol(String lieuVol) {
        this.lieuVol = lieuVol;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "DeclarationVol {" +
                "ID='" + id + '\'' +
                ", Objet='" + objet.getNumeroSerie() + '\'' +
                ", Date Déclaration='" + dateDeclaration + '\'' +
                ", Lieu='" + lieuVol + '\'' +
                ", Description='" + description + '\'' +
                '}';
    }
}
