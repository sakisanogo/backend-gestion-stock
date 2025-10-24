package com.saki.gestion_stock.model;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

// Annotation Lombok qui génère automatiquement getters, setters, equals, hashCode, toString
@Data
// Indique que cette classe est une superclasse mappée pour l'héritage JPA
// Les champs seront hérités par toutes les entités qui étendent cette classe
@MappedSuperclass
// Active l'auditing automatique pour les dates de création et modification
@EntityListeners(AuditingEntityListener.class)
public class AbstractEntity implements Serializable {

    // Identifiant unique généré automatiquement pour chaque entité
    @Id
    @GeneratedValue
    private Integer id;

    // Date de création automatiquement remplie à la persistance de l'entité
    // Ne peut pas être modifiée après la création (updatable = false)
    @CreatedDate
    @Column(name = "creationDate", nullable = false, updatable = false)
    private Instant creationDate;

    // Date de dernière modification automatiquement mise à jour à chaque modification
    @LastModifiedDate
    @Column(name = "lastModifiedDate")
    private Instant lastModifiedDate;
}