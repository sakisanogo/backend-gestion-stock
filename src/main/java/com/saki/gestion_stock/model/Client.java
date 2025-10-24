package com.saki.gestion_stock.model;

import java.util.List;
import javax.persistence.CascadeType;
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
@Table(name = "client")
public class Client extends AbstractEntity {

    // Nom de famille du client
    @Column(name = "nom")
    private String nom;

    // Prénom du client
    @Column(name = "prenom")
    private String prenom;

    // Adresse du client intégrée comme objet embeddable
    // Les champs d'Adresse seront mappés dans la même table Client
    @Embedded
    private Adresse adresse;

    // Chemin ou référence de la photo du client
    @Column(name = "photo")
    private String photo;

    // Adresse email du client
    @Column(name = "mail")
    private String mail;

    // Numéro de téléphone du client
    @Column(name = "numTel")
    private String numTel;

    // Identifiant de l'entreprise propriétaire du client (pour le multi-locataire)
    @Column(name = "identreprise")
    private Integer idEntreprise;

    // Relation One-to-Many : un client peut avoir plusieurs commandes
    // mappedBy indique que la relation est gérée par l'attribut "client" dans CommandeClient
    // Cette liste représente l'historique des commandes passées par ce client
    @OneToMany(mappedBy = "client")
    private List<CommandeClient> commandeClients;
}