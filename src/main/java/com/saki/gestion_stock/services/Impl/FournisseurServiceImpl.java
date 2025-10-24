package com.saki.gestion_stock.services.Impl;

import java.util.List;
import java.util.stream.Collectors;

import com.saki.gestion_stock.dto.FournisseurDto;
import com.saki.gestion_stock.exception.EntityNotFoundException;
import com.saki.gestion_stock.exception.ErrorCodes;
import com.saki.gestion_stock.exception.InvalidEntityException;
import com.saki.gestion_stock.exception.InvalidOperationException;
import com.saki.gestion_stock.repository.CommandeFournisseurRepository;
import com.saki.gestion_stock.repository.FournisseurRepository;
import com.saki.gestion_stock.services.FournisseurService;
import com.saki.gestion_stock.validator.FournisseurValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// Service d'implémentation pour la gestion des fournisseurs
// @Slf4j génère automatiquement un logger SLF4J
@Service
@Slf4j
public class FournisseurServiceImpl implements FournisseurService {

    // Injection des repositories nécessaires
    private FournisseurRepository fournisseurRepository;
    private CommandeFournisseurRepository commandeFournisseurRepository; // Pour vérifier les dépendances

    // Constructeur avec injection de dépendances
    @Autowired
    public FournisseurServiceImpl(FournisseurRepository fournisseurRepository,
                                  CommandeFournisseurRepository commandeFournisseurRepository) {
        this.fournisseurRepository = fournisseurRepository;
        this.commandeFournisseurRepository = commandeFournisseurRepository;
    }

    // Sauvegarde d'un nouveau fournisseur avec validation
    @Override
    public FournisseurDto save(FournisseurDto dto) {
        List<String> errors = FournisseurValidator.validate(dto);
        if (!errors.isEmpty()) {
            log.error("Fournisseur is not valid {}", dto);
            throw new InvalidEntityException("Le fournisseur n'est pas valide", ErrorCodes.FOURNISSEUR_NOT_VALID, errors);
        }

        // Conversion DTO -> Entity, sauvegarde, puis conversion Entity -> DTO
        return FournisseurDto.fromEntity(
                fournisseurRepository.save(
                        FournisseurDto.toEntity(dto)
                )
        );
    }

    // Recherche d'un fournisseur par son ID
    @Override
    public FournisseurDto findById(Integer id) {
        if (id == null) {
            log.error("Fournisseur ID is null");
            return null;
        }
        return fournisseurRepository.findById(id)
                .map(FournisseurDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucun fournisseur avec l'ID = " + id + " n' ete trouve dans la BDD",
                        ErrorCodes.FOURNISSEUR_NOT_FOUND)
                );
    }

    // Récupération de tous les fournisseurs
    @Override
    public List<FournisseurDto> findAll() {
        return fournisseurRepository.findAll().stream()
                .map(FournisseurDto::fromEntity)
                .collect(Collectors.toList());
    }

    // Suppression d'un fournisseur avec vérification des dépendances
    @Override
    public void delete(Integer id) {
        if (id == null) {
            log.error("Fournisseur ID is null");
            return;
        }

        // Vérifier si le fournisseur existe (lève une exception si non trouvé)
        findById(id);

        // Vérifier si le fournisseur a des commandes associées
        // Cette vérification empêche la suppression d'un fournisseur ayant un historique de commandes
        boolean hasCommandes = commandeFournisseurRepository.existsByFournisseurId(id);
        if (hasCommandes) {
            throw new InvalidOperationException("Impossible de supprimer un fournisseur qui a deja des commandes",
                    ErrorCodes.FOURNISSEUR_ALREADY_IN_USE);
        }

        // Si aucune commande n'existe, suppression du fournisseur
        fournisseurRepository.deleteById(id);
        log.info("Fournisseur ID: {} supprimé avec succès", id);
    }
}