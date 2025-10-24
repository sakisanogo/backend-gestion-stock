package com.saki.gestion_stock.services;

import com.saki.gestion_stock.dto.CommandeFournisseurDto;
import com.saki.gestion_stock.dto.LigneCommandeFournisseurDto;
import com.saki.gestion_stock.model.EtatCommande;

import java.math.BigDecimal;
import java.util.List;

// Interface de service pour la gestion des commandes fournisseurs
// Définit le contrat métier pour le cycle de vie complet des commandes d'approvisionnement
public interface CommandeFournisseurService {

    // Création d'une nouvelle commande fournisseur avec validation complète
    // Gère la sauvegarde des lignes de commande et les entrées de stock associées
    CommandeFournisseurDto save(CommandeFournisseurDto dto);

    // Mise à jour de l'état d'une commande fournisseur
    // Les transitions d'état peuvent déclencher des entrées de stock lors de la livraison
    CommandeFournisseurDto updateEtatCommande(Integer idCommande, EtatCommande etatCommande);

    // Modification de la quantité commandée d'un article dans une ligne de commande
    // Affecte les quantités attendues en réception
    CommandeFournisseurDto updateQuantiteCommande(Integer idCommande, Integer idLigneCommande, BigDecimal quantite);

    // Changement du fournisseur associé à une commande
    // Utile pour les réapprovisionnements alternatifs ou les changements de partenaire
    CommandeFournisseurDto updateFournisseur(Integer idCommande, Integer idFournisseur);

    // Remplacement d'un article par un autre dans une ligne de commande
    // Permet d'ajuster les commandes en cours selon les disponibilités fournisseurs
    CommandeFournisseurDto updateArticle(Integer idCommande, Integer idLigneCommande, Integer idArticle);

    // Suppression d'un article d'une commande = suppression de la ligne de commande
    // Ajuste les quantités commandées et les prévisions de stock
    CommandeFournisseurDto deleteArticle(Integer idCommande, Integer idLigneCommande);

    // Recherche d'une commande fournisseur par son identifiant unique
    // Retourne la commande avec le détail de ses lignes
    CommandeFournisseurDto findById(Integer id);

    // Recherche d'une commande fournisseur par son code (numéro de commande)
    CommandeFournisseurDto findByCode(String code);

    // Récupération de toutes les commandes fournisseurs du système
    List<CommandeFournisseurDto> findAll();

    // Consultation de toutes les lignes d'une commande fournisseur spécifique
    // Utile pour le détail des articles commandés et leur réception
    List<LigneCommandeFournisseurDto> findAllLignesCommandesFournisseurByCommandeFournisseurId(Integer idCommande);

    // Suppression complète d'une commande fournisseur
    // Doit gérer la suppression en cascade des lignes de commande associées
    void delete(Integer id);

}