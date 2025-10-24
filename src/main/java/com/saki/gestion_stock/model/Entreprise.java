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
@Table(name = "entreprise")
public class Entreprise extends AbstractEntity {

    // Nom de l'entreprise
    @Column(name = "nom")
    private String nom;

    // Description de l'activité de l'entreprise
    @Column(name = "description")
    private String description;

    // Adresse de l'entreprise intégrée comme objet embeddable
    // Les champs d'Adresse seront mappés dans la même table Entreprise
    @Embedded
    private Adresse adresse;

    // Code fiscal ou numéro d'identification fiscale de l'entreprise (SIRET, etc.)
    @Column(name = "codefiscal")
    private String codeFiscal;

    // Logo ou photo de l'entreprise
    @Column(name = "photo")
    private String photo;

    // Adresse email de contact de l'entreprise
    @Column(name = "email")
    private String email;

    // Mot de passe pour l'authentification de l'entreprise
    // Note: En pratique, ce champ devrait être hashé et sécurisé
    @Column(name = "motDePasse")
    private String motDePasse;

    // Numéro de téléphone de l'entreprise
    @Column(name = "numtel")
    private String numTel;

    // Site web de l'entreprise
    @Column(name = "siteweb")
    private String steWeb;

    // Relation One-to-Many : une entreprise peut avoir plusieurs utilisateurs
    // mappedBy indique que la relation est gérée par l'attribut "entreprise" dans Utilisateur
    // Cette liste représente tous les utilisateurs qui appartiennent à cette entreprise
    @OneToMany(mappedBy = "entreprise")
    private List<Utilisateur> utilisateurs;
}