package com.saki.gestion_stock.model;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "lignecommandeclient")
public class LigneCommandeClient extends AbstractEntity {

    // Relation Many-to-One : une ligne de commande référence un seul article
    // Plusieurs lignes de commande peuvent concerner le même article
    @ManyToOne
    @JoinColumn(name = "idarticle")  // Clé étrangère vers la table article
    private Article article;

    // Relation Many-to-One : une ligne de commande appartient à une seule commande client
    // Une commande client peut contenir plusieurs lignes de commande
    @ManyToOne
    @JoinColumn(name = "idcommandeclient")  // Clé étrangère vers la table commandeclient
    private CommandeClient commandeClient;

    // Quantité de l'article commandé (utilisation de BigDecimal pour la précision décimale)
    @Column(name = "quantite")
    private BigDecimal quantite;

    // Prix unitaire de l'article au moment de la commande
    // Permet de conserver le prix historique même si le prix de l'article change ensuite
    @Column(name = "prixunitaire")
    private BigDecimal prixUnitaire;

    // Identifiant de l'entreprise propriétaire de la ligne de commande (pour le multi-locataire)
    @Column(name = "identreprise")
    private Integer idEntreprise;
}