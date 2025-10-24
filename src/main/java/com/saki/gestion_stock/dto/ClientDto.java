// Déclaration du package pour les DTOs
package com.saki.gestion_stock.dto;

// Importation de l'entité Client
import com.saki.gestion_stock.model.Client;
// Importation de l'annotation Jackson pour ignorer des champs lors de la sérialisation JSON
import com.fasterxml.jackson.annotation.JsonIgnore;
// Importation pour la liste de commandes clients
import java.util.List;
// Importations des annotations Lombok
import lombok.Builder;
import lombok.Data;

// Annotation Lombok pour générer automatiquement les getters, setters, equals, hashCode, toString
@Data
// Annotation Lombok pour générer un builder pattern pour la classe
@Builder
public class ClientDto {

    // Identifiant unique du client
    private Integer id;

    // Nom de famille du client
    private String nom;

    // Prénom du client
    private String prenom;

    // Adresse du client (DTO imbriqué)
    private AdresseDto adresse;

    // Photo ou avatar du client
    private String photo;

    // Adresse email du client
    private String mail;

    // Numéro de téléphone du client
    private String numTel;

    // Identifiant de l'entreprise propriétaire du client (pour le multi-tenant)
    private Integer idEntreprise;

    // Liste des commandes passées par ce client
    // Annotation pour ignorer ce champ lors de la sérialisation JSON (évite les références circulaires)
    @JsonIgnore
    private List<CommandeClientDto> commandeClients;

    // Méthode statique pour convertir une entité Client en ClientDto
    public static ClientDto fromEntity(Client client) {
        // Vérification si l'entité est null pour éviter les NullPointerException
        if (client == null) {
            return null;
        }

        // Construction du ClientDto en utilisant le pattern Builder de Lombok
        return ClientDto.builder()
                .id(client.getId())
                .nom(client.getNom())
                .prenom(client.getPrenom())
                // Conversion de l'adresse entité en AdresseDto
                .adresse(AdresseDto.fromEntity(client.getAdresse()))
                .photo(client.getPhoto())
                .mail(client.getMail())
                .numTel(client.getNumTel())
                .idEntreprise(client.getIdEntreprise())
                .build();
    }

    // Méthode statique pour convertir un ClientDto en entité Client
    public static Client toEntity(ClientDto dto) {
        // Vérification si le DTO est null pour éviter les NullPointerException
        if (dto == null) {
            return null;
        }

        // Création d'une nouvelle instance d'entité Client
        Client client = new Client();

        // Copie des données du DTO vers l'entité
        client.setId(dto.getId());
        client.setNom(dto.getNom());
        client.setPrenom(dto.getPrenom());
        // Conversion de l'AdresseDto en entité Adresse
        client.setAdresse(AdresseDto.toEntity(dto.getAdresse()));
        client.setPhoto(dto.getPhoto());
        client.setMail(dto.getMail());
        client.setNumTel(dto.getNumTel());
        client.setIdEntreprise(dto.getIdEntreprise());

        return client;
    }

}