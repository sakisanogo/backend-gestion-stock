package com.saki.gestion_stock.model;

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
@Table(name = "roles")
public class Roles extends AbstractEntity {

    // Nom du rôle (ex: "ADMIN", "USER", "MANAGER", etc.)
    // Définit les permissions et accès de l'utilisateur dans le système
    @Column(name = "rolename")
    private String roleName;

    // Relation Many-to-One : un rôle appartient à un seul utilisateur
    // Un utilisateur peut avoir plusieurs rôles (relation inverse de la liste de rôles dans Utilisateur)
    @ManyToOne
    @JoinColumn(name = "idutilisateur")  // Clé étrangère vers la table utilisateur
    private Utilisateur utilisateur;
}