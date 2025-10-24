package com.saki.gestion_stock.model;

// Énumération représentant les différents états possibles d'une commande
// Utilisée pour suivre le cycle de vie des commandes clients et fournisseurs
public enum EtatCommande {

    // État initial : la commande est en cours de préparation
    // La commande vient d'être créée mais n'est pas encore validée
    EN_PREPARATION,

    // État intermédiaire : la commande a été validée
    // La commande est confirmée et peut passer à la phase d'exécution (livraison, etc.)
    VALIDEE,

    // État final : la commande a été livrée
    // La commande est complètement traitée et livrée au client ou reçue du fournisseur
    LIVREE
}