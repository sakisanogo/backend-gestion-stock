package com.saki.gestion_stock.model;

import java.time.Instant;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
@Table(name = "commandefournisseur")
public class CommandeFournisseur extends AbstractEntity {

    // Code unique identifiant la commande fournisseur (numéro de commande)
    @Column(name = "code")
    private String code;

    // Date et heure de la création de la commande fournisseur
    @Column(name = "datecommande")
    private Instant dateCommande;

    // État de la commande fournisseur stocké sous forme de chaîne de caractères
    // Les valeurs possibles sont définies dans l'énumération EtatCommande
    // (ex: EN_PREPARATION, VALIDEE, LIVREE, etc.)
    @Column(name = "etatcommande")
    @Enumerated(EnumType.STRING)
    private EtatCommande etatCommande;

    // Identifiant de l'entreprise propriétaire de la commande (pour le multi-locataire)
    @Column(name = "identreprise")
    private Integer idEntreprise;

    // Relation Many-to-One : une commande fournisseur est passée à un seul fournisseur
    // Plusieurs commandes peuvent être associées au même fournisseur
    @ManyToOne
    @JoinColumn(name = "idfournisseur")  // Clé étrangère vers la table fournisseur
    private Fournisseur fournisseur;

    // Relation One-to-Many : une commande fournisseur contient plusieurs lignes de commande
    // Chaque ligne représente un article commandé au fournisseur avec sa quantité, prix, etc.
    // mappedBy indique que la relation est gérée par l'attribut "commandeFournisseur" dans LigneCommandeFournisseur
    @OneToMany(mappedBy = "commandeFournisseur")
    private List<LigneCommandeFournisseur> ligneCommandeFournisseurs;
}