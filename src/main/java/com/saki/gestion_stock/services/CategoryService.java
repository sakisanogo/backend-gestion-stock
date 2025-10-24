package com.saki.gestion_stock.services;

import com.saki.gestion_stock.dto.CategoryDto;

import java.util.List;

// Interface de service pour la gestion des catégories d'articles
// Définit le contrat métier pour toutes les opérations liées aux catégories
public interface CategoryService {

    // Sauvegarde d'une nouvelle catégorie ou mise à jour d'une catégorie existante
    // Inclut la validation des données avant sauvegarde
    CategoryDto save(CategoryDto dto);

    // Recherche d'une catégorie par son identifiant unique
    // Retourne une CategoryDto ou lance une exception si non trouvée
    CategoryDto findById(Integer id);

    // Recherche d'une catégorie par son code (généralement unique)
    // Alternative à la recherche par ID pour les intégrations externes
    CategoryDto findByCode(String code);

    // Récupération de toutes les catégories du système
    // Retourne une liste vide si aucune catégorie n'existe
    List<CategoryDto> findAll();

    // Suppression d'une catégorie avec gestion des dépendances
    // Doit vérifier qu'aucun article n'est associé à cette catégorie avant suppression
    // ou implémenter une suppression en cascade
    void delete(Integer id);

}