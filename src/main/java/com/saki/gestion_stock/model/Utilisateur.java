package com.saki.gestion_stock.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.Instant;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "utilisateur")
public class Utilisateur extends AbstractEntity {

    // Nom de famille de l'utilisateur
    @Column(name = "nom")
    private String nom;

    // Prénom de l'utilisateur
    @Column(name = "prenom")
    private String prenom;

    // Adresse email de l'utilisateur (utilisée pour l'authentification)
    @Column(name = "email")
    private String email;

    // Date de naissance de l'utilisateur
    @Column(name = "datedenaissance")
    private Instant dateDeNaissance;

    // Mot de passe de l'utilisateur (devrait être hashé et sécurisé en pratique)
    @Column(name = "motedepasse")
    private String moteDePasse;

    // Adresse de l'utilisateur intégrée comme objet embeddable
    // Les champs d'Adresse seront mappés dans la même table Utilisateur
    @Embedded
    private Adresse adresse;

    // Photo de profil de l'utilisateur
    @Column(name = "photo")
    private String photo;

    // Relation Many-to-One : un utilisateur appartient à une seule entreprise
    // Plusieurs utilisateurs peuvent appartenir à la même entreprise
    @ManyToOne
    @JoinColumn(name = "identreprise")  // Clé étrangère vers la table entreprise
    private Entreprise entreprise;

    // Relation One-to-Many : un utilisateur peut avoir plusieurs rôles
    // FetchType.EAGER : les rôles sont chargés immédiatement avec l'utilisateur
    // mappedBy indique que la relation est gérée par l'attribut "utilisateur" dans Roles
    // @JsonIgnore : évite la sérialisation JSON circulaire lors des appels API
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "utilisateur")
    @JsonIgnore
    private List<Roles> roles;
}