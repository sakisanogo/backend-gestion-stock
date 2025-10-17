package com.saki.gestion_stock.services.Impl;

import java.util.List;
import java.util.stream.Collectors;

import com.saki.gestion_stock.dto.ClientDto;
import com.saki.gestion_stock.exception.EntityNotFoundException;
import com.saki.gestion_stock.exception.ErrorCodes;
import com.saki.gestion_stock.exception.InvalidEntityException;
import com.saki.gestion_stock.exception.InvalidOperationException;
import com.saki.gestion_stock.model.CommandeClient;
import com.saki.gestion_stock.model.LigneCommandeClient;
import com.saki.gestion_stock.repository.ClientRepository;
import com.saki.gestion_stock.repository.CommandeClientRepository;
import com.saki.gestion_stock.repository.LigneCommandeClientRepository;
import com.saki.gestion_stock.services.ClientService;
import com.saki.gestion_stock.validator.ClientValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class ClientServiceImpl implements ClientService {

    private ClientRepository clientRepository;
    private CommandeClientRepository commandeClientRepository;
    private LigneCommandeClientRepository ligneCommandeClientRepository; // Ajout pour gérer les lignes de commande

    @Autowired
    public ClientServiceImpl(
            ClientRepository clientRepository,
            CommandeClientRepository commandeClientRepository,
            LigneCommandeClientRepository ligneCommandeClientRepository) { // Ajout dans le constructeur
        this.clientRepository = clientRepository;
        this.commandeClientRepository = commandeClientRepository;
        this.ligneCommandeClientRepository = ligneCommandeClientRepository;
    }

    @Override
    public ClientDto save(ClientDto dto) {
        List<String> errors = ClientValidator.validate(dto);
        if (!errors.isEmpty()) {
            log.error("Client is not valid {}", dto);
            throw new InvalidEntityException("Le client n'est pas valide", ErrorCodes.CLIENT_NOT_VALID, errors);
        }

        return ClientDto.fromEntity(
                clientRepository.save(
                        ClientDto.toEntity(dto)
                )
        );
    }

    @Override
    public ClientDto findById(Integer id) {
        if (id == null) {
            log.error("Client ID is null");
            return null;
        }
        return clientRepository.findById(id)
                .map(ClientDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucun Client avec l'ID = " + id + " n' ete trouve dans la BDD",
                        ErrorCodes.CLIENT_NOT_FOUND)
                );
    }

    @Override
    public List<ClientDto> findAll() {
        return clientRepository.findAll().stream()
                .map(ClientDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        if (id == null) {
            log.error("Client ID is null");
            return;
        }

        // Vérifier si le client existe
        findById(id);

        log.info("Tentative de suppression du client ID: {}", id);

        // Récupérer toutes les commandes clients associées à ce client
        List<CommandeClient> commandeClients = commandeClientRepository.findAllByClientId(id);

        if (!commandeClients.isEmpty()) {
            log.info("Suppression de {} commandes clients pour le client ID: {}", commandeClients.size(), id);

            // Pour chaque commande client, supprimer d'abord les lignes de commande
            for (CommandeClient commandeClient : commandeClients) {
                // Récupérer et supprimer les lignes de commande associées
                List<LigneCommandeClient> lignesCommande = ligneCommandeClientRepository.findAllByCommandeClientId(commandeClient.getId());
                if (!lignesCommande.isEmpty()) {
                    log.info("Suppression de {} lignes de commande pour la commande ID: {}", lignesCommande.size(), commandeClient.getId());
                    ligneCommandeClientRepository.deleteAll(lignesCommande);
                }

                // Supprimer la commande client
                commandeClientRepository.delete(commandeClient);
            }

            log.info("Toutes les commandes clients et leurs lignes ont été supprimées");
        }

        // Maintenant supprimer le client
        clientRepository.deleteById(id);
        log.info("Client ID: {} supprimé avec succès", id);
    }
}