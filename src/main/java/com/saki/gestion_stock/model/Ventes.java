package com.saki.gestion_stock.model;

import java.time.Instant;
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
@Table(name = "ventes")
public class Ventes extends AbstractEntity {

    // Code unique identifiant la vente (numéro de vente ou de facture)
    @Column(name = "code")
    private String code;

    // Date et heure de la vente
    @Column(name = "datevente")
    private Instant dateVente;

    // Commentaire ou notes supplémentaires sur la vente
    // Peut contenir des informations complémentaires sur les conditions de vente, remises, etc.
    @Column(name = "commentaire")
    private String commentaire;

    // Identifiant de l'entreprise propriétaire de la vente (pour le multi-locataire)
    @Column(name = "identreprise")
    private Integer idEntreprise;

    // Relation One-to-Many : une vente contient plusieurs lignes de vente
    // Chaque ligne représente un article vendu avec sa quantité, prix, etc.
    // mappedBy indique que la relation est gérée par l'attribut "vente" dans LigneVente
    @OneToMany(mappedBy = "vente")
    private List<LigneVente> ligneVentes;
}