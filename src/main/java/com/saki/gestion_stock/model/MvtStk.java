package com.saki.gestion_stock.model;

import java.math.BigDecimal;
import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
@Table(name = "mvtstk")
public class MvtStk extends AbstractEntity {

    // Date et heure du mouvement de stock
    @Column(name = "datemvt")
    private Instant dateMvt;

    // Quantité concernée par le mouvement (positive ou négative selon le type)
    // Utilisation de BigDecimal pour la précision décimale
    @Column(name = "quantite")
    private BigDecimal quantite;

    // Relation Many-to-One : un mouvement de stock concerne un seul article
    // Un article peut avoir plusieurs mouvements de stock
    @ManyToOne
    @JoinColumn(name = "idarticle")  // Clé étrangère vers la table article
    private Article article;

    // Type de mouvement de stock (ENTREE, SORTIE, CORRECTION, etc.)
    // Stocké sous forme de chaîne de caractères en base de données
    @Column(name = "typemvt")
    @Enumerated(EnumType.STRING)
    private TypeMvtStk typeMvt;

    // Source ou origine du mouvement de stock (COMMANDE_CLIENT, COMMANDE_FOURNISSEUR, INVENTAIRE, etc.)
    // Permet de tracer l'origine du mouvement pour l'audit et l'analyse
    @Column(name = "sourcemvt")
    @Enumerated(EnumType.STRING)
    private SourceMvtStk sourceMvt;

    // Identifiant de l'entreprise propriétaire du mouvement de stock (pour le multi-locataire)
    @Column(name = "identreprise")
    private Integer idEntreprise;
}