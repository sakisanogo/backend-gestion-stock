package com.saki.gestion_stock.repository;

import java.util.Optional;
import com.saki.gestion_stock.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

// Interface de repository pour l'entité Utilisateur, étendant JpaRepository pour les opérations CRUD
// Spring Data JPA implémente automatiquement les méthodes de base (save, findAll, findById, delete, etc.)
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Integer> {

    // Requête JPQL personnalisée pour rechercher un utilisateur par son email
    // @Query permet de définir une requête JPQL (Java Persistence Query Language) personnalisée
    // "select u from Utilisateur u where u.email = :email" - requête qui sélectionne l'utilisateur par email
    // @Param("email") lie le paramètre de méthode "email" au paramètre nommé ":email" dans la requête JPQL
    // Retourne un Optional<Utilisateur> pour gérer proprement le cas où aucun utilisateur n'est trouvé
    // Cette méthode est cruciale pour l'authentification (recherche de l'utilisateur par email lors du login)
    // JPQL query
    @Query(value = "select u from Utilisateur u where u.email = :email")
    Optional<Utilisateur> findUtilisateurByEmail(@Param("email") String email);
}