package com.saki.gestion_stock.model;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

// Annotation Lombok pour générer getters, setters, toString, etc.
@Data
// Génère un constructeur sans arguments
@NoArgsConstructor
// Génère un constructeur avec tous les arguments
@AllArgsConstructor
// Génère equals et hashCode en incluant les champs de la classe parente AbstractEntity
@EqualsAndHashCode(callSuper = true)
// Indique que cette classe est une entité JPA
@Entity
// Spécifie le nom de la table dans la base de données
@Table(name = "fournisseur")
public class Fournisseur extends AbstractEntity {

    // Nom de famille du fournisseur
    @Column(name = "nom")
    private String nom;

    // Prénom du fournisseur
    @Column(name = "prenom")
    private String prenom;

    // Adresse du fournisseur intégrée comme objet embeddable
    // Les champs d'Adresse seront mappés dans la même table Fournisseur
    @Embedded
    private Adresse adresse;

    // Logo ou photo du fournisseur
    @Column(name = "photo")
    private String photo;

    // Adresse email de contact du fournisseur
    @Column(name = "mail")
    private String mail;

    // Numéro de téléphone du fournisseur
    @Column(name = "numTel")
    private String numTel;

    // Identifiant de l'entreprise propriétaire du fournisseur (pour le multi-locataire)
    @Column(name = "identreprise")
    private Integer idEntreprise;

    // Relation One-to-Many : un fournisseur peut avoir plusieurs commandes
    // mappedBy indique que la relation est gérée par l'attribut "fournisseur" dans CommandeFournisseur
    // Cette liste représente l'historique des commandes passées à ce fournisseur
    @OneToMany(mappedBy = "fournisseur")
    private List<CommandeFournisseur> commandeFournisseurs;
}