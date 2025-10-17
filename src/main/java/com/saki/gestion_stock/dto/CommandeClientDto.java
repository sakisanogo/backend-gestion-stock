package com.saki.gestion_stock.dto;

import com.saki.gestion_stock.model.CommandeClient;
import com.saki.gestion_stock.model.EtatCommande;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import com.saki.gestion_stock.model.LigneCommandeClient;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommandeClientDto {

    private Integer id;
    private String code;
    private Instant dateCommande;
    private EtatCommande etatCommande;
    private ClientDto client;
    private Integer idEntreprise;
    private List<LigneCommandeClientDto> ligneCommandeClients;

    public static CommandeClientDto fromEntity(CommandeClient commandeClient) {
        if (commandeClient == null) {
            return null;
        }
        return CommandeClientDto.builder()
                .id(commandeClient.getId())
                .code(commandeClient.getCode())
                .dateCommande(commandeClient.getDateCommande())
                .etatCommande(commandeClient.getEtatCommande())
                .client(ClientDto.fromEntity(commandeClient.getClient()))
                .idEntreprise(commandeClient.getIdEntreprise())
                .ligneCommandeClients(
                        commandeClient.getLigneCommandeClients() != null ?
                                commandeClient.getLigneCommandeClients().stream()
                                        .map(LigneCommandeClientDto::fromEntity)
                                        .collect(Collectors.toList())
                                : null
                )
                .build();
    }

    public static CommandeClient toEntity(CommandeClientDto dto) {
        if (dto == null) {
            return null;
        }
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
                        LigneCommandeClient ligne = LigneCommandeClientDto.toEntity(ligneDto);
                        ligne.setCommandeClient(commandeClient); // ⚠️ IMPORTANT : Lier la commande
                        return ligne;
                    })
                    .collect(Collectors.toList());
            commandeClient.setLigneCommandeClients(ligneCommandeClients);
        }

        return commandeClient;
    }

    public boolean isCommandeLivree() {
        return EtatCommande.LIVREE.equals(this.etatCommande);
    }
}