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
@Table(name = "commandeclient")
public class CommandeClient extends AbstractEntity {

    // Code unique identifiant la commande client (numéro de commande)
    @Column(name = "code")
    private String code;

    // Date et heure de la création de la commande
    @Column(name = "datecommande")
    private Instant dateCommande;

    // État de la commande stocké sous forme de chaîne de caractères en base
    // Les valeurs possibles sont définies dans l'énumération EtatCommande
    @Column(name = "etatcommande")
    @Enumerated(EnumType.STRING)
    private EtatCommande etatCommande;

    // Identifiant de l'entreprise propriétaire de la commande (pour le multi-locataire)
    @Column(name = "identreprise")
    private Integer idEntreprise;

    // Relation Many-to-One : une commande appartient à un seul client
    // Plusieurs commandes peuvent être associées au même client
    @ManyToOne
    @JoinColumn(name = "idclient")  // Clé étrangère vers la table client
    private Client client;

    // Relation One-to-Many : une commande client contient plusieurs lignes de commande
    // Chaque ligne représente un article commandé avec sa quantité, prix, etc.
    // mappedBy indique que la relation est gérée par l'attribut "commandeClient" dans LigneCommandeClient
    @OneToMany(mappedBy = "commandeClient")
    private List<LigneCommandeClient> ligneCommandeClients;
}