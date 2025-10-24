// Déclaration du package pour les exceptions
package com.saki.gestion_stock.exception;

// Enumération des codes d'erreur standardisés de l'application
public enum ErrorCodes {

    // Codes d'erreur pour les articles (plage 1000-1999)
    ARTICLE_NOT_FOUND(1000),           // Article non trouvé
    ARTICLE_NOT_VALID(1001),           // Données de l'article invalides
    ARTICLE_ALREADY_IN_USE(1002),      // Article utilisé dans des commandes/ventes

    // Codes d'erreur pour les catégories (plage 2000-2999)
    CATEGORY_NOT_FOUND(2000),          // Catégorie non trouvée
    CATEGORY_NOT_VALID(2001),          // Données de la catégorie invalides
    CATEGORY_ALREADY_IN_USE(2002),     // Catégorie utilisée par des articles

    // Codes d'erreur pour les clients (plage 3000-3999)
    CLIENT_NOT_FOUND(3000),            // Client non trouvé
    CLIENT_NOT_VALID(3001),            // Données du client invalides
    CLIENT_ALREADY_IN_USE(3002),       // Client utilisé dans des commandes

    // Codes d'erreur pour les commandes clients (plage 4000-4999)
    COMMANDE_CLIENT_NOT_FOUND(4000),   // Commande client non trouvée
    COMMANDE_CLIENT_NOT_VALID(4001),   // Données de la commande client invalides
    COMMANDE_CLIENT_NON_MODIFIABLE(4002), // Commande client non modifiable (état final)
    COMMANDE_CLIENT_ALREADY_IN_USE(4003), // Commande client déjà utilisée

    // Codes d'erreur pour les commandes fournisseurs (plage 5000-5999)
    COMMANDE_FOURNISSEUR_NOT_FOUND(5000),   // Commande fournisseur non trouvée
    COMMANDE_FOURNISSEUR_NOT_VALID(5001),   // Données de la commande fournisseur invalides
    COMMANDE_FOURNISSEUR_NON_MODIFIABLE(5002), // Commande fournisseur non modifiable
    COMMANDE_FOURNISSEUR_ALREADY_IN_USE(5003), // Commande fournisseur déjà utilisée

    // Codes d'erreur pour les entreprises (plage 6000-6999)
    ENTREPRISE_NOT_FOUND(6000),        // Entreprise non trouvée
    ENTREPRISE_NOT_VALID(6001),        // Données de l'entreprise invalides

    // Codes d'erreur pour les fournisseurs (plage 7000-7999)
    FOURNISSEUR_NOT_FOUND(7000),       // Fournisseur non trouvé
    FOURNISSEUR_NOT_VALID(7001),       // Données du fournisseur invalides
    FOURNISSEUR_ALREADY_IN_USE(7002),  // Fournisseur utilisé dans des commandes

    // Codes d'erreur pour les lignes de commande (plage 8000-10999)
    LIGNE_COMMANDE_CLIENT_NOT_FOUND(8000),     // Ligne commande client non trouvée
    LIGNE_COMMANDE_FOURNISSEUR_NOT_FOUND(9000), // Ligne commande fournisseur non trouvée
    LIGNE_VENTE_NOT_FOUND(10000),              // Ligne de vente non trouvée

    // Codes d'erreur pour les mouvements de stock (plage 11000-11999)
    MVT_STK_NOT_FOUND(11000),          // Mouvement de stock non trouvé
    MVT_STK_NOT_VALID(11001),          // Données du mouvement de stock invalides

    // Codes d'erreur pour les utilisateurs (plage 12000-12999)
    UTILISATEUR_NOT_FOUND(12000),      // Utilisateur non trouvé
    UTILISATEUR_NOT_VALID(12001),      // Données de l'utilisateur invalides
    UTILISATEUR_ALREADY_EXISTS(12002), // Utilisateur déjà existant
    UTILISATEUR_CHANGE_PASSWORD_OBJECT_NOT_VALID(12003), // Objet changement mot de passe invalide

    BAD_CREDENTIALS(12004), // ← CORRECTION: code dupliqué - Identifiants incorrects

    // Codes d'erreur pour les ventes (plage 13000-13999)
    VENTE_NOT_FOUND(13000),            // Vente non trouvée
    VENTE_NOT_VALID(13001),            // Données de la vente invalides
    VENTE_ALREADY_IN_USE(13002),       // Vente déjà utilisée

    // Codes d'erreur pour la gestion de stock (plage 15000-15999)
    STOCK_INSUFFISANT(15000),          // Stock insuffisant pour l'opération
    QUANTITE_INVALIDE(15001),          // Quantité invalide (négative ou nulle)
    OPERATION_NOT_VALID(15002),        // Opération sur le stock non valide

    // Codes d'erreur techniques (plage 14000-14999)
    UPDATE_PHOTO_EXCEPTION(14000),     // Erreur lors de la mise à jour de photo
    UNKNOWN_CONTEXT(14001);            // Contexte inconnu pour une opération

    // Code numérique de l'erreur
    private int code;

    // Constructeur de l'enum avec le code numérique
    ErrorCodes(int code) {
        this.code = code;
    }

    // Getter pour récupérer le code numérique
    public int getCode() {
        return code;
    }
}