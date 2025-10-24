package com.saki.gestion_stock.model;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;

import java.util.List;
import javax.persistence.Column;
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
@Table(name = "category")
public class Category extends AbstractEntity {

    // Code unique identifiant la catégorie
    @Column(name = "code")
    private String code;

    // Désignation ou libellé de la catégorie
    @Column(name = "designation")
    private String designation;

    // Identifiant de l'entreprise propriétaire de la catégorie (pour le multi-locataire)
    @Column(name = "identreprise")
    private Integer idEntreprise;

    // Relation One-to-Many : une catégorie peut contenir plusieurs articles
    // mappedBy indique que la relation est gérée par l'attribut "category" dans Article
    // Cette liste représente tous les articles qui appartiennent à cette catégorie
    @OneToMany(mappedBy = "category")
    private List<Article> articles;
}