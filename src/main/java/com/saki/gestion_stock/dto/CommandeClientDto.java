// Déclaration du package pour les DTOs
package com.saki.gestion_stock.dto;

// Importations des entités
import com.saki.gestion_stock.model.CommandeClient;
import com.saki.gestion_stock.model.EtatCommande;
// Importations des classes Java
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import com.saki.gestion_stock.model.LigneCommandeClient;
// Importations des annotations Lombok
import lombok.Builder;
import lombok.Data;

// Annotation Lombok pour générer automatiquement les getters, setters, equals, hashCode, toString
@Data
// Annotation Lombok pour générer un builder pattern pour la classe
@Builder
public class CommandeClientDto {

    // Identifiant unique de la commande client
    private Integer id;

    // Code unique de la commande
    private String code;

    // Date de création de la commande
    private Instant dateCommande;

    // État actuel de la commande (EN_PREPARATION, VALIDEE, LIVREE, etc.)
    private EtatCommande etatCommande;

    // Client associé à cette commande
    private ClientDto client;

    // Identifiant de l'entreprise propriétaire (pour le multi-tenant)
    private Integer idEntreprise;

    // Liste des lignes de commande (articles commandés)
    private List<LigneCommandeClientDto> ligneCommandeClients;

    // Méthode statique pour convertir une entité CommandeClient en CommandeClientDto
    public static CommandeClientDto fromEntity(CommandeClient commandeClient) {
        // Vérification si l'entité est null pour éviter les NullPointerException
        if (commandeClient == null) {
            return null;
        }

        // Construction du CommandeClientDto en utilisant le pattern Builder de Lombok
        return CommandeClientDto.builder()
                .id(commandeClient.getId())
                .code(commandeClient.getCode())
                .dateCommande(commandeClient.getDateCommande())
                .etatCommande(commandeClient.getEtatCommande())
                // Conversion du client entité en ClientDto
                .client(ClientDto.fromEntity(commandeClient.getClient()))
                .idEntreprise(commandeClient.getIdEntreprise())
                // Conversion des lignes de commande entité en LigneCommandeClientDto
                .ligneCommandeClients(
                        commandeClient.getLigneCommandeClients() != null ?
                                commandeClient.getLigneCommandeClients().stream()
                                        .map(LigneCommandeClientDto::fromEntity)
                                        .collect(Collectors.toList())
                                : null
                )
                .build();
    }

    // Méthode statique pour convertir un CommandeClientDto en entité CommandeClient
    public static CommandeClient toEntity(CommandeClientDto dto) {
        // Vérification si le DTO est null pour éviter les NullPointerException
        if (dto == null) {
            return null;
        }

        // Création d'une nouvelle instance d'entité CommandeClient
        CommandeClient commandeClient = new CommandeClient();
        commandeClient.setId(dto.getId());
        commandeClient.setCode(dto.getCode());
        commandeClient.setClient(ClientDto.toEntity(dto.getClient()));
        commandeClient.setDateCommande(dto.getDateCommande());
        commandeClient.setEtatCommande(dto.getEtatCommande());
        commandeClient.setIdEntreprise(dto.getIdEntreprise());

        // ✅ CORRECTION : Gérer les lignes de commande
        if (dto.getLigneCommandeClients() != null) {
            List<LigneCommandeClient> ligneCommandeClients = dto.getLigneCommandeClients().stream()
                    .map(ligneDto -> {
                        // Conversion de chaque ligne DTO en entité
                        LigneCommandeClient ligne = LigneCommandeClientDto.toEntity(ligneDto);
                        ligne.setCommandeClient(commandeClient); // ⚠️ IMPORTANT : Lier la commande
                        return ligne;
                    })
                    .collect(Collectors.toList());
            commandeClient.setLigneCommandeClients(ligneCommandeClients);
        }

        return commandeClient;
    }

    // Méthode utilitaire pour vérifier si la commande est livrée
    public boolean isCommandeLivree() {
        return EtatCommande.LIVREE.equals(this.etatCommande);
    }
}