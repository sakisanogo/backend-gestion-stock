package com.saki.gestion_stock.model;

// Énumération représentant les différents types de mouvements de stock
// Définit la nature et l'impact d'un mouvement sur le niveau de stock
public enum TypeMvtStk {

    // Mouvement d'entrée de stock - Augmente le niveau de stock
    // Exemples: réception de commande fournisseur, retour client, production
    ENTREE,

    // Mouvement de sortie de stock - Diminue le niveau de stock
    // Exemples: vente, expédition commande client, utilisation interne
    SORTIE,

    // Correction positive de stock - Augmente le niveau de stock
    // Utilisé pour les ajustements d'inventaire lorsque le stock physique est supérieur au stock théorique
    CORRECTION_POS,

    // Correction négative de stock - Diminue le niveau de stock
    // Utilisé pour les ajustements d'inventaire lorsque le stock physique est inférieur au stock théorique
    CORRECTION_NEG
}