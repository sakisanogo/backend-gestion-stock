package com.saki.gestion_stock.model;

// Énumération représentant les différentes sources possibles d'un mouvement de stock
// Utilisée pour tracer l'origine des entrées et sorties de stock dans le système
public enum SourceMvtStk {

    // Mouvement de stock provenant d'une commande client
    // Génère généralement une sortie de stock (diminution du stock)
    COMMANDE_CLIENT,

    // Mouvement de stock provenant d'une commande fournisseur
    // Génère généralement une entrée de stock (augmentation du stock)
    COMMANDE_FOURNISSEUR,

    // Mouvement de stock provenant d'une vente directe
    // Génère généralement une sortie de stock (diminution du stock)
    VENTE
}