package com.saki.gestion_stock.repository;

import java.util.Optional;

import com.saki.gestion_stock.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

// Interface de repository pour l'entité Category, étendant JpaRepository pour les opérations CRUD
// Spring Data JPA implémente automatiquement les méthodes de base (save, findAll, findById, delete, etc.)
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    // Recherche une catégorie par son code (généralement unique)
    // Retourne un Optional<Category> pour gérer le cas où aucune catégorie n'est trouvée
    // Méthode dérivée : Spring Data JPA génère automatiquement la requête SQL basée sur le nom de la méthode
    // La requête équivalente serait : SELECT * FROM category WHERE code = ?
    Optional<Category> findCategoryByCode(String code);
}