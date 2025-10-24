package com.saki.gestion_stock.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

// Annotation Lombok qui génère getters, setters, equals, hashCode, toString
@Data
// Génère un constructeur sans arguments
@NoArgsConstructor
// Génère un constructeur avec tous les arguments
@AllArgsConstructor
// Génère les méthodes equals et hashCode
@EqualsAndHashCode
// Indique que cette classe est embeddable (intégrable) dans d'autres entités JPA
// Permet de réutiliser cet objet Adresse dans plusieurs entités sans créer de table dédiée
@Embeddable
public class Adresse implements Serializable {

    // Première ligne de l'adresse
    @Column(name = "adresse1")
    private String adresse1;

    // Deuxième ligne de l'adresse (complément d'adresse - optionnel)
    @Column(name = "adresse2")
    private String adresse2;

    // Ville de l'adresse
    @Column(name = "ville")
    private String ville;

    // Code postal de l'adresse
    @Column(name = "codepostale")
    private String codePostale;

    // Pays de l'adresse
    @Column(name = "pays")
    private String pays;
}