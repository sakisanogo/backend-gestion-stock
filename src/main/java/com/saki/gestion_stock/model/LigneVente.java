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
@Table(name = "lignevente")
public class LigneVente extends AbstractEntity {

    // Relation Many-to-One : une ligne de vente appartient à une seule vente
    // Une vente peut contenir plusieurs lignes de vente (plusieurs articles vendus)
    @ManyToOne
    @JoinColumn(name = "idvente")  // Clé étrangère vers la table vente
    private Ventes vente;

    // Relation Many-to-One : une ligne de vente référence un seul article
    // Plusieurs lignes de vente peuvent concerner le même article
    @ManyToOne
    @JoinColumn(name = "idarticle")  // Clé étrangère vers la table article
    private Article article;

    // Quantité de l'article vendu (utilisation de BigDecimal pour la précision décimale)
    @Column(name = "quantite")
    private BigDecimal quantite;

    // Prix unitaire de vente de l'article au moment de la vente
    // Permet de conserver le prix de vente historique même si le prix de l'article change ensuite
    @Column(name = "prixunitaire")
    private BigDecimal prixUnitaire;

    // Identifiant de l'entreprise propriétaire de la ligne de vente (pour le multi-locataire)
    @Column(name = "identreprise")
    private Integer idEntreprise;
}