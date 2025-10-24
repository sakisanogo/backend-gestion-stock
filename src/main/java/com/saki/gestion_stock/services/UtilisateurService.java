package com.saki.gestion_stock.services;

import com.saki.gestion_stock.dto.ChangerMotDePasseUtilisateurDto;
import com.saki.gestion_stock.dto.UtilisateurDto;

import java.util.List;

// Interface de service pour la gestion des utilisateurs
// Définit le contrat métier pour l'authentification et la gestion des comptes utilisateurs
public interface UtilisateurService {

    // Création d'un nouvel utilisateur ou mise à jour d'un utilisateur existant
    // Inclut la validation des données, l'unicité de l'email et le hachage du mot de passe
    UtilisateurDto save(UtilisateurDto dto);

    // Recherche d'un utilisateur par son identifiant unique
    // Retourne un UtilisateurDto complet ou lance une exception si non trouvé
    UtilisateurDto findById(Integer id);

    // Récupération de tous les utilisateurs du système
    // L'accès peut être restreint selon les permissions de l'utilisateur connecté
    List<UtilisateurDto> findAll();

    // Suppression d'un utilisateur
    // Doit gérer les dépendances avec les rôles et autres données associées
    void delete(Integer id);

    // Recherche d'un utilisateur par son adresse email
    // Principalement utilisé pour le processus d'authentification Spring Security
    UtilisateurDto findByEmail(String email);

    // Changement sécurisé du mot de passe d'un utilisateur
    // Inclut la validation des mots de passe et leur confirmation
    // Le mot de passe est automatiquement haché avant sauvegarde
    UtilisateurDto changerMotDePasse(ChangerMotDePasseUtilisateurDto dto);

}