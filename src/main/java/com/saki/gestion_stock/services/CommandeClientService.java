package com.saki.gestion_stock.services;

import com.saki.gestion_stock.dto.CommandeClientDto;
import com.saki.gestion_stock.dto.LigneCommandeClientDto;
import com.saki.gestion_stock.model.EtatCommande;

import java.math.BigDecimal;
import java.util.List;

// Interface de service pour la gestion des commandes clients
// Définit le contrat métier pour le cycle de vie complet des commandes clients
public interface CommandeClientService {

    // Création d'une nouvelle commande client avec validation complète
    // Gère également la sauvegarde des lignes de commande et les mouvements de stock
    CommandeClientDto save(CommandeClientDto dto);

    // Mise à jour de l'état d'une commande (ex: EN_PREPARATION → VALIDEE → LIVREE)
    // Peut déclencher des actions métier comme les mouvements de stock
    CommandeClientDto updateEtatCommande(Integer idCommande, EtatCommande etatCommande);

    // Modification de la quantité d'un article dans une ligne de commande
    // Inclut la vérification du stock disponible si augmentation
    CommandeClientDto updateQuantiteCommande(Integer idCommande, Integer idLigneCommande, BigDecimal quantite);

    // Changement du client associé à une commande
    // Utile pour les erreurs de saisie ou les transferts de commande
    CommandeClientDto updateClient(Integer idCommande, Integer idClient);

    // Remplacement d'un article par un autre dans une ligne de commande
    // Inclut la validation du nouvel article et l'ajustement des stocks
    CommandeClientDto updateArticle(Integer idCommande, Integer idLigneCommande, Integer newIdArticle);

    // Suppression d'un article d'une commande = suppression de la ligne de commande
    // Doit gérer l'ajustement des stocks si nécessaire
    CommandeClientDto deleteArticle(Integer idCommande, Integer idLigneCommande);

    // Recherche d'une commande client par son identifiant unique
    // Retourne la commande avec ses lignes détaillées
    CommandeClientDto findById(Integer id);

    // Recherche d'une commande client par son code (numéro de commande)
    // Alternative pour les références métier
    CommandeClientDto findByCode(String code);

    // Récupération de toutes les commandes clients du système
    List<CommandeClientDto> findAll();

    // Consultation de toutes les lignes d'une commande client spécifique
    // Utile pour l'affichage du détail d'une commande
    List<LigneCommandeClientDto> findAllLignesCommandesClientByCommandeClientId(Integer idCommande);

    // Suppression complète d'une commande client
    // Doit gérer la suppression en cascade des lignes de commande
    void delete(Integer id);

}