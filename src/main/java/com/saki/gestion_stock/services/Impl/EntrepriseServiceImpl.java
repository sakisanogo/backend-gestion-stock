package com.saki.gestion_stock.services.Impl;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import com.saki.gestion_stock.dto.EntrepriseDto;
import com.saki.gestion_stock.dto.RolesDto;
import com.saki.gestion_stock.dto.UtilisateurDto;
import com.saki.gestion_stock.exception.EntityNotFoundException;
import com.saki.gestion_stock.exception.ErrorCodes;
import com.saki.gestion_stock.exception.InvalidEntityException;
import com.saki.gestion_stock.repository.EntrepriseRepository;
import com.saki.gestion_stock.repository.RolesRepository;
import com.saki.gestion_stock.services.EntrepriseService;
import com.saki.gestion_stock.services.UtilisateurService;
import com.saki.gestion_stock.validator.EntrepriseValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

// Service d'implémentation pour la gestion des entreprises
// @Transactional avec rollback sur toutes les exceptions pour assurer l'intégrité des données
// @Slf4j génère automatiquement un logger SLF4J
@Transactional(rollbackOn = Exception.class)
@Service
@Slf4j
public class EntrepriseServiceImpl implements EntrepriseService {

    // Injection des repositories et services nécessaires
    private EntrepriseRepository entrepriseRepository;
    private UtilisateurService utilisateurService;
    private RolesRepository rolesRepository;

    // Constructeur avec injection de dépendances
    @Autowired
    public EntrepriseServiceImpl(EntrepriseRepository entrepriseRepository, UtilisateurService utilisateurService,
                                 RolesRepository rolesRepository) {
        this.entrepriseRepository = entrepriseRepository;
        this.utilisateurService = utilisateurService;
        this.rolesRepository = rolesRepository;
    }

    // Sauvegarde d'une nouvelle entreprise avec création automatique d'un administrateur
    @Override
    public EntrepriseDto save(EntrepriseDto dto) {
        // Validation des données de l'entreprise
        List<String> errors = EntrepriseValidator.validate(dto);
        if (!errors.isEmpty()) {
            log.error("Entreprise is not valid {}", dto);
            throw new InvalidEntityException("L'entreprise n'est pas valide", ErrorCodes.ENTREPRISE_NOT_VALID, errors);
        }

        // Sauvegarde de l'entreprise
        EntrepriseDto savedEntreprise = EntrepriseDto.fromEntity(
                entrepriseRepository.save(EntrepriseDto.toEntity(dto))
        );

        // Création automatique d'un utilisateur administrateur pour cette entreprise
        UtilisateurDto utilisateur = fromEntreprise(savedEntreprise);

        // Sauvegarde de l'utilisateur administrateur
        UtilisateurDto savedUser = utilisateurService.save(utilisateur);

        // Attribution du rôle ADMIN à l'utilisateur créé
        RolesDto rolesDto = RolesDto.builder()
                .roleName("ADMIN")
                .utilisateur(savedUser)
                .build();

        rolesRepository.save(RolesDto.toEntity(rolesDto));

        return savedEntreprise;
    }

    // Crée un utilisateur administrateur à partir des informations de l'entreprise
    private UtilisateurDto fromEntreprise(EntrepriseDto dto) {
        return UtilisateurDto.builder()
                .adresse(dto.getAdresse())           // Réutilisation de l'adresse de l'entreprise
                .nom(dto.getNom())                   // Nom de l'entreprise comme nom d'utilisateur
                .prenom(dto.getCodeFiscal())         // Code fiscal comme prénom (peut être adapté)
                .email(dto.getEmail())               // Email de l'entreprise comme identifiant
                .moteDePasse(generateRandomPassword()) // Génération d'un mot de passe temporaire
                .entreprise(dto)                     // Lien avec l'entreprise créée
                .dateDeNaissance(Instant.now())      // Date actuelle comme date de naissance par défaut
                .photo(dto.getPhoto())               // Réutilisation de la photo de l'entreprise
                .build();
    }

    // Génère un mot de passe temporaire - À AMÉLIORER en production
    // NOTE: En environnement de production, il faudrait :
    // 1. Générer un vrai mot de passe aléatoire sécurisé
    // 2. Envoyer un email avec un lien d'activation
    // 3. Forcer l'utilisateur à changer son mot de passe à la première connexion
    private String generateRandomPassword() {
        return "som3R@nd0mP@$$word"; // Mot de passe fixe - À remplacer par une génération sécurisée
    }

    // Recherche d'une entreprise par son ID
    @Override
    public EntrepriseDto findById(Integer id) {
        if (id == null) {
            log.error("Entreprise ID is null");
            return null;
        }
        return entrepriseRepository.findById(id)
                .map(EntrepriseDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucune entreprise avec l'ID = " + id + " n' ete trouve dans la BDD",
                        ErrorCodes.ENTREPRISE_NOT_FOUND)
                );
    }

    // Récupération de toutes les entreprises
    @Override
    public List<EntrepriseDto> findAll() {
        return entrepriseRepository.findAll().stream()
                .map(EntrepriseDto::fromEntity)
                .collect(Collectors.toList());
    }

    // Suppression d'une entreprise
    // NOTE: En production, il faudrait implémenter une suppression en cascade
    // ou une désactivation plutôt qu'une suppression physique
    @Override
    public void delete(Integer id) {
        if (id == null) {
            log.error("Entreprise ID is null");
            return;
        }
        entrepriseRepository.deleteById(id);
    }
}