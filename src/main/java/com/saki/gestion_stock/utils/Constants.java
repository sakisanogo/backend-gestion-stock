package com.saki.gestion_stock.utils;

// Interface de constantes pour les endpoints de l'API REST
// Centralise toutes les URLs de l'application pour une maintenance facile
public interface Constants {

    // Racine de base de toutes les URLs de l'API
    // Version v1 pour la gestion des évolutions futures de l'API
    String APP_ROOT = "gestiondestock/v1";

    // ============ ENDPOINTS COMMANDES FOURNISSEURS ============
    String COMMANDE_FOURNISSEUR_ENDPOINT = APP_ROOT + "/commandesfournisseurs";
    String CREATE_COMMANDE_FOURNISSEUR_ENDPOINT = COMMANDE_FOURNISSEUR_ENDPOINT + "/create";
    String FIND_COMMANDE_FOURNISSEUR_BY_ID_ENDPOINT = COMMANDE_FOURNISSEUR_ENDPOINT + "/{idCommandeFournisseur}";
    String FIND_COMMANDE_FOURNISSEUR_BY_CODE_ENDPOINT = COMMANDE_FOURNISSEUR_ENDPOINT + "/filter/{codeCommandeFournisseur}";
    String FIND_ALL_COMMANDE_FOURNISSEUR_ENDPOINT = COMMANDE_FOURNISSEUR_ENDPOINT + "/all";
    String DELETE_COMMANDE_FOURNISSEUR_ENDPOINT = COMMANDE_FOURNISSEUR_ENDPOINT + "/delete/{idCommandeFournisseur}";

    // ============ ENDPOINTS ENTREPRISES ============
    String ENTREPRISE_ENDPOINT = APP_ROOT + "/entreprises";

    // ============ ENDPOINTS FOURNISSEURS ============
    String FOURNISSEUR_ENDPOINT = APP_ROOT + "/fournisseurs";

    // ============ ENDPOINTS UTILISATEURS ============
    String UTILISATEUR_ENDPOINT = APP_ROOT + "/utilisateurs";

    // ============ ENDPOINTS VENTES ============
    String VENTES_ENDPOINT = APP_ROOT + "/ventes";

    // ============ ENDPOINT AUTHENTIFICATION ============
    String AUTHENTICATION_ENDPOINT = APP_ROOT + "/auth";

}