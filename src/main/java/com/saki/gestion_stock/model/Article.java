package com.saki.gestion_stock.model;

import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

// Annotation Lombok pour générer getters, setters, etc.
@Data
// Constructeur sans arguments
@NoArgsConstructor
// Constructeur avec tous les arguments
@AllArgsConstructor
// Génère equals et hashCode en incluant les champs de la classe parente AbstractEntity
@EqualsAndHashCode(callSuper = true)
// Indique que cette classe est une entité JPA
@Entity
// Spécifie le nom de la table dans la base de données
@Table(name = "article")
public class Article extends AbstractEntity {

    // Code unique identifiant l'article
    @Column(name = "codearticle")
    private String codeArticle;

    // Désignation ou libellé de l'article
    @Column(name = "designation")
    private String designation;

    // Prix unitaire hors taxes de l'article
    @Column(name = "prixunitaireht")
    private BigDecimal prixUnitaireHt;

    // Taux de TVA applicable à l'article (ex: 0.20 pour 20%)
    @Column(name = "tauxtva")
    private BigDecimal tauxTva;

    // Prix unitaire toutes taxes comprises (calculé)
    @Column(name = "prixunitairettc")
    private BigDecimal prixUnitaireTtc;

    // Chemin ou référence de la photo de l'article
    @Column(name = "photo")
    private String photo;

    // Identifiant de l'entreprise propriétaire de l'article (pour le multi-locataire)
    @Column(name = "identreprise")
    private Integer idEntreprise;

    // Relation Many-to-One : plusieurs articles peuvent appartenir à une même catégorie
    @ManyToOne
    @JoinColumn(name = "idcategory")  // Clé étrangère vers la table category
    private Category category;

    // Relation One-to-Many : un article peut apparaître dans plusieurs lignes de vente
    // mappedBy indique que la relation est gérée par l'attribut "article" dans LigneVente
    @OneToMany(mappedBy = "article")
    private List<LigneVente> ligneVentes;

    // Relation One-to-Many : un article peut apparaître dans plusieurs lignes de commande client
    @OneToMany(mappedBy = "article")
    private List<LigneCommandeClient> ligneCommandeClients;

    // Relation One-to-Many : un article peut apparaître dans plusieurs lignes de commande fournisseur
    @OneToMany(mappedBy = "article")
    private List<LigneCommandeFournisseur> ligneCommandeFournisseurs;

    // Relation One-to-Many : un article peut avoir plusieurs mouvements de stock
    @OneToMany(mappedBy = "article")
    private List<MvtStk> mvtStks;
}