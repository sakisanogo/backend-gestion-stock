// Déclaration du package pour les DTOs
package com.saki.gestion_stock.dto;

// Importation de l'entité Ventes
import com.saki.gestion_stock.model.Ventes;
// Importations des classes Java
import java.time.Instant;
import java.util.List;
// Importations des annotations Lombok
import lombok.Builder;
import lombok.Data;

// Annotation Lombok pour générer automatiquement les getters, setters, equals, hashCode, toString
@Data
// Annotation Lombok pour générer un builder pattern pour la classe
@Builder
public class VentesDto {

    // Identifiant unique de la vente
    private Integer id;

    // Code unique de la vente
    private String code;

    // Date et heure de la vente
    private Instant dateVente;

    // Commentaire ou note supplémentaire sur la vente
    private String commentaire;

    // Liste des lignes de vente (articles vendus)
    private List<LigneVenteDto> ligneVentes;

    // Identifiant de l'entreprise propriétaire (pour le multi-tenant)
    private Integer idEntreprise;

    // Méthode statique pour convertir une entité Ventes en VentesDto
    public static VentesDto fromEntity(Ventes vente) {
        // Vérification si l'entité est null pour éviter les NullPointerException
        if (vente == null) {
            return null;
        }

        // Construction du VentesDto en utilisant le pattern Builder de Lombok
        return VentesDto.builder()
                .id(vente.getId())
                .code(vente.getCode())
                .dateVente(vente.getDateVente()) // ← CORRECTION AJOUTÉE ICI
                .commentaire(vente.getCommentaire())
                .idEntreprise(vente.getIdEntreprise())
                .build();
    }

    // Méthode statique pour convertir un VentesDto en entité Ventes
    public static Ventes toEntity(VentesDto dto) {
        // Vérification si le DTO est null pour éviter les NullPointerException
        if (dto == null) {
            return null;
        }

        // Création d'une nouvelle instance d'entité Ventes
        Ventes ventes = new Ventes();
        ventes.setId(dto.getId());
        ventes.setCode(dto.getCode());
        ventes.setDateVente(dto.getDateVente());
        ventes.setCommentaire(dto.getCommentaire());
        ventes.setIdEntreprise(dto.getIdEntreprise());

        return ventes;
    }
}